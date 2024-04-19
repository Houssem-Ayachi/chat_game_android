package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.azorom.chatgame.Requests.Auth.Auth;
import com.azorom.chatgame.Requests.Auth.AuthResponse;
import com.azorom.chatgame.Requests.Auth.LoginOBJ;
import com.azorom.chatgame.Requests.Auth.SignUpOBJ;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Storage.Storage;

public class CreateAccount extends AppCompatActivity {
    Auth authHandler;
    Storage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        authHandler = new Auth(this.getApplicationContext());
        storage=new Storage();
        Button c = findViewById(R.id.Create);
        c.setOnClickListener(v -> {
            Create();
        });
    }
    private void Create(){
        String username=((EditText)findViewById(R.id.name)).getText().toString();
        String email=((EditText)findViewById(R.id.Email)).getText().toString();
        String password=((EditText)findViewById(R.id.Password)).getText().toString();
        String confirmPassword=((EditText)findViewById(R.id.confirmpassword)).getText().toString();

        if(username.equals("")){
            //Not allowed
            return;
        }
        if(password.equals("")){
            //Not allowed
            return;
        }
        if(email.equals("")){
            //Not allowed
            return;
        }
        if(confirmPassword.equals("")){
            //Not allowed
            return;
        }
        if(!password.equals(confirmPassword)){
            //Not allowed
            return;
        }
        SignUpOBJ signupObj = new SignUpOBJ(username,email,password);
        RequestResponse<AuthResponse, HttpRequestError> resp = authHandler.signupUser(signupObj);
        if(resp.response != null){
            storage.saveKey(resp.response.access_key);
            Intent intent3 = new Intent(CreateAccount.this, VerifyMail.class);
            intent3.putExtra("email", email);
            startActivity(intent3);
        }



    }

}