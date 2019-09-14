<!doctype html>
<html class="x-admin-sm" xmlns:th="http://www.thymeleaf.org">
<head>
    <#include "../common/header.ftl" />
    <style>
        .title-text span {
            display: block; /*设置为块级元素会独占一行形成上下居中的效果*/
            position: relative; /*定位横线（当横线的父元素）*/
            text-align: center;
        }

        .blue-text {
            color: #188eee; /*居中文字的颜色*/
            font-weight: bold;
        }

        .title-text span:before, .title-text span:after {
            content: '';
            position: absolute; /*定位背景横线的位置*/
            top: 50%;
            background: #8c8c8c; /*背景横线颜色*/
            width: 35%; /*单侧横线的长度*/
            height: 1px;
        }

        .title-text span:before {
            left: 2%; /*调整背景横线的左右距离*/
        }

        .title-text span:after {
            right: 2%;
        }
    </style>
</head>
<body>
<div class="work-intro">
    <div class="title">
        <h2 class="title-text">
            <span class="blue-text">欢迎使用${SITE_NAME}!</span>
        </h2>
    </div>
</div>
</body>
</html>