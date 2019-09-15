layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form
        , layer = layui.layer
        , $ = layui.jquery;

    form.verify({
        username: function (value) {
            if (value.length < 3) {
                return '登录名至少3个字符啊';
            }
        },
        nikename: function (value) {
            if (value.length < 3) {
                return '显示名至少得3个字符啊';
            }
        },
        pass: [/(.+){6,12}$/, '密码必须6到12位'],
        repass: function (value) {
            if ($('#L_pass').val() != $('#L_repass').val()) {
                return '两次密码不一致';
            }
        }
    });

    form.on('submit(add)', function (data) {
        $.ajax({
            url: basePath + '/user/save'
            , method: 'post'
            , contentType: 'application/json;charset=utf-8'
            , data: JSON.stringify(data.field)
            , success: function (msg) {
                if (!!msg) {
                    layer.alert("保存成功!", {
                            icon: 6
                        }, function () {
                            xadmin.close();
                            xadmin.father_reload();
                        }
                    );
                } else {
                    layer.alert("保存成功!", {icon: 5});
                }
            }
        });
        return false;
    });
});