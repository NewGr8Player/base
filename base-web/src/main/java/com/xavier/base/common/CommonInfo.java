package com.xavier.base.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 网站信息
 */
@Component
public class CommonInfo {

    public static String SITE_NAME;

    public static String AUTHOR_NAME;

    public static String AUTHOR_SITE;

    @Value("${site.site-name:" + DEFAULT_SITE_NAME + "}")
    public void setSiteName(String siteName) {
        SITE_NAME = siteName;
    }

    @Value("${site.author-name:" + DEFAULT_AUTHOR_NAME + "}")
    public void setAuthorName(String authorName) {
        AUTHOR_NAME = authorName;
    }

    @Value("${site.author-site:" + DEFAULT_AUTHOR_SITE + "}")
    public void setAuthorSite(String authorSite) {
        AUTHOR_SITE = authorSite;
    }

    private static final String DEFAULT_SITE_NAME = "通用后台模板";

    private static final String DEFAULT_AUTHOR_NAME = "NewGr8Player";

    private static final String DEFAULT_AUTHOR_SITE = "https://newgr8player.top/";
}
