package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.azorom.chatgame.Pages.HomePage.HomePage;
import com.azorom.chatgame.Storage.Storage;
import com.azorom.chatgame.Storage.StorageSingleton;
import com.azorom.chatgame.WS.WSClient;
import com.azorom.chatgame.WS.WSSingleton;

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

        //NOTE: STORING KEY MANUALLY FOR DEBUGGING PURPOSES
        Storage storage = new Storage();
//        eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NjE3ZGZiZmVkMGMyYzlhZGEwODMyYjUiLCJjcmVhdGVkQXQiOiIyMDI0LTA0LTExVDEzOjAzOjU5Ljg3NFoiLCJpYXQiOjE3MTI4NDA2Mzl9.WZmMeheFco4-Af0p-U-Kcb1i_YKmh-9m_p5x0S7-dWQ
        storage.saveKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NjE3ZGUwZWJjYTliMmM2ZjEwYmYxY2YiLCJjcmVhdGVkQXQiOiIyMDI0LTA0LTEyVDIzOjAyOjIwLjA2M1oiLCJpYXQiOjE3MTI5NjI5NDB9.Jc5uDGHIcnKBi9wtHhc4tYEziCzCaH8YAyKlrxcUNYw");

        WSClient wsc = WSSingleton.getClient();

        Intent i = new Intent(this, Waiting.class);
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