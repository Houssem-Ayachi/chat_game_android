package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.azorom.chatgame.Requests.Auth;
import com.azorom.chatgame.Requests.RequestsConstants;
import com.azorom.chatgame.Requests.UserRequest;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Auth authHandler;
    UserRequest userReqHandler;

    public MainActivity(){
        authHandler = new Auth();
        userReqHandler = new UserRequest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        signupUser();
//        verifyUser();
//        loginUser();
        updateCharacter();
    }

    private void loginUser(){
        //TODO: (login / password) arguments should be dynamic (coming from user input)
        Auth.LoginOBJ loginObj = new Auth.LoginOBJ("azorom", "ilovepizza");
        //NOTE: this object contains two of possible replies from the server
        //an Error object property called error (exists if it isn't null)
        //and a Respone object property called response (exists if it isn't null)
        //response should contain the necessary data acknowledgment data
        //response is a dynamic object based on the request being sent
        RequestsConstants.RequestResponse<Auth.AuthResponse> resp;
        try {
            resp = (RequestsConstants.RequestResponse<Auth.AuthResponse>)authHandler.login(loginObj).get();
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
        RequestsConstants.RequestResponse<Auth.AuthResponse> resp;
        try {
            resp = (RequestsConstants.RequestResponse<Auth.AuthResponse>)this.authHandler.signUp(signUpOBJ).get();
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
        RequestsConstants.RequestResponse<Auth.VerificationCodeResponse> resp;
        try {
            resp = (RequestsConstants.RequestResponse<Auth.VerificationCodeResponse>)this.authHandler.verifyUser(codeObj).get();
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

    private void updateCharacter(){
        UserRequest.UpdateCharacterOBJ characterOBJ = new UserRequest.UpdateCharacterOBJ(
                "6609660ddaf015cb8266e5ab",
                "hat3",
                "head1",
                "body4"
        );
        RequestsConstants.RequestResponse<RequestsConstants.BasicRequestResponse> resp;
        try {
            resp = (RequestsConstants.RequestResponse<RequestsConstants.BasicRequestResponse>)userReqHandler.updateCharacter(characterOBJ).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(resp.error != null){
            Log.d("REQUESTS", "error " + resp.error.message);
        }else if(resp.response != null){
            Log.d("REQUESTS", "response: " + resp.response.response);
        }else{
            Log.d("REQUESTS", "null body");
        }
    }
}