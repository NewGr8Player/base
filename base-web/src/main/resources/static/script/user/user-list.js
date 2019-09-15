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
            , url: basePath + '/user/queryList'
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
            if (event == 'view') {
                xadmin.open('查看', '/user/view?id=' + data.id, 600, 400);
            } else if (event == 'edit') {
                xadmin.open('编辑', '/user/edit?id=' + data.id, 600, 400);
            } else if (event == 'delete') {
                layer.confirm('确认要删除吗？', function () {
                    $.ajax({
                        url: basePath + '/user/delete'
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