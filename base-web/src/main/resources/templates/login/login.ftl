<!doctype html>
<html class="x-admin-sm">
<head>
    <#include "../common/header.ftl"/>
    <script type="text/javascript" src="${request.contextPath}/static/script/login/login.js"></script>
</head>
<body class="login-bg">
<div class="login layui-anim layui-anim-up">
    <div class="message">${SITE_NAME}-管理登录</div>
    <div id="darkbannerwrap"></div>
    <input id="message" type="hidden" value="${message}"/>
    <form id="loginForm" action="/login" method="post" class="layui-form">
        <input name="username" placeholder="账户" type="text" lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input name="password" placeholder="密码" type="password" lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20">
    </form>
</div>
<!-- 底部结束 -->
</body>
</html>