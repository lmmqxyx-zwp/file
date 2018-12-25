package top.by.file.util;

import java.util.List;

import top.by.file.vo.China;

public class ExcelUtilTest {

	public static void main(String[] args) {
		String EXCEL_PATH = "C:/Users/zwp/Desktop/file-master/src/main/java/top/by/file/china.xls";
		int SHEET_NUM 	  = 0;
		
		try {
			List<China> list = ExcelUtil.readExcelExample(EXCEL_PATH, SHEET_NUM);
			System.out.println(list.size());
			System.out.println(list.get(17));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("3.0".split("\\.").length);
	}
	
}
