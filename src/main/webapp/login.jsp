<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>客户关系管理系统</title>
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="/static/plugins/jquery-easyui/jquery.min.js"></script>

    <script>
        $(function () {
            //登陆
            $("#login_btn").click(function () {
                $.post("/login.do", $("#loginform").serialize(), function (data) {
                    if (data.success) {
                        //登陆成功,跳转到主页
                        window.parent.location.href = "/index.do";
                    } else {
                        //登陆失败,提示错误信息
                        alert(data.msg);
                    }
                }, "json");
            });

            //重置
            function reset() {

            }

        });
    </script>

</head>
<body>
<section class="container">
    <div class="login">
        <h1>用户登录</h1>
        <form id="loginform" method="post">
            <p><input type="text" name="username" value="admin" placeholder="账号"></p>
            <p><input type="password" name="password" value="1" placeholder="密码"></p>
            <p class="submit">
                <input type="button" value="登录" id="login_btn">
                <input type="button" value="重置" onclick="reset()">
            </p>
        </form>
    </div>
</section>
<div style="text-align:center;" class="login-help">
    <p>Copyright ©2015 广州叩丁狼教育科技有限公司</p>
    <table>
        <tr>
            <td colspan="1"></td>
        </tr>

    </table>
</div>
</body>
</html>