package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.azorom.chatgame.Requests.Constants.BasicRequestResponse;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.User.UpdateCharacterData;
import com.azorom.chatgame.Requests.User.UserRequests;
import com.azorom.chatgame.Storage.DrawableSets;

import java.util.ArrayList;

//TODO: add email regex

public class CharacterCustomization extends AppCompatActivity {

    UserRequests userReqHandler;

    int currHatIdx = 0;
    int currHeadIdx = 0;

    UpdateCharacterData characterObj;

    public CharacterCustomization(){


        //default values for the updateCharacterObj to be sent with the request
        characterObj = new UpdateCharacterData(
                "hat1",
                "head1"
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_customization);
        userReqHandler = new UserRequests(this.getApplicationContext());

        ImageView hatLeft = findViewById(R.id.hatLeft);
        hatLeft.setOnClickListener(v -> this.hatSwitch(-1));
        ImageView hatRight = findViewById(R.id.hatRight);
        hatRight.setOnClickListener(v -> this.hatSwitch(1));

        ImageView headLeft = findViewById(R.id.headLeft);
        headLeft.setOnClickListener(v -> this.headSwitch(-1));
        ImageView headRight = findViewById(R.id.headRight);
        headRight.setOnClickListener(v -> this.headSwitch(1));

        Button confirmSetBtn = findViewById(R.id.confirmset);
        confirmSetBtn.setOnClickListener(
                (v) -> this.confirmSet()
        );

    }

    private void hatSwitch(int direction){
        currHatIdx += direction;
        if (currHatIdx < 0){
            currHatIdx = DrawableSets.hats.keySet().size()-1;
        }else if(currHatIdx >= DrawableSets.hats.keySet().size()){
            currHatIdx = 0;
        }
        String hat = (new ArrayList<>(DrawableSets.hats.keySet())).get(currHatIdx);
        this.characterObj.hat = hat;
        int hatImgId = DrawableSets.hats.get(hat);
        ImageView hatImg = findViewById(R.id.hatImg);
        hatImg.setImageResource(hatImgId);
    }

    private void headSwitch(int direction){
        currHeadIdx += direction;
        if (currHeadIdx < 0){
            currHeadIdx = DrawableSets.heads.keySet().size()-1;
        }else if(currHeadIdx >= DrawableSets.heads.keySet().size()){
            currHeadIdx = 0;
        }
        String head = (new ArrayList<String>(DrawableSets.heads.keySet())).get(currHeadIdx);
        int headImgId = DrawableSets.heads.get(head);
        this.characterObj.head = head;
        ImageView headImg = findViewById(R.id.headImg);
        headImg.setImageResource(headImgId);
    }

    private void confirmSet(){
        RequestResponse<BasicRequestResponse, HttpRequestError> resp =
                this.userReqHandler.updateCharacter(this.characterObj);
        if(resp.error != null){
            displayError(resp.error.message);
            return;
        }
        Intent intent= new Intent(CharacterCustomization.this, EditProfile.class);
        startActivity(intent);
        this.finish();
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}