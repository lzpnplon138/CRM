<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门管理</title>
    <%@include file="/static/common/common.jsp"%>

    <script src="/static/js/department.js"></script>
</head>
<body>

<table id="myDataGrid"></table>

<div id="myToolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
    <a id="state_btn" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="changeState">启动</a>
</div>

<div id="form_btns">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
</div>

<div id="myDialog">
    <form id="myForm" method="post">
        <input name="id" type="hidden" id="deptId">
        <table align="center" style="margin-top: 20px">
            <tbody>
                <tr>
                    <td>部门名称:</td>
                    <td><input class="easyui-textbox" name="name" prompt="请输入部门名称"
                               invalidMessage="部门名称已存在"
                               validType="remote['/department/validateName.do?id='+deptId.value,'name']"></td>
                </tr>
                <tr>
                    <td>部门编码:</td>
                    <td><input class="easyui-textbox" name="sn" prompt="请输入部门编码"></td>
                </tr>
            </tbody>
        </table>
    </form>
</div>

</body>
</html>