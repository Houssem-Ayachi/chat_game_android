package com.azorom.chatgame.Requests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRequest {

    public static class UpdateCharacterOBJ {
        public String id;
        public String hat;
        public String head;
        public String body;

        public UpdateCharacterOBJ(String id, String hat, String head, String body){
            this.id = id;
            this.hat = hat;
            this.head = head;
            this.body = body;
        }
    }
    public static class EditProfileOBJ{
        public String id;
        public String userName;
        public String bio;
        public String[] funFacts;
        public EditProfileOBJ(String id, String userName, String bio, String[] funFacts){
            this.id = id;
            this.userName = userName;
            this.bio = bio;
            this.funFacts = funFacts;
        }
    }
    private final ExecutorService _executor;

    public UserRequest(){
        _executor = Executors.newSingleThreadExecutor();
    }

    public Future<Object> updateCharacter(UpdateCharacterOBJ characterObj){
        return _executor.submit(() -> RequestsConstants.putRequest(
                RequestsConstants.serverHost + "/api/user/character",
                characterObj,
                RequestsConstants.BasicRequestResponse.class
        ));
    }

    public Future<Object> editProfile(EditProfileOBJ editObj){
        return _executor.submit(() -> RequestsConstants.putRequest(
                RequestsConstants.serverHost + "/api/user/profile",
                editObj,
                RequestsConstants.BasicRequestResponse.class
        ));
    }
}