package com.azorom.chatgame.Requests.Auth;

import android.content.Context;
import android.util.Log;

import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;
import com.azorom.chatgame.Storage.Storage;

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
                AuthResponse.class,
                context
                )
        );
    }

    private Future<Object> login(LoginOBJ loginObj){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/login",
                loginObj,
                AuthResponse.class,
                context
                )
        );
    }

    private Future<Object> sendverificationRequest(VerificationCodeOBJ codeOBJ){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/auth/verify",
                codeOBJ,
                VerificationCodeResponse.class,
                context
                )
        );
    }

    public RequestResponse<AuthResponse> loginUser(LoginOBJ loginObj){
        //NOTE: this object contains two of possible replies from the server
        //an Error object property called error (exists if it isn't null)
        //and a Respone object property called response (exists if it isn't null)
        //response should contain the necessary data acknowledgment data
        //response is a dynamic object based on the request being sent
        RequestResponse<AuthResponse> resp;
        try {
            resp = (RequestResponse<AuthResponse>)this.login(loginObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<AuthResponse> signupUser(SignUpOBJ signUpOBJ){
        RequestResponse<AuthResponse> resp;
        try {
            resp = (RequestResponse<AuthResponse>)this.signUp(signUpOBJ).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<VerificationCodeResponse> verifyUser(VerificationCodeOBJ codeObj){
        RequestResponse<VerificationCodeResponse> resp;
        try {
            resp = (RequestResponse<VerificationCodeResponse>)this.sendverificationRequest(codeObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}