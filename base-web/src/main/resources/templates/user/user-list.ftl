<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <#include "../common/header.ftl" />
    <script type="text/javascript" src="${request.contextPath}/static/js/xadmin.js"></script>
    <script type="text/javascript" src="${request.contextPath}/static/script/user/user-list.js"></script>
</head>
<body>
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a href="">用户管理</a>
            <a>
              <cite>用户列表</cite>
            </a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i>
    </a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form id="searchForm" class="layui-form layui-col-space5">
                        <div class="layui-inline layui-show-xs-block">
                            <input type="text" name="username" placeholder="请输入用户名" autocomplete="off"
                                   class="layui-input">
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn" lay-submit="" lay-filter="sreach">
                                <i class="layui-icon layui-icon-search"></i>
                            </button>
                            <button class="layui-btn" lay-submit="" lay-filter="reset">
                                <i class="layui-icon layui-icon-refresh"></i>
                            </button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-header">
                    <@shiro.hasPermission name="sys:user:edit">
                        <button class="layui-btn layui-btn-danger" onclick="delAll()">
                            <i class="layui-icon"></i>批量删除
                        </button>
                        <button class="layui-btn" onclick="xadmin.open('添加用户','/user/add',600,400)">
                            <i class="layui-icon"></i>添加
                        </button>
                    </@shiro.hasPermission>
                </div>
                <div class="layui-card-body ">
                    <table id="userTable" class="layui-table layui-form" lay-filter="userTable">
                    </table>
                </div>
                <script id="btnTepmlate" type="text/html">
                    <a lay-event="view" class="layui-btn layui-btn-xs">
                        <i class="layui-icon layui-icon-search"></i>
                        查看
                    </a>
                    <@shiro.hasPermission name="sys:user:edit">
                        <a lay-event="edit" class="layui-btn layui-btn-normal layui-btn-xs">
                            <i class="layui-icon layui-icon-edit"></i>
                            编辑
                        </a>
                        <a lay-event="delete" class="layui-btn layui-btn-danger layui-btn-xs">
                            <i class="layui-icon layui-icon-delete"></i>
                            删除
                        </a>
                    </@shiro.hasPermission>
                </script>
            </div>
        </div>
    </div>
</div>
</body>
</html>