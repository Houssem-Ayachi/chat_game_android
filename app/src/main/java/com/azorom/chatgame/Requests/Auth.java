package com.azorom.chatgame.Requests;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private final ExecutorService _executor;

    public Auth(){
        _executor = Executors.newSingleThreadExecutor();
    }

    public Future<Object> signUp(SignUpOBJ signUpOBJ){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/signup",
                signUpOBJ,
                AuthResponse.class
                )
        );
    }

    public Future<Object> login(LoginOBJ loginObj){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/login",
                loginObj,
                AuthResponse.class
                )
        );
    }

    public Future<Object> verifyUser(VerificationCodeOBJ codeOBJ){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/verify",
                codeOBJ,
                VerificationCodeResponse.class
                )
        );
    }
}