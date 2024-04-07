package com.azorom.chatgame.Requests;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRequest {

    public static class UpdateCharacterOBJ {
        public String hat;
        public String head;

        public UpdateCharacterOBJ(String hat, String head){
            this.hat = hat;
            this.head = head;
        }
    }
    public static class EditProfileOBJ{
        public String userName;
        public String bio;
        public String[] funFacts;
        public EditProfileOBJ(String userName, String bio, String[] funFacts){
            this.userName = userName;
            this.bio = bio;
            this.funFacts = funFacts;
        }
    }
    private final ExecutorService _executor;
    Context context;

    public UserRequest(Context context){
        _executor = Executors.newSingleThreadExecutor();
        this.context = context;
    }

    public Future<Object> updateCharacter(UpdateCharacterOBJ characterObj){
        return _executor.submit(() -> RequestsConstants.putRequest(
                RequestsConstants.serverHost + "/api/user/character",
                characterObj,
                RequestsConstants.BasicRequestResponse.class,
                context
        ));
    }

    public Future<Object> editProfile(EditProfileOBJ editObj){
        return _executor.submit(() -> RequestsConstants.putRequest(
                RequestsConstants.serverHost + "/api/user/profile",
                editObj,
                RequestsConstants.BasicRequestResponse.class,
                context
        ));
    }
}