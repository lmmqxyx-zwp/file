package top.by.file.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import top.by.file.vo.FileList;

public class DBUtil {
	
	private static final String URL = "jdbc:mysql://192.168.0.244:3306/file?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&useSSL=false";
	private static final String USER_NAME = "oppox905";
	private static final String PASS_WORD = "oppox905";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Connection connection;
	
	private static Connection getConnection() {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection(URL, USER_NAME, PASS_WORD);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	private static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T> List<T> QueryList(String sql, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			PreparedStatement pre = getConnection().prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			Field[] fields = cls.getDeclaredFields();
			// 取出bean里的所有方法
			Method[] methods = cls.getDeclaredMethods();
			//Map<String, String> valMap = getFieldValueMap(cls.newInstance());
			
			while(rs.next()) {
				Object bean = cls.newInstance();
				for(int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					// 获取字段属性
					String fieldName = field.getName();
					// 转换表列名
					String columnLabel = HumpToUnderline(fieldName);
					// 获取set方法
					String fieldSetName = parSetName(fieldName);
					if (!checkSetMet(methods, fieldSetName)) {
						continue;
					}
					
					Method fieldSetMet = cls.getMethod(fieldSetName, field.getType());
					//String value = valMap.get(fieldName);
					
					String fieldType = field.getType().getSimpleName();
					if ("String".equals(fieldType)) {
						fieldSetMet.invoke(bean, rs.getString(columnLabel));
					} else if ("Date".equals(fieldType)) {
						Date temp = parseDate(rs.getString(columnLabel));
						fieldSetMet.invoke(bean, temp);
					} else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
						Integer intval = Integer.parseInt(rs.getString(columnLabel));
						fieldSetMet.invoke(bean, intval);
					} else if ("Long".equalsIgnoreCase(fieldType)) {
						Long temp = Long.parseLong(rs.getString(columnLabel));
						fieldSetMet.invoke(bean, temp);
					} else if ("Double".equalsIgnoreCase(fieldType)) {
						Double temp = Double.parseDouble(rs.getString(columnLabel));
						fieldSetMet.invoke(bean, temp);
					} else if ("Boolean".equalsIgnoreCase(fieldType)) {
						Boolean temp = Boolean.parseBoolean(rs.getString(columnLabel));
						fieldSetMet.invoke(bean, temp);
					} else {
						System.out.println("not supper type" + fieldType);
					}
				}
				
				list.add((T) bean);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 格式化string为Date
	 * 
	 * @param datestr
	 * @return date
	 */
	public static Date parseDate(String datestr) {
		if (null == datestr || "".equals(datestr)) {
			return null;
		}
		try {
			String fmtstr = null;
			if (datestr.indexOf(':') > 0) {
				fmtstr = "yyyy-MM-dd HH:mm:ss";
			} else {
				fmtstr = "yyyy-MM-dd";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);
			return sdf.parse(datestr);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 取Bean的属性和值对应关系的MAP
	 * 
	 * @param bean
	 * @return Map
	 */
	public static Map<String, String> getFieldValueMap(Object bean) {
		Class<?> cls = bean.getClass();
		Map<String, String> valueMap = new HashMap<String, String>();
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldType = field.getType().getSimpleName();
				String fieldGetName = parGetName(field.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
				String result = null;
				if ("Date".equals(fieldType)) {
					result = fmtDate((Date) fieldVal);
				} else {
					if (null != fieldVal) {
						result = String.valueOf(fieldVal);
					}
				}
				// String fieldKeyName = parKeyName(field.getName());
				valueMap.put(field.getName(), result);
			} catch (Exception e) {
				continue;
			}
		}
		return valueMap;
	}
	
	/**
	 * 日期转化为String
	 * 
	 * @param date
	 * @return date string
	 */
	public static String fmtDate(Date date) {
		if (null == date) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断是否存在某属性的 get方法
	 * 
	 * @param methods
	 * @param fieldGetMet
	 * @return boolean
	 */
	public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
		for (Method met : methods) {
			if (fieldGetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼接某属性的 get方法
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String parGetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		int startIndex = 0;
		if (fieldName.charAt(0) == '_')
			startIndex = 1;
		return "get" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
				+ fieldName.substring(startIndex + 1);
	}

	/**
	 * 判断是否存在某属性的 set方法
	 * 
	 * @param methods
	 * @param fieldSetMet
	 * @return boolean
	 */
	public static boolean checkSetMet(Method[] methods, String fieldSetMet) {
		for (Method met : methods) {
			if (fieldSetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼接在某属性的 set方法
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String parSetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		int startIndex = 0;
		if (fieldName.charAt(0) == '_')
			startIndex = 1;
		return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
				+ fieldName.substring(startIndex + 1);
	}
	
	public static List<String> getField(Object o) {
		List<String> list = new ArrayList<String>();
		
		Class cls = o.getClass();
		
		Field[] fields = cls.getDeclaredFields();
		
		for (Field field : fields) {
			list.add(field.getName());
		}
		
		return list;
	}
	
	/***
	 * 下划线命名转为驼峰命名
	 * 
	 * @param para 下划线命名的字符串
	 */
	public static String UnderlineToHump(String para) {
		StringBuilder result = new StringBuilder();
		String a[] = para.split("_");
		for (String s : a) {
			if (result.length() == 0) {
				result.append(s.toLowerCase());
			} else {
				result.append(s.substring(0, 1).toUpperCase());
				result.append(s.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/***
	 * 驼峰命名转为下划线命名
	 * 
	 * @param para 驼峰命名的字符串
	 */

	public static String HumpToUnderline(String para) {
		StringBuilder sb = new StringBuilder(para);
		int temp = 0;// 定位
		for (int i = 0; i < para.length(); i++) {
			if (Character.isUpperCase(para.charAt(i))) {
				sb.insert(i + temp, "_");
				temp += 1;
			}
		}
		return sb.toString().toLowerCase();
	}
	
	
	public static void main(String[] args) {
		System.out.println(getField(new FileList()));
		
		String field = "fileNameKK33a";
		
		System.out.println(HumpToUnderline(field));
		List<FileList> list = QueryList("select * from t_file_list;", FileList.class);
		for (FileList fileList : list) {
			System.out.println(fileList);
		}
	}

}
