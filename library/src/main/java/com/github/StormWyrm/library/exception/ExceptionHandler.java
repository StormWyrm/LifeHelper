package com.github.StormWyrm.library.exception;

import android.net.ParseException;
import com.google.gson.JsonParseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import retrofit2.HttpException;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class ExceptionHandler {

    public static ResponseException handleException(Throwable throwable) {
        ResponseException reponseException = new ResponseException(throwable);
        if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            reponseException.setErrorCode(apiException.getErrorCode());
            reponseException.setErrorMessage(apiException.getErrorMessage());
        } else if (throwable instanceof HttpException) {
            HttpException e = (HttpException) throwable;
            reponseException.setErrorCode(e.code());
            reponseException.setErrorMessage("错误的请求或者服务器Error");
        } else if (throwable instanceof JsonParseException ||
                throwable instanceof JSONException ||
                throwable instanceof ParseException) {
            reponseException.setErrorCode(ResponseCode.PARSE_ERROR);
            reponseException.setErrorMessage("解析Json错误");
        } else if (throwable instanceof ConnectException) {
            reponseException.setErrorCode(ResponseCode.NET_ERROR);
            reponseException.setErrorMessage("连接失败");
        } else if (throwable instanceof ConnectTimeoutException ||
                throwable instanceof SocketTimeoutException) {
            reponseException.setErrorCode(ResponseCode.TIMEOUT_ERROR);
            reponseException.setErrorMessage("网络连接超时");
        } else if (throwable instanceof SSLHandshakeException) {
            reponseException.setErrorCode(ResponseCode.SSL_ERROR);
            reponseException.setErrorMessage("证书校验失败");
        } else {
            reponseException.setErrorCode(ResponseCode.UNKNOWN_ERROR);
            reponseException.setErrorMessage("未知错误");
        }
        return reponseException;
    }
}
