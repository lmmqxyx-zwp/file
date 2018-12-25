<%@page language="java" pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="top.by.file.vo.FileList"%>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <style type="text/css">
            table.gridtable {
                font-family: verdana,arial,sans-serif;
                font-size:11px;
                color:#333333;
                border-width: 1px;
                border-color: #666666;
                border-collapse: collapse;
            }
            table.gridtable th {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #666666;
                background-color: #dedede;
            }
            table.gridtable td {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #666666;
                background-color: #ffffff;
            }
        </style>
        
        <link rel="stylesheet" type="text/css" href="simditor-2.3.23/css/app.css" />
        <link rel="stylesheet" type="text/css" href="simditor-2.3.23/css/mobile.css" />
        <link rel="stylesheet" type="text/css" href="simditor-2.3.23/css/simditor.css" />
        
        <script type="text/javascript" src="js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="layer/layer.js"></script>
        <script type="text/javascript" src="simditor-2.3.23/scripts/module.js"></script>
        <script type="text/javascript" src="simditor-2.3.23/scripts/hotkeys.js"></script>
        <script type="text/javascript" src="simditor-2.3.23/scripts/uploader.js"></script>
        <script type="text/javascript" src="simditor-2.3.23/scripts/simditor.js"></script>
    </head>
    <body>
        <h2 style="color: red;">Hello World!(<%=request.getContextPath() %>) 
            <a href='<%=request.getContextPath() %>/jstree?EXE_METHOD_NAME=jstree_index.jsp'>jstree</a>
        </h2>
        <table class="gridtable" style="margin: 0px auto; width: 777px;">
            <thead>
                <tr>
                    <th>主键</th>
                    <th>文件名</th>
                    <th>文件类型</th>
                    <th>文件备注</th>
                    <th>文件版本</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<FileList> list = (List<FileList>) request.getAttribute("fileList");
                    for (int i = 0; i < list.size(); i++) {
                        FileList fileList = list.get(i);
                %>
                <tr>
                    <td><%=fileList.getId()%></td>
                    <td><%=fileList.getFileName()%></td>
                    <td><%=fileList.getFileType()%></td>
                    <td><%=fileList.getFileRemark()%></td>
                    <td><%=fileList.getFileVersion()%></td>
                    <td><a href='#' onclick="Insert();">增</a> | <a href='#'
                        onclick="Delete('<%=fileList.getId()%>');">刪</a> | <a href='#'
                        onclick="Update('<%=fileList.getId()%>');">改</a> | <a href='#'
                        onclick="Select('<%=fileList.getId()%>');">查</a></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <div id="insert_model" style="display: none">
            <div>
                <input type="text" id="fileName" name="fileName" placeholder="文件名称">
            </div>
            <div>
                <select id="fileType">
                    <option value="">文件类型</option>
                    <option value=".txt">.txt</option>
                </select>
            </div>
            <div>
                <input type="text" id="fileRemark" name="fileRemark"
                    placeholder="文件备注">
            </div>
            <div>
                <select id="fileVersion">
                    <option value="">文件版本</option>
                    <option value="1.0.0">1.0.0</option>
                    <option value="1.0.1">1.0.1</option>
                    <option value="1.0.2">1.0.2</option>
                    <option value="1.0.3">1.0.3</option>
                    <option value="1.0.4">1.0.4</option>
                    <option value="1.0.5">1.0.5</option>
                    <option value="1.0.6">1.0.6</option>
                    <option value="1.0.7">1.0.7</option>
                </select>
            </div>
            <div>
                <textarea id="editor" name="comment"></textarea>
            </div>
        </div>
    </body>

    <script type="text/javascript">
        $(function() {
            Simditor.locale = 'zh-CN';//设置中文
            var editor = new Simditor({
                textarea : $('#editor'), //textarea的id
                placeholder : '这里输入文字...',
                toolbar : [ 'title', 'bold', 'italic', 'underline',
                        'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul',
                        'blockquote', 'code', 'table', '|', 'link', 'image',
                        'hr', '|', 'indent', 'outdent', 'alignment' ], //工具条都包含哪些内容
                pasteImage : true,//允许粘贴图片
                defaultImage : '/image/fork.png',//编辑器插入的默认图片，此处可以删除
                upload : {
                    url : '<%=request.getContextPath()%>/upload', //文件上传的接口地址
                    params : null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
                    fileKey : 'upload_file', //服务器端获取文件数据的参数名
                    connectionCount : 3,
                    leaveConfirm : '正在上传文件'
                }
            });
        });

        function Insert() {
            layer.open({
                type : 1, //Layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）,
                title : '新增文件',
                area : [ '100%', '100%' ], //宽高
                shade : 0.4, //遮罩透明度
                content : $("#insert_model"),//支持获取DOM元素
                btn : [ '提交', '丢弃' ], //按钮组
                scrollbar : false,//屏蔽浏览器滚动条
                yes : function(index) {
                    layer.close(index);
                    var params = {
                        'fileName': $('#fileName').val(), 
                        'fileType': $('#fileType').val(), 
                        'fileRemark': $('#fileRemark').val(), 
                        'fileVersion': $('#fileVersion').val(), 
                        'fileContent': $('#editor').val()
                    };
                    $.ajax({
                        type : 'post',
                        url : '<%=request.getContextPath()%>/insert',
                        data : params,
                        dataType : 'json',
                        success : function(data) {
                            console.log(data);
                            location.reload();
                        },
                        error : function(e) {
                            console.log(e);
                        }
                    });
                },
                btn2 : function() {
                    layer.msg('丢弃');
                }
            });
        }

        function Delete(id) {
            var index = layer.confirm('确定要删除吗？', {btn: ['是',' 否'], coseBtn: 0, offset: '220px'}, function() {
                layer.close(index);
                $.ajax({
                    type : 'post',
                    url : '<%=request.getContextPath()%>/delete',
                    data : {'id': id},
                    dataType : 'json',
                    success : function(data) {
                        console.log(data);
                        location.reload();
                    },
                    error : function(e) {
                        console.log(e);
                    }
                });
            });
        }

        function Update(id) {

        }
        function Select(id) {
            $.ajax({
                type : 'get',
                url : '<%=request.getContextPath()%>/select',
                data : {'id': id},
                dataType : 'json',
                contentType:"application/json",
                success : function(data) {
                    layer.tab({
                        area: ['100%', '100%'],
                        tab: [{
                            title: '文件内容', 
                            content: data.content
                        }]
                    });
                },
                error : function(e) {
                    console.log(e);
                }
            });
        }
    </script>

</html>
