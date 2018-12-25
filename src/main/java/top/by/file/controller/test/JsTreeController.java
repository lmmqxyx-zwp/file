package top.by.file.controller.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import top.by.file.db.DBUtil;
import top.by.file.vo.China;
import top.by.file.vo.JsTree;

@WebServlet(value = "/jstree")
public class JsTreeController extends HttpServlet {

    private static final String JSTREE_INDEX = "jstree_index.jsp";
    private static final String JSON_DATA_ALL = "JSON_DATA_ALL";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 需要执行的方法
        String EXE_METHOD_NAME = request.getParameter("EXE_METHOD_NAME");
        try {
            if (EXE_METHOD_NAME == null || "".equals(EXE_METHOD_NAME)) {
                throw new JsTreeException("EXE_METHOD_NAME");
            }
            
            if (JSTREE_INDEX.equals(EXE_METHOD_NAME)) {
                this.JSTREE_INDEX(request, response);
            } else if (JSON_DATA_ALL.equals(EXE_METHOD_NAME)) {
                this.JSON_DATA_ALL(request, response);
            }
        } catch (JsTreeException je) {
            je.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    
    // 主页
    private void JSTREE_INDEX(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); 
        request.getRequestDispatcher(JSTREE_INDEX).forward(request, response);
    }
    
    // 所有数据
    private void JSON_DATA_ALL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        try {
            String sql = "select * from t_china;";
            System.out.println("执行SQL：" + sql);
            List<China> list = DBUtil.QueryList(sql, China.class);
            List<JsTree<China>> l = new ArrayList();
            for (China china : list) {
                JsTree<China> jsTree = new JsTree<China>();
                jsTree.setId(String.valueOf(china.getId()));
                if (china.getParentId() == 0) {
                    jsTree.setParent("#");
                    Map<String, Object> li_attr = new HashMap<String, Object>();
                    li_attr.put("data-jstree", "{\"type\":\"js\"}");
                    jsTree.setLi_attr(li_attr);
                } else {
                    jsTree.setParent(String.valueOf(china.getParentId()));
                }
                jsTree.setText(china.getName());
                Map<String, Boolean> state = new HashMap<String, Boolean>();
                //state.put("opened", true);
                jsTree.setState(state);
                //jsTree.setChildren(this.getJsTree(id));
                jsTree.setSourceData(china);
                
                l.add(jsTree);
            }
            response.getWriter().println(new JSONObject().put("data", l));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        List<China> list = DBUtil.QueryList("select * from t_china", China.class);
        System.out.println(list.size());
    }
    
    /**
     * JsTree异常
     * 
     * <p>Title: JsTreeException</p>
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
    private class JsTreeException extends Exception {
        public JsTreeException(String message) {
            super(message);
        }
    }
    
}
