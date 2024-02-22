package com.example.cloudCommon.enums;

/**
 * 系统常量，开发不建议使用具体数值，可观性不强
 */
public class SystemConstants {
    /**
     * 登录token
     */
    public static final String USER_LOGIN="blogLogin:";
    public static final String ADMIN_LOGIN="adminLogin:";

    /**
     * 文章：草稿为1，已发布为0
     */
    public static final int ARTICLE_STATUS_DRAFT=1;
    public static final int ARTICLE_STATUS_NORMAL=0;

    /**
     * 分类：禁用为1，开放为0
     */
    public static final String CATEGORY_STATUS_DISABLE="1";
    public static final String CATEGORY_STATUS_ENABLE="0";

    /**
     * 友链审核，0审核通过，1审核未通过，2未审核
     */
    public static final int LINK_ACCEPT=0;
    public static final int LINK_REJECT=1;
    public static final int LINK_WAIT_CHECK=2;

    /**
     * 评论，根评论
     */
    public static final int COMMENT_ROOT=-1;

    /**
     * 评论type，1为link，0为article
     */
    public static final int ARTICLE_COMMENT =0;
    public static final int LINK_COMMENT =1;

    /**
     * redis热部署浏览量
     */
    public static final String ARTICLE_VIEW_COUNT="article-view-count:";

    /**
     * 用户角色
     */
    public static final String ADMIN="admin";
    public static final String USER="common";
    public static final String LINK="link";

    /**
     * 菜单的类型，C是菜单，M目录，F按钮
     */
    public static final String MENU_BUTTON="F";
    public static final String MENU_DIR="M";
    public static final String MENU_MENU="C";

    /**
     * role的status状态
     */
    public static final String ROLE_STATUS_ENABLE = "0";
}
