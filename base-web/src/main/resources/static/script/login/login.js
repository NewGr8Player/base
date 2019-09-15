layui.use(['form', 'layer'], function () {
    var form = layui.form
        , layer = layui.layer;

    $(function () {
        if (window.parent.length > 0) { /* 避免session超时嵌套 */
            window.parent.location = basePath + '/login.html';
        }
        var message = $('#message').val();
        if (!!message) {
            layer.msg(message);
        }
    });

    form.on('submit(login)', function (data) {
        console.log(data);
        layer.msg('登录中请稍后', {icon: 4, shade: [0.8, '#393D49'], shadeClose: false});
        $('#loginForm').submit();
        return false;
    });
});