package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.azorom.chatgame.Requests.Auth;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    Auth authHandler;

    public MainActivity(){
        authHandler = new Auth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResponseBody resp = null;
        try {
            resp = authHandler.login().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(resp == null){
            Log.d("REQUESTS", "null body");
            return;
        }
        try {
            Log.d("RESPONSE", resp.string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}