package io.grasspow.toolboxwebapi.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.grasspow.toolboxwebapi.util.GsonUtils;

public class DataMsg {
    private int code;
    private String msg;
    private JsonElement data;

    public DataMsg(String msg, Object data) {
        this(200,msg,data);
    }

    public DataMsg(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = JsonParser.parseString(GsonUtils.GSON.toJson(data));
    }

    public static DataMsg success(String msg, Object data) {
        return new DataMsg(200,msg, data);
    }

    public static DataMsg error_404(String msg, Object data) {
        return new DataMsg(404, msg, data);
    }

    public static DataMsg error_500(String msg, Object data) {
        return new DataMsg(500, msg, data);
    }

    @Override
    public String toString() {
        return "DataMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
