package top.by.file.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import top.by.file.db.DBUtil;

@WebServlet(value = "/delete")
public class DeleteController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		if (id == null || "".equals(id)) {
			throw new ServletException("数据无比重要，删除需要传递ID");
		}
		String sql = "delete from t_file_list where id = " + id + "";
		
		JSONObject jObject = new JSONObject();
		
		boolean b = DBUtil.ExecuteSql(sql);
		if (b) {
			System.out.println("数据删除成功，执行SQL：" + sql);
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
