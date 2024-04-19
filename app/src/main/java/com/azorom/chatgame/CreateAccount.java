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
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Storage.Storage;

public class CreateAccount extends AppCompatActivity {
    Auth authHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Intent intent2 = getIntent();
        authHandler = new Auth(this.getApplicationContext());
        Button c = findViewById(R.id.Create);
        c.setOnClickListener(v -> {
            Create();
        });
    }
    private void Create(){
        String username=((EditText)findViewById(R.id.name)).getText().toString();
        String email=((EditText)findViewById(R.id.Email)).getText().toString();
        String password=((EditText)findViewById(R.id.Password)).getText().toString();
        String ConfirmPassword=((EditText)findViewById(R.id.confirmpassword)).getText().toString();

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
        if(ConfirmPassword.equals("")){
            //Not allowed
            return;
        }
        if(!password.equals(ConfirmPassword)){
            //Not allowed
            return;
        }
        SignUpOBJ signupObj = new SignUpOBJ(username,email,password);
        RequestResponse<AuthResponse> resp = authHandler.signUp(signupObj);
        if(resp.response != null){
            Intent intent3 = new Intent(CreateAccount.this, VerifyMail.class);
            startActivity(intent3);
        }



    }

}