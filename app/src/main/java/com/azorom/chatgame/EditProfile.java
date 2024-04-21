package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.azorom.chatgame.Pages.HomePage.HomePage;
import com.azorom.chatgame.Requests.Auth.Auth;
import com.azorom.chatgame.Requests.Constants.BasicRequestResponse;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.EditProfileOBJ;
import com.azorom.chatgame.Requests.User.UserRequests;

//TODO: HANDLE REQUEST ERRORS

public class EditProfile extends AppCompatActivity {

    UserRequests userRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userRequests = new UserRequests(this.getApplicationContext());

        Button b = findViewById(R.id.Save);
        b.setOnClickListener(v -> Save());
    }
    private void Save(){
        String username=((EditText)findViewById(R.id.user)).getText().toString();
        String funfact=((EditText)findViewById(R.id.FunFact)).getText().toString();
        String bio=((EditText)findViewById(R.id.bio)).getText().toString();
        if(username.equals("")){
            //Not allowed
            return;
        }
        if(funfact.equals("")){
            //Not allowed
            return;
        }
        if(bio.equals("")){
            //Not allowed
            return;
        }
        RequestResponse<BasicRequestResponse, HttpRequestError> resp = userRequests.editProfile(new EditProfileOBJ(username, bio, new String[] {funfact}));
        if(resp.error != null){

        }else if(resp.response != null){
            Intent i = new Intent(EditProfile.this, HomePage.class);
            startActivity(i);
            this.finish();
        }
    }
}