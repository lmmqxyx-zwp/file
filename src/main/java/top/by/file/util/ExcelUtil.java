package top.by.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import top.by.file.vo.China;

/**
 * Excel操作
 * 
 * <p>Title: ExcelUtil.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.iceos.top</p>
 *
 * ----------------------------------------
 * ----------------------------------------
 * @author zwp
 * @date 2018年12月24日
 *
 * @version 1.0
 */
public class ExcelUtil {

    private static final String EXCEL_XLS  = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    
    /**
     * 判断Excel的版本,获取Workbook
     * 
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) { // Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     * 
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }
    
    /**
     * 获取excel中的数据给List
     * 
     * @param EXCEL_PATH excel路径
     * @param SHEET_NUM  sheet页码
     * @return
     * @throws Exception
     */
	public static List<China> readExcelExample(String EXCEL_PATH, int SHEET_NUM) throws Exception {
		List<China> list = null;

		File excelFile = new File(EXCEL_PATH);

		checkExcelVaild(excelFile);

		list = new ArrayList<China>();

		FileInputStream in = new FileInputStream(excelFile);

		Workbook workbook = getWorkbok(in, excelFile);

		Sheet sheet = workbook.getSheetAt(SHEET_NUM);

		int count = 0;
		for (Row row : sheet) {
			// 跳过第一行的表头
			if (count == 0) {
				count++;
				continue;
			}

			int endCol = row.getLastCellNum();

			China china = new China();

			for (int i = 0; i < endCol; i++) {
				Cell cell = row.getCell(i);

				Object o = getValue(cell);

				if (o == null) {
					continue;
				}

				switch (i) {
				case 0:
					china.setId(Integer.parseInt(o.toString()));
					break;
				case 1:
					china.setName(o.toString().toUpperCase().equals("NULL") ? "" : o.toString());
					break;
				case 2:
					china.setParentId(Integer.parseInt(o.toString()));
					break;
				case 3:
					china.setShortName(o.toString().toUpperCase().equals("NULL") ? "" : o.toString());
					break;
				case 4:
					china.setLevelType(Integer.parseInt(o.toString().split("\\.")[0]));
					break;
				case 5:
					china.setCityCode(o.toString().toUpperCase().equals("NULL") ? "" : o.toString());
					break;
				case 6:
					china.setZipCode(o.toString().toUpperCase().equals("NULL") ? "" : o.toString());
					break;
				case 7:
					china.setManagerName(o.toString().toUpperCase().equals("NULL") ? "" : o.toString());
					break;
				case 8:
					china.setLng(Float.parseFloat(o.toString()));
					break;
				case 9:
					china.setLat(Float.parseFloat(o.toString()));
					break;
				case 10:
					china.setPinyin(o.toString().toUpperCase().equals("NULL") ? "" : o.toString());
					break;
				}
			}

			if (china.getId() == null || "".equals(china.getId())) {
				
			} else {
				list.add(china);
			}
		}

		return list;
	}

    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     * 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 同时支持Excel 2003、2007
            String excel_path = "C:/Users/zwp/Downloads/行政区划数据库_with+经纬度-省市区-邮编-区号-拼音-简称.xls";
            // 创建文件对象
            File excelFile = new File(excel_path); 
            FileInputStream in = new FileInputStream(excelFile);
            checkExcelVaild(excelFile);

            // 获取Workbook的两种方法
            // Workbook workbook = WorkbookFactory.create(is);
            // Excel2003/2007/2010都是可以处理的
            Workbook workbook = getWorkbok(in, excelFile);

            // Sheet的数量
            int sheetCount = workbook.getNumberOfSheets(); 
            // 设置当前excel中sheet的下标：0开始
            // 遍历第一个Sheet
            // Sheet sheet = workbook.getSheetAt(0);
            Sheet sheet = workbook.getSheetAt(0);

            // 获取总行数
            System.out.println(sheet.getLastRowNum());

            // 为跳过第一行目录设置count
            int count = 0;
            for (Row row : sheet) {
                try {
                    // 跳过第一和第二行的目录
                    // if (count < 2) {
                    //     count++;
                    //     continue;
                    // }
                    count ++;
                    if (count == 10) {
                        return;
                    }

                    // 如果当前行没有数据，跳出循环
                    // if (row.getCell(0).toString().equals("")) {
                    //     return;
                    // }

                    // 获取总列数(空格的不计算)
                    int columnTotalNum = row.getPhysicalNumberOfCells();
                    System.out.println("总列数：" + columnTotalNum);
                    System.out.println("最大列数：" + row.getLastCellNum());

                    // for循环的，不扫描空格的列
                    // for (Cell cell : row) {
                    // System.out.println(cell);
                    // }
                    int end = row.getLastCellNum();
                    for (int i = 0; i < end; i++) {
                        Cell cell = row.getCell(i);
                        if (cell == null) {
                            System.out.print("null" + "\t");
                            continue;
                        }

                        Object obj = getValue(cell);
                        System.out.print(obj + "\t");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getValue(Cell cell) {
        Object o = null;
        if(cell == null) {
        	return o;
        }
        switch (cell.getCellTypeEnum()) {
        case BOOLEAN:
            o = cell.getBooleanCellValue();
            break;
        case ERROR:
            o = cell.getErrorCellValue();
            break;
        case NUMERIC:
            o = cell.getNumericCellValue();
            break;
        case STRING:
            o = cell.getStringCellValue();
            break;
        default:
            break;
        }
        return o;
    }
}
