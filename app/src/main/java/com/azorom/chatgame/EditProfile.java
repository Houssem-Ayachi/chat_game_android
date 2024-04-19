package com.azorom.chatgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.azorom.chatgame.Requests.Auth.Auth;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent=getIntent();
        Button b = findViewById(R.id.Save);
        b.setOnClickListener(v -> {
            Save();
        });
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
        Intent i = new Intent(EditProfile.this, MyProfile.class);
        i.putExtra("username",username );
        i.putExtra("funfact",funfact);
        i.putExtra("bio",bio);
        startActivity(i);
    }
}