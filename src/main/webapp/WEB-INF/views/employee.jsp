<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>员工管理</title>
    <%@include file="/static/common/common.jsp" %>

    <script src="/static/js/employee.js"></script>
</head>
<body>

<%--数据表格--%>
<table id="myDataGrid"></table>

<%--数据表格的工具栏--%>
<div id="myToolbar">
    <shiro:hasPermission name="employee:saveOrUpdate">
        <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="edit">编辑</a>
    </shiro:hasPermission>
    <a id="state_btn" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="changeState">离职</a>


    <input id="keyword" class="easyui-textbox" prompt="用户名/电话"/>
    入职时间:
    <input id="minDate" class="easyui-datebox" prompt="起始时间" editable="false"/>至
    <input id="maxDate" class="easyui-datebox" prompt="结束时间" editable="false"/>
    <input id="deptId" class="easyui-combobox" data-options="panelHeight:'auto',url:'/department/list.do',
                        valueField:'id',textField:'name',editable:false,prompt:'所属部门'">
    <a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="query();">查询</a>


    <a class="easyui-linkbutton" iconCls="icon-cut" plain="true" data-cmd="import">导入</a>
    <a class="easyui-linkbutton" iconCls="icon-cut" plain="true" data-cmd="export">导出</a>
</div>

<%--上传窗口--%>
<div id="import">
    <form id="importFileForm" action="/employee/importXlsx.do" method="post" enctype="multipart/form-data">
        <table style="margin:5px;height:70px;">
            <tr>
                <td colspan="4">
                    <input id="file" class="easyui-filebox" name="file" style="width:300px" data-options="buttonText:'选择xlsx文件',
                        accept:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel' ">
                </td>
            </tr>
            <tr>
                <td colspan="4" id="fileName">
                </td>
            </tr>
            <tr>
                <td colspan="4" id="uploadInfo">
                </td>
            </tr>
        </table>
    </form>
</div>

<%--导入框按钮--%>
<div id="import_btns">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" data-cmd="upload">上传</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" data-cmd="close">关闭</a>
</div>

<%--对话框按钮--%>
<div id="form_btns">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
</div>

<%--对话框--%>
<div id="myDialog">
    <form id="myForm" method="post">
        <input name="id" type="hidden" id="empId">
        <table align="center" style="margin-top: 20px">
            <tbody>
            <tr>
                <td>用户名:</td>
                <td><input class="easyui-textbox" name="username" prompt="请输入用户名"
                           invalidMessage="用户名已存在"
                           validType="remote['/employee/validateUsername.do?id='+empId.value,'username']"></td>
            </tr>
            <tr>
                <td>真实姓名:</td>
                <td><input class="easyui-textbox" name="realname" prompt="请输入真实姓名"></td>
            </tr>
            <tr id="tr_password">
                <td>密码:</td>
                <td><input id="passwordbox" class="easyui-passwordbox" name="password" prompt="请输入密码"></td>
            </tr>
            <tr>
                <td>电话:</td>
                <td><input class="easyui-numberbox" name="tel" prompt="请输入电话"></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input class="easyui-textbox" name="email" prompt="请输入邮箱" validType="email"></td>
            </tr>
            <tr>
                <td>部门:</td>
                <td><input name="dept.id" class="easyui-combobox" data-options="panelHeight:'auto',url:'/department/list.do',
                        valueField:'id',textField:'name',editable:false,prompt:'请选择部门'"></td>
            </tr>
            <tr>
                <td>入职时间:</td>
                <td><input class="easyui-datebox" name="hireDate" prompt="请选择入职时间" editable="false"></td>
            </tr>
            <tr>
                <td>是否管理员:</td>
                <td><input name="admin" class="easyui-combobox" data-options="panelHeight:'auto',
                        data:[{value:'1',text:'是'},{value:'0',text:'否'}],
                        valueField:'value',textField:'text', editable:false"></td>
            </tr>
            <tr>
                <td>角色:</td>
                <td><input id="role_combobox" class="easyui-combobox" data-options="panelHeight:'auto',multiple:true,
                        url:'/role/list.do',valueField:'id',textField:'name'"></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

</body>
</html>