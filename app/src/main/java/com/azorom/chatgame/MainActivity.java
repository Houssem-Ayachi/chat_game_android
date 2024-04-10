package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.azorom.chatgame.Requests.Auth.Auth;
import com.azorom.chatgame.Requests.Auth.AuthResponse;
import com.azorom.chatgame.Requests.Auth.LoginOBJ;
import com.azorom.chatgame.Requests.Auth.SignUpOBJ;
import com.azorom.chatgame.Requests.Auth.VerificationCodeOBJ;
import com.azorom.chatgame.Requests.Auth.VerificationCodeResponse;
import com.azorom.chatgame.Requests.Constants.BasicRequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;
import com.azorom.chatgame.Requests.User.EditProfileOBJ;
import com.azorom.chatgame.Requests.User.UpdateCharacterData;
import com.azorom.chatgame.Requests.User.UserRequest;
import com.azorom.chatgame.Storage.Storage;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Auth authHandler;
    UserRequest userReqHandler;
    Storage storage;

    //IMPORTANT NOTE: this activity shouldn't have a ui related to it
    //                it is only served as an entry point for the application
    //                it checks for stuff like open services and the user statuses (isloggedin/isverified...)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authHandler = new Auth(this.getApplicationContext());
        userReqHandler = new UserRequest(this.getApplicationContext());
        storage = new Storage(this.getApplicationContext());

        Intent i = new Intent(this, CharacterCustomization.class);
        startActivity(i);

        storage.saveKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NjExMzFjODg0MmYzNGM4OGI0MzI0ODAiLCJjcmVhdGVkQXQiOiIyMDI0LTA0LTA3VDE5OjI2OjM3LjAyM1oiLCJpYXQiOjE3MTI1MTc5OTd9.fb2n4dbV-zB66LF1XvKOzwLP7jSwoEWjwSWzjXGJzOc");
    }
}