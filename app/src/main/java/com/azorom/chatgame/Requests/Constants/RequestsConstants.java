package com.azorom.chatgame.Requests.Constants;

import android.util.Log;

import com.azorom.chatgame.JSON.CustomJsonParser;
import com.azorom.chatgame.Storage.Storage;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class RequestsConstants {

    static OkHttpClient _client = new OkHttpClient();
    static Storage storage = new Storage();

    public static final String serverHost = "http://192.168.1.25:3000";

    private static String executeRequest(OkHttpClient client, Request req){
        String resJSON = "";
        ResponseBody respBody;
        try {
            Response resp = client.newCall(req).execute();
            respBody = resp.body();
        } catch (IOException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        try {
            if (respBody != null) {
                resJSON = respBody.string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resJSON;
    }

    public static <ResponseType> Object postRequest(
            String url,
            Object body,
            Class<ResponseType> responseClass
    ){
        String jsonString = CustomJsonParser.convertToJson(body);
        MediaType contentType = MediaType.get("application/json");
        RequestBody reqBody = RequestBody.create(jsonString, contentType);
        Request req = new Request.Builder()
                .url(url)
                .post(reqBody)
                .header("authorization", "Bearer " + storage.getKey())
                .build();
        String resJSON = RequestsConstants.executeRequest(_client, req);
        return CustomJsonParser.parseResponse(resJSON, responseClass, HttpRequestError.class);
    }

    public static <ResponseType> Object putRequest(
            String url,
            Object body,
            Class<ResponseType> responseClass
    ){
        String jsonString = CustomJsonParser.convertToJson(body);
        MediaType contentType = MediaType.get("application/json");
        RequestBody reqBody = RequestBody.create(jsonString, contentType);
        Request req = new Request.Builder()
                .url(url)
                .put(reqBody)
                .header("authorization", "Bearer " + storage.getKey())
                .build();
        String resJSON = executeRequest(_client, req);
        return CustomJsonParser.parseResponse(resJSON, responseClass, HttpRequestError.class);
    }

    public static <ResponseType> Object getRequest(
            String url,
            Class<ResponseType> responseClass
    ){
        String key = storage.getKey();
        Request req = new Request.Builder()
                .url(url)
                .get()
                .header("authorization", "Bearer " + key)
                .build();
        String resJSON = executeRequest(_client, req);
        return CustomJsonParser.parseResponse(resJSON, responseClass, HttpRequestError.class);
    }
}