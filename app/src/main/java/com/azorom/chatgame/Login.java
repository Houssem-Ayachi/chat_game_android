package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.azorom.chatgame.Pages.HomePage.HomePage;
import com.azorom.chatgame.Requests.Auth.Auth;
import com.azorom.chatgame.Requests.Auth.AuthResponse;
import com.azorom.chatgame.Requests.Auth.LoginOBJ;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Storage.Storage;

public class Login extends AppCompatActivity {
    Auth authHandler;
    Storage storage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        storage=new Storage();
        authHandler = new Auth(this.getApplicationContext());

        Button b = findViewById(R.id.login);
        b.setOnClickListener(v -> login());

        Button btn=findViewById(R.id.createaccount);
        btn.setOnClickListener(v -> {
            Intent intent1 = new Intent(Login.this, CreateAccount.class);
           startActivity(intent1);
        });
    }

    private void login(){
        EditText userNameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password1);
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(userName.equals("")){
            //Not allowed
            return;
        }
        if(password.equals("")){
            //Not allowed
            return;
        }
        LoginOBJ loginObj = new LoginOBJ(userName, password);
        RequestResponse<AuthResponse, HttpRequestError> resp = authHandler.loginUser(loginObj);
        if(resp.response != null){
            storage.saveKey(resp.response.access_key);
            Intent i = new Intent(Login.this, HomePage.class);
            startActivity(i);
            this.finish();
        }else if(resp.error != null){
            Log.d("DEBUG", "error: " + resp.error.message);
        }
    }
}