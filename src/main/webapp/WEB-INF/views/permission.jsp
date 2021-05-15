<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>权限管理</title>
    <%@include file="/static/common/common.jsp"%>

    <script src="/static/js/permission.js"></script>
</head>
<body>

<%--数据表格--%>
<table id="myDataGrid"></table>

<%--数据表格的工具栏--%>
<div id="myToolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="reload()">加载权限</a>
</div>

</body>
</html>