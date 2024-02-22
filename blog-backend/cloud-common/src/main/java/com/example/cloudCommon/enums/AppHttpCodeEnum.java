package com.example.cloudCommon.enums;

public enum AppHttpCodeEnum {
    //成功
    SUCCESS(200,"操作成功"),

    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    ARTICLE_NO_FOUND(404,"文章未找到"),
    CATEGORY_NO_FOUND(404,"分类未找到"),
    NO_FOUND(404,"请求的资源未找到"),
    USERNAME_EXIST(405,"用户名已存在"),
    PHONE_NUMBER_EXIST(405,"手机号已存在"),
    EMAIL_EXIST(405,"邮箱已存在"),
    EMAIL_ERROR(406,"邮箱格式错误"),
    DTO_NULL(406,"传输的数据为空"),
    FORM_ERROR(406,"修改菜单'写博文'失败，上级菜单不能选择自己"),
    DELETE_ERROR(406,"存在子菜单不允许删除"),
    IMAGE_SUFFIX_ERROR(406,"文件类别错误，请上传jpg和png的图片"),
    REQUIRE_USERNAME(406,"必需填写用户名"),
    COMMENT_NOT_NULL(406,"评论不能为空"),
    LOGIN_ERROR(406,"用户名或密码错误"),
    AUTHENTICATE_ERROR(406,"用户认证失败"),
    SYSTEM_ERROR(500,"出现未知异常"),
    REGISTER_FAIL(505, "注册时服务器异常");

    Integer code;
    String msg;

    AppHttpCodeEnum(Integer code, String errorMessage){
        this.code=code;
        this.msg=errorMessage;
    }

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
