package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        c.setOnClickListener(v -> Create());
    }

    private void Create(){
        String email=((EditText)findViewById(R.id.Email)).getText().toString();
        String password=((EditText)findViewById(R.id.Password)).getText().toString();
        String confirmPassword=((EditText)findViewById(R.id.confirmpassword)).getText().toString();

        if(email.equals("")){
            displayError("email field empty");
            return;
        }

        if(password.equals("")){
            displayError("password field empty");
            return;
        }

        if(confirmPassword.equals("")){
            displayError("confirm password field empty");
            return;
        }

        if(!password.equals(confirmPassword)){
            displayError("confirm password should be the same as password");
            return;
        }

        SignUpOBJ signupObj = new SignUpOBJ(email,password);
        RequestResponse<AuthResponse, HttpRequestError> resp = authHandler.signupUser(signupObj);
        if(resp.response != null){
            storage.saveKey(resp.response.access_key);
            Intent intent3 = new Intent(CreateAccount.this, VerifyMail.class);
            intent3.putExtra("email", email);
            startActivity(intent3);
            this.finish();
        }else if(resp.error != null){
            displayError(resp.error.message);
        }
    }

    private void displayError(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}