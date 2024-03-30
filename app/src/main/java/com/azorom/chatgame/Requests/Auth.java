package com.azorom.chatgame.Requests;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Auth {

    private class MockLoginOBJ{
        public String login;
        public String password;
        public MockLoginOBJ(String login, String password){
            this.login = login;
            this.password = password;
        }
    }

    OkHttpClient _client;

    private final ExecutorService _executor;

    public Auth(){
        _client = new OkHttpClient();
        _executor = Executors.newSingleThreadExecutor();
    }

    private ResponseBody sendLogin(){
        URL url;
        try {
            url = new URL("http://192.168.56.1:3000/api/auth/login");
        }catch (MalformedURLException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        ObjectMapper objMapper = new ObjectMapper();
        MockLoginOBJ login = new MockLoginOBJ("azorom", "ilovepizza");
        String loginJSON = "";
        try {
            loginJSON = objMapper.writeValueAsString(login);
        } catch (JsonProcessingException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        MediaType contentType = MediaType.get("application/json");
        RequestBody body = RequestBody.create(loginJSON, contentType);
        Request req = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try{
            Response resp = _client.newCall(req).execute();
            return resp.body();
        }catch(IOException e){
            Log.d("REQUESTS", e.toString());
        }
        return null;
    }
    public Future<ResponseBody> login(){
        return _executor.submit(this::sendLogin);
    }
}
