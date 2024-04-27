package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.azorom.chatgame.Pages.HomePage.HomePage;
import com.azorom.chatgame.Requests.Constants.BasicRequestResponse;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.Character;
import com.azorom.chatgame.Requests.User.EditProfileOBJ;
import com.azorom.chatgame.Requests.User.UserProfileResp;
import com.azorom.chatgame.Requests.User.UserRequests;
import com.azorom.chatgame.Storage.DrawableSets;

public class EditProfile extends AppCompatActivity {

    UserRequests userRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userRequests = new UserRequests(this.getApplicationContext());

        init();

        Button b = findViewById(R.id.Save);
        b.setOnClickListener(v -> Save());
    }

    private void init(){
        UserProfileResp profile = getProfile();
        if(profile != null){
            populateFields(profile);
        }
    }

    private void Save(){
        String username=((EditText)findViewById(R.id.user)).getText().toString();
        String funfact=((EditText)findViewById(R.id.FunFact)).getText().toString();
        String bio=((EditText)findViewById(R.id.bio)).getText().toString();
        if(username.equals("")){
            displayError("user name field empty");
            return;
        }
        if(bio.equals("")){
            displayError("bio field empty");
            return;
        }
        if(funfact.equals("")){
            displayError("fun facts field empty");
            return;
        }
        RequestResponse<BasicRequestResponse, HttpRequestError> resp = userRequests.editProfile(new EditProfileOBJ(username, bio, new String[] {funfact}));
        if(resp.error != null){
            displayError(resp.error.message);
        }else if(resp.response != null){
            Intent i = new Intent(EditProfile.this, HomePage.class);
            startActivity(i);
            this.finish();
        }
    }

    private UserProfileResp getProfile(){
        RequestResponse<UserProfileResp, HttpRequestError> resp = userRequests.getUserProfile();
        if(resp.error != null){
            displayError(resp.error.message);
            return null;
        }else if(resp.response != null){
            return resp.response;
        }
        return null;
    }

    private void populateFields(UserProfileResp profile){
        changeCharacterImages(profile.character);

        if(profile.userName.equals("guest")){//user is visiting this page for the first time
            displayError("first time");
            return;
        }

        EditText userNameInput = findViewById(R.id.user);
        EditText bioInput = findViewById(R.id.bio);
        EditText funFact = findViewById(R.id.FunFact);

        userNameInput.setText(profile.userName);
        bioInput.setText(profile.bio);
        funFact.setText(profile.funFacts[0]);
    }

    private void changeCharacterImages(Character character){
        ImageView hatImg = findViewById(R.id.hatImg);
        hatImg.setImageResource(DrawableSets.hats.get(character.hat));
        ImageView headImg = findViewById(R.id.headImg);
        headImg.setImageResource(DrawableSets.heads.get(character.head));
    }

    private void displayError(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}