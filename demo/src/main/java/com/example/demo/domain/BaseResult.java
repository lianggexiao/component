package com.example.demo.domain;


public class BaseResult<T> {

    public static final int SUCCESS = 200;
    public static final int FAIL = 503;

    public static final String ACTION_SUCCESS = "success";
    public static final String ACTION_INFO = "info";
    public static final String ACTION_WARN = "warn";
    public static final String ACTION_ERROR = "error";

    private int state = SUCCESS;
    private String action = BaseResult.ACTION_SUCCESS;
    private String message;
    private T data;

    public static <T> BaseResult<T> buildRestResult(T data) {
        return new BaseResult<T>(data);
    }

    public static BaseResult<String> buildSuccessResult() {
        BaseResult<String> baseResult = new BaseResult<String>(BaseResult.ACTION_SUCCESS);
        baseResult.setAction(BaseResult.ACTION_SUCCESS);
        return baseResult;
    }

    public static BaseResult<String> info(String message) {
        BaseResult<String> baseResult = new BaseResult<String>(message);
        baseResult.setAction(BaseResult.ACTION_INFO);
        return baseResult;
    }

    public static BaseResult<String> warn(String message) {
        BaseResult<String> baseResult = new BaseResult<String>(message);
        baseResult.setAction(BaseResult.ACTION_WARN);
        return baseResult;
    }

    public static BaseResult<String> error(String message) {
        BaseResult<String> baseResult = new BaseResult<String>(message);
        baseResult.setState(FAIL);
        baseResult.setAction(BaseResult.ACTION_ERROR);
        return baseResult;
    }

    public BaseResult(T data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
