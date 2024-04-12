package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.azorom.chatgame.Storage.Storage;
import com.azorom.chatgame.Storage.StorageSingleton;

public class MainActivity extends AppCompatActivity {

    //IMPORTANT NOTE: this activity shouldn't have a ui related to it
    //                it is only served as an entry point for the application
    //                it checks for stuff like open services and the user statuses (isloggedin/isverified...)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StorageSingleton storageSingleton = StorageSingleton.instance;
        if(storageSingleton.getStorage() == null){
            storageSingleton.setStorage(this.getApplicationContext());
        }
        Storage storage = new Storage();
        storage.saveKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NjE3ZGZiZmVkMGMyYzlhZGEwODMyYjUiLCJjcmVhdGVkQXQiOiIyMDI0LTA0LTExVDEzOjAzOjU5Ljg3NFoiLCJpYXQiOjE3MTI4NDA2Mzl9.WZmMeheFco4-Af0p-U-Kcb1i_YKmh-9m_p5x0S7-dWQ");
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
        this.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }
}