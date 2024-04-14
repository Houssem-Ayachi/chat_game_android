package com.azorom.chatgame.Requests.Auth;

import android.content.Context;

import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Auth {
    private final ExecutorService _executor;
    private final Context context;

    public Auth(Context context){
        _executor = Executors.newSingleThreadExecutor();
        this.context = context;
    }

    private Future<Object> signUp(SignUpOBJ signUpOBJ){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/signup",
                signUpOBJ,
                AuthResponse.class
                )
        );
    }

    private Future<Object> login(LoginOBJ loginObj){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/login",
                loginObj,
                AuthResponse.class
                )
        );
    }

    private Future<Object> sendverificationRequest(VerificationCodeOBJ codeOBJ){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/verify",
                codeOBJ,
                VerificationCodeResponse.class
                )
        );
    }

    public RequestResponse<AuthResponse, HttpRequestError> loginUser(LoginOBJ loginObj){
        //NOTE: this object contains two of possible replies from the server
        //an Error object property called error (exists if it isn't null)
        //and a Respone object property called response (exists if it isn't null)
        //response should contain the necessary data acknowledgment data
        //response is a dynamic object based on the request being sent
        RequestResponse<AuthResponse, HttpRequestError> resp;
        try {
            resp = (RequestResponse<AuthResponse, HttpRequestError>)this.login(loginObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<AuthResponse, HttpRequestError> signupUser(SignUpOBJ signUpOBJ){
        RequestResponse<AuthResponse, HttpRequestError> resp;
        try {
            resp = (RequestResponse<AuthResponse, HttpRequestError>)this.signUp(signUpOBJ).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<VerificationCodeResponse, HttpRequestError> verifyUser(VerificationCodeOBJ codeObj){
        RequestResponse<VerificationCodeResponse, HttpRequestError> resp;
        try {
            resp = (RequestResponse<VerificationCodeResponse, HttpRequestError>)this.sendverificationRequest(codeObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}