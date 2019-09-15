<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <#include "../common/header.ftl" />
    <script type="text/javascript" src="${request.contextPath}/static/js/xadmin.js"></script>
    <script type="text/javascript" src="${request.contextPath}/static/script/user/user-add.js"></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form">
            <div class="layui-form-item">
                <label for="username" class="layui-form-label">
                    <span class="x-red">*</span>登录名
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="username" name="username" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span>将会成为您唯一的登入名
                </div>
            </div>
            <div class="layui-form-item">
                <label for="phone" class="layui-form-label">
                    <span class="x-red">*</span>显示名
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="nickname" name="nickname" required="" lay-verify="required"
                           autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span>登陆后显示此名称
                </div>
            </div>
            <#-- TODO 这里做完角色还得加进来 -->
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="x-red">*</span>角色</label>
                <div class="layui-input-block">
                    <input type="checkbox" name="like1[write]" lay-skin="primary" title="超级管理员" checked="">
                    <input type="checkbox" name="like1[read]" lay-skin="primary" title="编辑人员">
                    <input type="checkbox" name="like1[write]" lay-skin="primary" title="宣传人员" checked="">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">
                    <span class="x-red">*</span>密码
                </label>
                <div class="layui-input-inline">
                    <input type="password" id="L_pass" name="password" required="" lay-verify="pass"
                           autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    6到16个字符
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                    <span class="x-red">*</span>确认密码
                </label>
                <div class="layui-input-inline">
                    <input type="password" id="L_repass" name="repass" required="" lay-verify="repass"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                </label>
                <@shiro.hasPermission name="sys:user:edit">
                    <button class="layui-btn" lay-filter="add" lay-submit="">
                        保存
                    </button>
                </@shiro.hasPermission>
            </div>
        </form>
    </div>
</div>
</body>

</html>
