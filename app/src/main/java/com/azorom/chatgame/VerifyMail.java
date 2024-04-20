package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.azorom.chatgame.Requests.Auth.Auth;
import com.azorom.chatgame.Requests.Auth.AuthResponse;
import com.azorom.chatgame.Requests.Auth.VerificationCodeOBJ;
import com.azorom.chatgame.Requests.Auth.VerificationCodeResponse;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;

public class VerifyMail extends AppCompatActivity {
    Auth authHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mail);
        authHandler = new Auth(this.getApplicationContext());
        Intent intent4 = getIntent();
        String email = intent4.getStringExtra("email");
        Button confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(v -> {
            Verify(email);
        });
        Button resend = findViewById(R.id.Resend);
        resend.setOnClickListener(v -> {

        });
    }
    private void Verify(String email){

        int code=Integer.parseInt(((EditText)findViewById(R.id.code)).getText().toString());
        VerificationCodeOBJ verifObj = new VerificationCodeOBJ(code,email);
        RequestResponse<VerificationCodeResponse, HttpRequestError> resp = authHandler.verifyUser(verifObj);
        if(resp.error==null){
            Intent intent5= new Intent(VerifyMail.this, CharacterCustomization.class);
            startActivity(intent5);

        }
    }
}