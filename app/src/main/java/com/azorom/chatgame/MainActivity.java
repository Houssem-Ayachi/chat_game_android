package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

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
        setContentView(R.layout.activity_main);

        //something of a loading screen
        setHourGlassVid();

        StorageSingleton storageSingleton = StorageSingleton.instance;
        if(storageSingleton.getStorage() == null){
            storageSingleton.setStorage(this.getApplicationContext());
        }

        Storage storage = new Storage();
        String accessKey = storage.getKey();
        if(accessKey.equals("")){ //user is not logged in -> send to login page
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            this.finish();
        }else{ //user is logged in -> send to home page
            WSClient wsc = WSSingleton.getClient();

            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
            this.finish();
        }
    }

    private void setHourGlassVid(){
        VideoView videoView = findViewById(R.id.videoView);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.loading;
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
        videoView.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }
}