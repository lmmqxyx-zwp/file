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

import jdk.nashorn.internal.scripts.JO;
import top.by.file.db.DBUtil;
import top.by.file.vo.FileList;

/**
 * @ClassName: SelectController
 * @Description: 获取单个文件
 *
 * ---
 * ---
 * @author 冰羽
 * @date 2018年11月25日 --- 下午1:47:05
 * --------------------------------------
 * @version 0.0.1-SNAPSHOT
 */
@WebServlet(value = "/select")
public class SelectController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Access-Control-Allow-Origin", "*");
		// TODO Auto-generated method stub
		List<FileList> list = DBUtil.QueryList("select * from t_file_list where id = '" + request.getParameter("id") + "'", FileList.class);
		JSONObject jObject = new JSONObject();
		if (list != null && list.size() == 1) {
			jObject.put("content", list.get(0).getFileContent());
		}
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
