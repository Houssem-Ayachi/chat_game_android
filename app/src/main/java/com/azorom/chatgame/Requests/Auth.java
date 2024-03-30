package com.azorom.chatgame.Requests;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

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

    public static class LoginOBJ{
        public String login;
        public String password;
        public LoginOBJ(String login, String password){
            this.login = login;
            this.password = password;
        }
    }

    public static class SignUpOBJ{
        public String userName;
        public String email;
        public String password;
        public SignUpOBJ(String userName, String email, String password){
            this.userName = userName;
            this.email = email;
            this.password = password;
        }
    }

    public static class VerificationCodeOBJ{
        public int code;
        public String email;
        public VerificationCodeOBJ(int code, String email){
            this.code = code;
            this.email = email;
        }
    }

    public static class AuthResponse {
        public String access_key;
        public AuthResponse(@JsonProperty("access_key") String accessKey){
            this.access_key = accessKey;
        }
    }

    public static class VerificationCodeResponse{
        public String response;
        public VerificationCodeResponse(@JsonProperty("response") String response){
            this.response = response;
        }
    }

    public static class RequestError{
        public String message;
        public String error;
        public int statusCode;
        public RequestError(
                @JsonProperty("message") String message,
                @JsonProperty("error") String error,
                @JsonProperty("statusCode") int statusCode
        ){
            this.message = message;
            this.error = error;
            this.statusCode = statusCode;
        }
    }

    public static class AuthRequestResponse<ResponseType> {
        public ResponseType response;
        public RequestError error;
        public AuthRequestResponse(ResponseType response, RequestError error){
            this.response = response;
            this.error = error;
        }
    }

    OkHttpClient _client;

    private final ExecutorService _executor;

    public Auth(){
        _client = new OkHttpClient();
        _executor = Executors.newSingleThreadExecutor();
    }

    private AuthRequestResponse<AuthResponse> sendLogin(LoginOBJ login) {
        URL url;
        try {
            url = new URL(RequestsConstants.serverHost + "/api/auth/login");
        } catch (MalformedURLException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        ObjectMapper objMapper = new ObjectMapper();
        String loginJSON = "";
        try {
            loginJSON = objMapper.writeValueAsString(login);
        } catch (JsonProcessingException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        MediaType contentType = MediaType.get("application/json");
        RequestBody reqBody = RequestBody.create(loginJSON, contentType);
        Request req = new Request.Builder()
                .url(url)
                .post(reqBody)
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
        AuthResponse loginResp = null;
        RequestError error = null;
        try {
            loginResp = objMapper.readValue(resJSON, AuthResponse.class);
        } catch (UnrecognizedPropertyException e){
            try {
                error = objMapper.readValue(resJSON, RequestError.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
        }
        return new AuthRequestResponse<>(loginResp, error);
    }

    private AuthRequestResponse<AuthResponse> sendSignup(SignUpOBJ signup){
        URL url;
        try {
            url = new URL(RequestsConstants.serverHost + "/api/auth/signup");
        } catch (MalformedURLException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        ObjectMapper objMapper = new ObjectMapper();
        String loginJSON = "";
        try {
            loginJSON = objMapper.writeValueAsString(signup);
        } catch (JsonProcessingException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        MediaType contentType = MediaType.get("application/json");
        RequestBody reqBody = RequestBody.create(loginJSON, contentType);
        Request req = new Request.Builder()
                .url(url)
                .post(reqBody)
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
        AuthResponse signupResp = null;
        RequestError error = null;
        try {
            signupResp = objMapper.readValue(resJSON, AuthResponse.class);
        } catch (UnrecognizedPropertyException e){
            try {
                error = objMapper.readValue(resJSON, RequestError.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new AuthRequestResponse<>(signupResp, error);
    }

    private AuthRequestResponse<VerificationCodeResponse> sendVerificationCode(VerificationCodeOBJ codeObj){
        URL url;
        try {
            url = new URL(RequestsConstants.serverHost + "/api/auth/verify");
        } catch (MalformedURLException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        ObjectMapper objMapper = new ObjectMapper();
        String loginJSON = "";
        try {
            loginJSON = objMapper.writeValueAsString(codeObj);
        } catch (JsonProcessingException e) {
            Log.d("REQUESTS", e.toString());
            return null;
        }
        MediaType contentType = MediaType.get("application/json");
        RequestBody reqBody = RequestBody.create(loginJSON, contentType);
        Request req = new Request.Builder()
                .url(url)
                .post(reqBody)
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
        VerificationCodeResponse signupResp = null;
        RequestError error = null;
        try {
            signupResp = objMapper.readValue(resJSON, VerificationCodeResponse.class);
        } catch (UnrecognizedPropertyException e){
            try {
                error = objMapper.readValue(resJSON, RequestError.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new AuthRequestResponse<>(signupResp, error);
    }

    public Future<AuthRequestResponse<AuthResponse>> signUp(SignUpOBJ signUpOBJ){
        return _executor.submit(() -> this.sendSignup(signUpOBJ));
    }

    public Future<AuthRequestResponse<AuthResponse>> login(LoginOBJ loginObj){
        return _executor.submit(() -> this.sendLogin(loginObj));
    }

    public Future<AuthRequestResponse<VerificationCodeResponse>> verifyUser(VerificationCodeOBJ codeOBJ){
        return _executor.submit(() -> this.sendVerificationCode(codeOBJ));
    }
}