<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>主页</title>
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/css/public.css">
    <%@include file="/static/common/common.jsp"%>
    <script src="/static/js/index.js"></script>
</head>
<body>
<div id="myLayout" class="easyui-layout" fit="true">
    <div data-options="region:'north',height:70,split:true" >
        <div class="public-header-warrp">
            <div class="public-header">
                <div class="content">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img  src="/static/images/logo.png"/>
                    <div class="public-header-admin fr">
                        <p class="admin-name"><font  color ="green">您好！<shiro:principal property="username"/> </font> </p>
                        <div class="public-header-fun fr">
                            <a href="/logout.do" class="public-header-loginout">安全退出</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'south'" style="height:100px;">
        <p align="center">版权申明</p>
    </div>
    <div data-options="region:'west'" style="width:160px;">
        <div class="easyui-accordion" fit="true">
            <div title="菜单">
                <ul id="myTree"></ul>
            </div>
            <div title="待办事项">待办事项</div>
            <div title="公司广告">公司广告</div>
        </div>
    </div>
    <div data-options="region:'center'">
        <div id="myTabs" class="easyui-tabs" fit="true"></div>
    </div>
</div>
</body>
</html>