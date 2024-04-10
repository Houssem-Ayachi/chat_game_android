package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }
}