package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.azorom.chatgame.Pages.HomePage.HomePage;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.UserProfileResp;
import com.azorom.chatgame.Requests.User.UserRequests;
import com.azorom.chatgame.Storage.DrawableSets;

import java.util.Arrays;

public class MyProfile extends AppCompatActivity {

    UserRequests userRequestsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        userRequestsHandler = new UserRequests(this.getApplicationContext());

        setProfile();

        Button b = findViewById(R.id.EditProfile);
        b.setOnClickListener(v -> {
            Intent intent2 = new Intent(MyProfile.this, EditProfile.class);
            startActivity(intent2);
        });
        ImageButton s = findViewById(R.id.store);
        s.setOnClickListener(v -> {
            Intent intent3 = new Intent(MyProfile.this, Store.class);
            startActivity(intent3);
        });
        ImageButton h = findViewById(R.id.Home);
        s.setOnClickListener(v -> {
            Intent intent4 = new Intent(MyProfile.this, HomePage.class);
            startActivity(intent4);
        });
    }

    private void setProfile(){
        RequestResponse<UserProfileResp, HttpRequestError> resp = userRequestsHandler.getUserProfile();
        if(resp.error != null){
            Log.d("DEBUG", resp.error.message);
        }else if(resp.response != null){
            populateProfileFields(resp.response);
        }
    }

    private void populateProfileFields(UserProfileResp profileInfo){
        ImageView hatImg = findViewById(R.id.profileHat);
        hatImg.setImageResource(DrawableSets.hats.get(profileInfo.character.hat));

        ImageView headImg = findViewById(R.id.profileHead);
        headImg.setImageResource(DrawableSets.heads.get(profileInfo.character.head));

        TextView userNameLabel = findViewById(R.id.userNameLabel);
        userNameLabel.setText(profileInfo.userName);

        TextView bio = findViewById(R.id.bioLabel);
        bio.setText(profileInfo.bio);

        TextView email = findViewById(R.id.emailLabel);
        email.setText(profileInfo.email);

        TextView funFacts = findViewById(R.id.funfactLabel);
        funFacts.setText(profileInfo.funFacts[0]);

        TextView levelLabel = findViewById(R.id.levelLabel);
        levelLabel.setText("Level: " + String.valueOf(profileInfo.level.rank));

        TextView points = findViewById(R.id.points);
        points.setText(String.valueOf(profileInfo.points));

    }
}