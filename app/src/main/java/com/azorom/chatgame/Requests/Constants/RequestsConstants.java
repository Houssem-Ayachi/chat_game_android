package com.azorom.chatgame.Requests.Constants;

import android.util.Log;

import com.azorom.chatgame.Storage.Storage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

//TODO: ADD BETTER ERROR HANDLING WHILE PARSING JSON STRINGS

public abstract class RequestsConstants {

    public static final String serverHost = "http://192.168.1.17:3000";


    public static <ResponseType> Object postRequest(
            String url,
            Object body,
            Class<ResponseType> responseClass
    ){
        OkHttpClient _client = new OkHttpClient();
        ObjectMapper objMapper = new ObjectMapper();
        Storage storage = new Storage();
        String jsonString = "";
        try {
            jsonString = objMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        MediaType contentType = MediaType.get("application/json");
        RequestBody reqBody = RequestBody.create(jsonString, contentType);
        Request req = new Request.Builder()
                .url(url)
                .post(reqBody)
                .header("authorization", "Bearer " + storage.getKey())
                .build();
        String resJSON = "";
        ResponseBody respBody = null;
        try {
            Response resp = _client.newCall(req).execute();
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
        ResponseType response = null;
        RequestError error = null;
        try {
            response = objMapper.readValue(resJSON, responseClass);
        } catch (UnrecognizedPropertyException e){
            try {
                error = objMapper.readValue(resJSON, RequestError.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new RequestResponse<>(response, error);
    }

    public static <ResponseType> Object putRequest(
            String url,
            Object body,
            Class<ResponseType> responseClass
    ){
        OkHttpClient _client = new OkHttpClient();
        ObjectMapper objMapper = new ObjectMapper();
        Storage storage = new Storage();
        String jsonString = "";
        try {
            jsonString = objMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        MediaType contentType = MediaType.get("application/json");
        RequestBody reqBody = RequestBody.create(jsonString, contentType);
        Request req = new Request.Builder()
                .url(url)
                .put(reqBody)
                .header("authorization", "Bearer " + storage.getKey())
                .build();
        String resJSON = "";
        ResponseBody respBody = null;
        try {
            Response resp = _client.newCall(req).execute();
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
        ResponseType response = null;
        RequestError error = null;
        try {
            response = objMapper.readValue(resJSON, responseClass);
        } catch (UnrecognizedPropertyException e){
            try {
                error = objMapper.readValue(resJSON, RequestError.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new RequestResponse<>(response, error);
    }

    public static <ResponseType> Object getRequest(
            String url,
            Class<ResponseType> responseClass
    ){
        OkHttpClient _client = new OkHttpClient();
        ObjectMapper objMapper = new ObjectMapper();
        Storage storage = new Storage();
        String key = storage.getKey();
        Request req = new Request.Builder()
                .url(url)
                .get()
                .header("authorization", "Bearer " + key)
                .build();
        String resJSON = "";
        ResponseBody respBody = null;
        try {
            Response resp = _client.newCall(req).execute();
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
        ResponseType response = null;
        RequestError error = null;
        try {
            response = objMapper.readValue(resJSON, responseClass);
        } catch (UnrecognizedPropertyException e){
            try {
                error = objMapper.readValue(resJSON, RequestError.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new RequestResponse<>(response, error);
    }

}
