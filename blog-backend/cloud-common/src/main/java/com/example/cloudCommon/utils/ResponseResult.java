package com.example.cloudCommon.utils;

import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult implements Serializable {
    private Integer code;
    private String msg;
    //这个是返回的数据
    private Object data;

    public ResponseResult(){
        code= AppHttpCodeEnum.SUCCESS.getCode();
        msg=AppHttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, Object data){
        this.code=code;
        this.data=data;
    }

    public ResponseResult(Integer code,String msg, Object data){
        this.code=code;
        this.data=data;
        this.msg=msg;
    }

    public static ResponseResult okResult(){
        ResponseResult result=new ResponseResult();
        return result.ok(200, null, "成功");
    }

    public static ResponseResult okResult(Integer code, String msg){
        ResponseResult result=new ResponseResult();
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(Object data){
        ResponseResult result=setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS,
                AppHttpCodeEnum.SUCCESS.getMsg());
        if(data!=null){
            result.data=data;
        }
        return result;
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums){
        return setAppHttpCodeEnum(enums);
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums,String msg){
        return setAppHttpCodeEnum(enums,msg);
    }

    public static ResponseResult errorResult(Integer code,String msg){
        return new ResponseResult(code,msg);
    }

    public static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums){
        return okResult(enums.getCode(),enums.getMsg());
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg){
        return okResult(enums.getCode(), msg);
    }

    public ResponseResult error(Integer code,String msg){
        this.code=code;
        this.msg=msg;
        return this;
    }

    public ResponseResult ok(Object data){
        this.data=data;
        return this;
    }

    public ResponseResult ok(Integer code, Object data){
        this.code=code;
        this.data=data;
        return this;
    }

    public ResponseResult ok(Integer code, Object data,String msg){
        this.code=code;
        this.data=data;
        this.msg=msg;
        return this;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code=code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

