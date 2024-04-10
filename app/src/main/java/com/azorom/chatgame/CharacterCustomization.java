package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.azorom.chatgame.Requests.User.UpdateCharacterData;
import com.azorom.chatgame.Requests.User.UserRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CharacterCustomization extends AppCompatActivity {

    UserRequest userReqHandler;

    Map<String, Integer> hats;
    int currHatIdx = 0;
    Map<String, Integer> heads;
    int currHeadIdx = 0;

    UpdateCharacterData characterObj;

    public CharacterCustomization(){

        hats = new HashMap<>();
        hats.put("hat1", R.drawable.hat1);
        hats.put("hat2", R.drawable.hat2);
        hats.put("hat3", R.drawable.hat3);

        heads = new HashMap<>();
        heads.put("hat1", R.drawable.head1);
        heads.put("hat2", R.drawable.head2);
        heads.put("hat3", R.drawable.head3);

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
        userReqHandler = new UserRequest(this.getApplicationContext());

        Button hatLeft = findViewById(R.id.hatLeft);
        hatLeft.setOnClickListener(v -> this.hatSwitch(-1));
        Button hatRight = findViewById(R.id.hatRight);
        hatRight.setOnClickListener(v -> this.hatSwitch(1));

        Button headLeft = findViewById(R.id.headLeft);
        headLeft.setOnClickListener(v -> this.headSwitch(-1));
        Button headRight = findViewById(R.id.headRight);
        headRight.setOnClickListener(v -> this.headSwitch(1));

        Button confirmSetBtn = findViewById(R.id.confirmSet);
        confirmSetBtn.setOnClickListener(
                (v) -> this.userReqHandler.updateCharacter(this.characterObj)
        );
    }

    private void hatSwitch(int direction){
        currHatIdx += direction;
        if (currHatIdx < 0){
            currHatIdx = hats.keySet().size()-1;
        }else if(currHatIdx >= hats.keySet().size()){
            currHatIdx = 0;
        }
        String hat = (new ArrayList<>(hats.keySet())).get(currHatIdx);
        this.characterObj.hat = hat;
        int hatImgId = hats.get(hat);
        ImageView hatImg = findViewById(R.id.hatImg);
        hatImg.setImageResource(hatImgId);
    }

    private void headSwitch(int direction){
        currHeadIdx += direction;
        if (currHeadIdx < 0){
            currHeadIdx = heads.keySet().size()-1;
        }else if(currHeadIdx >= heads.keySet().size()){
            currHeadIdx = 0;
        }
        String head = (new ArrayList<String>(heads.keySet())).get(currHeadIdx);
        int headImgId = heads.get(head);
        this.characterObj.head = head;
        ImageView headImg = findViewById(R.id.headImg);
        headImg.setImageResource(headImgId);
    }
}