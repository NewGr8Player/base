<!doctype html>
<html class="x-admin-sm" xmlns:th="http://www.thymeleaf.org">
<head>
    <th:block th:include="/common/header::head"></th:block>
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message" th:text="${SITE_NAME}+'-管理登录'"></div>
    <div id="darkbannerwrap"></div>
    <input id="message" type="hidden" th:value="${message}">
    <form method="post" class="layui-form" action="/login/login">
        <input name="user.username" placeholder="账户" type="text" lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input name="user.password" placeholder="密码" type="password" lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20">
    </form>
</div>

<script>
    layui.use(['layer', 'jquery', 'form'], function () {
        var layer = layui.layer,
            $ = layui.$,
            form = layui.form;

        $(function () {
            if (window.parent.length > 0) { /* 避免session超时嵌套 */
                window.parent.location = basePath + '/login';
            }
            var message = $("#message").val();
            if (!!message) {
                layer.msg(message, {icon: 2, timeout: 1000, shade: [0.8, '#393D49'], shadeClose: true});
            }
        });

        form.on('submit(login)', function (data) {
            console.log(data);
            layer.msg('登录中请稍后', {icon: 4, shade: [0.8, '#393D49'], shadeClose: false});
            //$('#loginForm').submit();
            return false;
        });
    });
</script>
<!-- 底部结束 -->
</body>
</html>