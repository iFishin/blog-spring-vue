package com.fish.vo;

public class ResultVO<T> {
    private String message;
    private String flag;
    private T data;

    //操作成功返回结果集
    public static <T> ResultVO<T> ok(String message, T t) {
        ResultVO<T> model = new ResultVO<>();
        model.setFlag("1");
        model.setMessage(message);
        model.setData(t);
        return model;
    }
    //操作成功不返回结果集
    public static ResultVO ok(String message) {
        ResultVO model = new ResultVO();
        model.setFlag("1");
        model.setMessage(message);
        return model;
    }
    //操作失败
    public static ResultVO fail(String message) {
        ResultVO model = new ResultVO();
        model.setFlag("0");
        model.setMessage(message);
        return model;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "message='" + message + '\'' +
                ", flag='" + flag + '\'' +
                ", data=" + data +
                '}';
    }
}
