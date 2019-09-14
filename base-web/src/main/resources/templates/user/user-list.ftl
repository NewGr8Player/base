<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <#include "../common/header.ftl" />
    <script type="text/javascript" src="${request.contextPath}/static/js/xadmin.js"></script>
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
<script>
    layui.use(['layer', 'jquery', 'element', 'table', 'laypage', 'form'], function () {
        var $ = layui.jquery,
            table = layui.table,
            form = layui.form,
            layer = parent.layui.layer;

        $(function () {
            tableRender();
        });

        /**
         * 数据表格对象
         */
        var tableGrid;

        /**
         * 刷新页面表格
         */
        function tableRender() {
            tableGrid = table.render({
                elem: '#userTable'
                , url: '${request.contextPath}/user/queryList'
                , method: 'post'
                , page: true
                , even: true
                , limit: 10
                , height: 'full-300'
                , skin: 'row line'
                , request: {
                    pageName: 'current'
                    , limitName: 'size'
                }
                , response: {
                    statusCode: 0
                    , statusName: 'status'
                    , msgName: 'msg'
                    , countName: 'total'
                    , dataName: 'records'
                }
                , cols: [[
                    {field: 'checkbox', title: '复选框', width: '5%', type: 'checkbox'}
                    , {field: 'sequence', title: '序号', width: '5%', type: 'numbers'}
                    , {field: 'id', title: '唯一标示', width: '20%', sort: true}
                    , {field: 'username', title: '登录名称', width: '20%'}
                    , {field: 'nickname', title: '显示名称', width: '20%'}
                    , {field: 'btn', title: '操作', width: '30%', templet: '#btnTepmlate'}
                ]]
            });
        }

        /**
         * 重载数据表格
         *
         * @param data
         */
        function reloadTable(data) {
            tableGrid.reload({
                where: {
                    username: data.field.username
                    , nickname: data.field.nickname
                }
                , page: {
                    curr: 1
                }
            });
        }

        /**
         * 查询
         */
        form.on('submit(sreach)', function (data) {
            reloadTable(data);
            return false;
        });

        /**
         * 重置
         */
        form.on('submit(reset)', function (data) {
            $('#searchForm').get(0).reset();
            for (var i in data.field) {
                data.field[i] = '';
            }
            reloadTable();
            return false;
        });

        /**
         * 操作按钮
         */
        table.on('tool(userTable)', function (obj) {
                var data = obj.data, event = obj.event;
                layer.msg(event);
                console.log(obj);
                if (event == 'view') {
                    xadmin.open('查看', '/user/view?id=' + data.id);
                } else if (event == 'edit') {
                    xadmin.open('编辑', '/user/edit?id=' + data.id);
                } else if (event == 'delete') {
                    layer.confirm('确认要删除吗？', function () {
                        $.ajax({
                            url: '${request.contextPath}/user/delete'
                            , method: 'post'
                            , data: {id: data.id}
                            , success: function (msg) {
                                if (!!msg) {
                                    layer.alert(
                                        "删除成功!"
                                        , {icon: 6, time: 1000}
                                        , function () {
                                            reloadTable();
                                        }
                                    );
                                } else {
                                    layer.alert("删除失败!", {icon: 5, time: 1000});
                                }
                            }
                        });

                    });
                } else {
                    layer.alert('错误的操作!');
                }
            }
        );
    });
</script>
</html>