<!doctype html>
<html class="x-admin-sm">
<head>
    <#include "../common/header.ftl"/>
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message">${SITE_NAME}-管理登录</div>
    <div id="darkbannerwrap"></div>

    <form id="loginForm" action="/login" method="post" class="layui-form">
        <input name="username" placeholder="账户" type="text" lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input name="password" placeholder="密码" type="password" lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20">
    </form>
</div>

<script>

    layui.use(['form', 'layer'], function () {
        var form = layui.form
            , layer = layui.layer;

        $(function () {
            if (window.parent.length > 0) { /* 避免session超时嵌套 */
                window.parent.location = basePath + '/login.html';
            }
            if (!!'${message}') {
                layer.msg('${message}');
            }
        });

        form.on('submit(login)', function (data) {
            console.log(data);
            layer.msg('登录中请稍后', {icon: 4, shade: [0.8, '#393D49'], shadeClose: false});
            $('#loginForm').submit();
            return false;
        });
    });
</script>
<!-- 底部结束 -->
</body>
</html>