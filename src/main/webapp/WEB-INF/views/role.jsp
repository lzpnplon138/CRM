<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
    <%@include file="/static/common/common.jsp"%>

    <script src="/static/js/role.js"></script>
</head>
<body>

<%--数据表格--%>
<table id="myDataGrid"></table>

<%--数据表格的工具栏--%>
<div id="myToolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
</div>

<%--对话框按钮--%>
<div id="form_btns">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
</div>

<%--对话框--%>
<div id="myDialog">
    <form id="myForm" method="post">
        <input name="id" type="hidden">
        <table align="center" style="margin-top: 20px">
            <tbody>
                <tr>
                    <td>角色名称:<input class="easyui-textbox" name="name" prompt="请输入角色名称"></td>
                    <td>角色编码:<input class="easyui-textbox" name="sn" prompt="请输入角色编码"></td>
                </tr>
                <tr>
                    <td>菜单:<input id="menu_combotree" class="easyui-combotree"
                                  data-options="cascadeCheck:false,multiple:true,url:'/menu/selectRootMenus.do'"></td>
                </tr>
                <tr>
                    <td><table id="allPermissions"></table></td>
                    <td><table id="selfPermissions"></table></td>
                </tr>
            </tbody>
        </table>
    </form>
</div>

</body>
</html>