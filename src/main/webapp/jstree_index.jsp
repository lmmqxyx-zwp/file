<%@page language="java" pageEncoding="utf-8"%>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jstree/themes/default/style.css" />
    </head>
    <body>
        <h2 style="color: red;">Hello JsTree!</h2>
        <hr>
        <!-- html -->
        <p>
            html方式形成的JsTree
        </p>
        <div id='html_data'>
          <ul>
		    <li>Root node 1
		      <ul>
		        <li class='.jstree-default .jstree-file'>Child node 1</li>
		        <li><a href="#">Child node 2</a></li>
		      </ul>
		    </li>
		    <li>Root node 2</li>
		  </ul>
        </div>
        <hr>
        <!-- <a id='exeSQL' href='#'>初始化国家省市区树形结构</a> -->
        <div id='json_data'></div>
    </body>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jstree/jstree.js"></script>
    <script type="text/javascript">
       var ctx = '<%=request.getContextPath()%>';
       var jsTree;
       $('#exeSQL').on('click', function() {
           $.ajax({
               type : 'post',
               url : ctx + '/jstree',
               data : {
                   'EXE_METHOD_NAME': 'JSON_DATA_ALL'
                   },
               dataType : 'json',
               success : function(data) {
                   debugger;
                   jsonDataTree = $('#json_data').jstree({
                       plugins: ['checkbox', 'dnd'],
                       core: {
                    	   check_callback: true,
                           data: data.data
                       }
                   });
               },
               error : function(e) {
                   console.log(e);
               }
           });
       });
       
       $(function() {
    	   $('#html_data').jstree();
    	   
    	   // 也可以直接初始化
           $.ajax({
               type : 'post',
               url : ctx + '/jstree',
               data : {
                   'EXE_METHOD_NAME': 'JSON_DATA_ALL'
                   },
               dataType : 'json',
               success : function(data) {
                   debugger;
                   jsTree = $('#json_data').jstree({
                       plugins: ['checkbox'],
                       core: {
                           data: data.data
                       }
                   });
               },
               error : function(e) {
                   console.log(e);
               }
           });
    	   
           // 点击选中事件
           $('#json_data').on('changed.jstree', function(e, data) {
               debugger;
               console.log(data);
           });
           
       });
    </script>
</html>
