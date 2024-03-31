package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.azorom.chatgame.Requests.Auth;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Auth authHandler;

    public MainActivity(){
        authHandler = new Auth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        signupUser();
//        verifyUser();
//        loginUser();
    }

    private void loginUser(){
        //TODO: (login / password) arguments should be dynamic (coming from user input)
        Auth.LoginOBJ loginObj = new Auth.LoginOBJ("azorom", "ilovepizza");
        //NOTE: this object contains two of possible replies from the server
        //an Error object property called error (exists if it isn't null)
        //and a Respone object property called response (exists if it isn't null)
        //response should contain the necessary data acknowledgment data
        //response is a dynamic object based on the request being sent
        Auth.AuthRequestResponse<Auth.AuthResponse> resp;
        try {
            resp = (Auth.AuthRequestResponse<Auth.AuthResponse>)authHandler.login(loginObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(resp.error != null){
            Log.d("REQUESTS", "error " + resp.error.message);
        }else if(resp.response != null){
            Log.d("REQUESTS", "ACCESS KEY: " + resp.response.access_key);
        }else{
            Log.d("REQUESTS", "null body");
        }
    }

    private void signupUser(){
        //TODO: (userName/email/password) arguments should be dynamic (coming from user input)
        Auth.SignUpOBJ signUpOBJ = new Auth.SignUpOBJ(
                "azorom",
                "mido.ayachi@gmail.com",
                "ilovepizzza"
        );
        Auth.AuthRequestResponse<Auth.AuthResponse> resp;
        try {
            resp = (Auth.AuthRequestResponse<Auth.AuthResponse>)this.authHandler.signUp(signUpOBJ).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(resp.error != null){
            Log.d("REQUESTS", "error " + resp.error.message);
        }else if(resp.response != null){
            Log.d("REQUESTS", "ACCESS KEY: " + resp.response.access_key);
        }else{
            Log.d("REQUESTS", "null body");
        }
    }

    private void verifyUser(){
        //TODO: (code/email) arguments should be dynamic (coming from user input)
        Auth.VerificationCodeOBJ codeObj = new Auth.VerificationCodeOBJ(
                941,
                "mido.ayachi@gmail.com"
        );
        Auth.AuthRequestResponse<Auth.VerificationCodeResponse> resp;
        try {
            resp = (Auth.AuthRequestResponse<Auth.VerificationCodeResponse>)this.authHandler.verifyUser(codeObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(resp.error != null){
            Log.d("REQUESTS", "error " + resp.error.message);
        }else if(resp.response != null){
            Log.d("REQUESTS", "message " + resp.response.response);
        }else{
            Log.d("REQUESTS", "null body");
        }
    }
}