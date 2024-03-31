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
}