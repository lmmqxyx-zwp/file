package top.by.file.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import top.by.file.db.DBUtil;
import top.by.file.vo.FileList;

@WebServlet(value = "/insert")
public class InsertController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName = request.getParameter("fileName") != null ? request.getParameter("fileName") : "";
		String fileType = request.getParameter("fileType") != null ? request.getParameter("fileType") : "";
		String fileRemark = request.getParameter("fileRemark") != null ? request.getParameter("fileRemark") : "";
		String fileContent = request.getParameter("fileContent") != null ? request.getParameter("fileContent").replace("'", "''") : "";
		String fileVersion = request.getParameter("fileVersion") != null ? request.getParameter("fileVersion") : "";
		
		String sql = "insert into t_file_list (file_name, file_type, file_remark, file_content, file_version) values ('" + fileName + "', '" + fileType + "', '" + fileRemark + "', '" + fileContent + "', '" + fileVersion + "')";
		
		JSONObject jObject = new JSONObject();
		
		boolean b = DBUtil.ExecuteSql(sql);
		if (b) {
			System.out.println("数据插入成功，执行SQL：" + sql);
		}
		
		jObject.put("b", b);
		
		PrintWriter out = response.getWriter();
		out.write(jObject.toString());
        out.flush();
        out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
}
