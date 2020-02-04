package com.yefeng.util;

/**
 * @Description:
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */
public class JsonResult {

    private String msg;

    private Object object;

    private int status;

    private String ok;

    public static JsonResult build(String msg,int status,Object object) {
        return new JsonResult(msg,object,status);
    }

    public static JsonResult ok() {
        return new JsonResult(null,null,200);
    }

    public static JsonResult ok(Object object) {
        return new JsonResult(null,object,200);
    }

    public static JsonResult sentMessage(String msg) {
        return new JsonResult(msg,null,200);
    }

    public static JsonResult errorMessage(String msg) {
        return new JsonResult(msg,null,500);
    }

    public static JsonResult errorMsgMap(Object object) {
        return new JsonResult("error",object,501);
    }

    public static JsonResult errorTokenMsg(String msg) {
        return new JsonResult(msg,null,500);
    }

    public JsonResult() {}

    public JsonResult(Object object) {
        setObject(object);
    }

    public JsonResult(String msg, Object object, int status) {
        this.msg = msg;
        this.object = object;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", status=" + status +
                ", ok='" + ok + '\'' +
                '}';
    }

}
