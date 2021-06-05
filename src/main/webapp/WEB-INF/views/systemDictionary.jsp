<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>字典目录管理</title>
    <%@include file="/static/common/common.jsp"%>

    <script src="/static/js/systemDictionary.js"></script>
</head>
<body>

<body class="easyui-layout">
<div data-options="region:'west',title:'字典目录',split:true" style="width:50%;background:#eee;">
    <table id="dictionary_datagrid"></table>
</div>
<div data-options="region:'center',title:'字典明细'" style="width:50%;background:#eee;">
    <table id="dictionaryItem_datagrid"></table>
</div>
</body>

<!--字典按钮-->
<div id="dictionary_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="reload">刷新</a>
</div>

<%--字典的对话框--%>
<div id="dictionary_dialog">
    <form id="dictionary_form" method="post">
        <input name="id" type="hidden">
        <table align="center" style="margin-top: 20px">
            <tbody>
            <tr>
                <td>目录编码:</td>
                <td><input class="easyui-textbox" name="sn"></td>
            </tr>
            <tr>
                <td>目录名称:</td>
                <td><input class="easyui-textbox" name="name"></td>
            </tr>
            <tr>
                <td>目录简介:</td>
                <td><input class="easyui-textbox" name="intro"></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

<%--字典对话框按钮--%>
<div id="dictionary_btns">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
</div>

<%--------------------------------------------------------------------------------------%>

<!--明细按钮-->
<div id="dictionaryItem_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="del">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="reload">刷新</a>
</div>


<%--明细的对话框--%>
<div id="dictionaryItem_dialog">
    <form id="dictionaryItem_form" method="post">
        <input name="id" type="hidden">
        <table align="center" style="margin-top: 20px">
            <tbody>
            <tr>
                <td>明细名称:</td>
                <td><input class="easyui-textbox" name="sn"></td>
            </tr>
            <tr>
                <td>明细简介:</td>
                <td><input class="easyui-textbox" name="name"></td>
            </tr>
            <tr>
                <td>字典明细:</td>
                <td><input id="item_combobox" class="easyui-combobox" data-options="panelHeight:'auto',
                        url:'/systemDictionary/list.do',valueField:'id',textField:'name'"></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

<%--明细对话框按钮--%>
<div id="dictionaryItem_btns">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
</div>

</body>
</html>