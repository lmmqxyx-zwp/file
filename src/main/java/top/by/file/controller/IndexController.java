package top.by.file.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.by.file.db.DBUtil;
import top.by.file.vo.FileList;

/**
 * @ClassName: IndexController
 * @Description: 获取所有文件
 *
 * ---
 * ---
 * @author 冰羽
 * @date 2018年11月25日 --- 下午1:47:24
 * --------------------------------------
 * @version 0.0.1-SNAPSHOT
 */
@WebServlet(value = "/index")
public class IndexController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<FileList> list = DBUtil.QueryList("select * from t_file_list order by id asc", FileList.class);
		System.out.println(list);
		request.setAttribute("fileList", list);
		request.setCharacterEncoding("utf-8"); 
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
}
