<!doctype html>
<html class="x-admin-sm" xmlns:th="http://www.thymeleaf.org">
<head>
    <#include "../common/header.ftl" />
    <script type="text/javascript" src="${request.contextPath}/static/js/xadmin.js"></script>
    <script>
        var is_remember = true;
    </script>
</head>
<body class="index">
<!-- 顶部开始 -->
<div class="container">
    <div class="logo">
        <a href="./index.ftl">${SITE_NAME}</a></div>
    <div class="left_open">
        <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
    </div>
    <ul class="layui-nav left fast-add" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:void(0);">+新增</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('最大化','http://www.baidu.com','','',true)">
                        <i class="iconfont">&#xe6a2;</i>弹出最大化</a></dd>
                <dd>
                    <a onclick="xadmin.open('弹出自动宽高','http://www.baidu.com')">
                        <i class="iconfont">&#xe6a8;</i>弹出自动宽高</a></dd>
                <dd>
                    <a onclick="xadmin.open('弹出指定宽高','http://www.baidu.com',500,300)">
                        <i class="iconfont">&#xe6a8;</i>弹出指定宽高</a></dd>
                <dd>
                    <a onclick="xadmin.add_tab('在tab打开','member-list.html')">
                        <i class="iconfont">&#xe6b8;</i>在tab打开</a></dd>
                <dd>
                    <a onclick="xadmin.add_tab('在tab打开刷新','member-del.html',true)">
                        <i class="iconfont">&#xe6b8;</i>在tab打开刷新</a></dd>
            </dl>
        </li>
    </ul>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:void(0);">${currentUser.username}</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('个人信息','http://www.baidu.com')">个人信息</a></dd>
                <dd>
                    <a href="/logout">退出</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item to-index">
            <a href="#">前台首页</a>
        </li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">
            <#list menuList as menu>
                <li>
                    <a
                            <#if menu.child?? && (menu.child?size > 0)>
                                href="javascript:void(0);"
                            <#else>
                                href="javascript:xadmin.add_tab('${menu.current.menuName}','${menu.current.menuUrl}');"
                            </#if>
                    >
                        <i class="layui-icon left-nav-li ${menu.current.menuIcon}" lay-tips="${menu.current.menuName}"></i>
                        <cite>${menu.current.menuName}</cite>
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <#if menu.child?? && (menu.child?size > 0)>
                        <ul class="sub-menu">
                            <#list menu.child as sub>
                                <li>
                                    <a onclick="xadmin.add_tab('${sub.menuName}','${sub.menuUrl}')">
                                        <i class="layui-icon ${sub.menuIcon}"></i>
                                        <cite>${sub.menuName}</cite>
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </#if>
                </li>
            </#list>
        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home">
                <i class="layui-icon">&#xe68e;</i>我的桌面
            </li>
        </ul>
        <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
            <dl>
                <dd data-type="this">关闭当前</dd>
                <dd data-type="other">关闭其它</dd>
                <dd data-type="all">关闭全部</dd>
            </dl>
        </div>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='/default' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
        </div>
        <div id="tab_show"></div>
    </div>
</div>
<div class="page-content-bg"></div>
<style id="theme_style"></style>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
</body>

</html>