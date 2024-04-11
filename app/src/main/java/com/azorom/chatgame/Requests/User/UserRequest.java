package com.azorom.chatgame.Requests.User;

import android.content.Context;

import com.azorom.chatgame.Requests.Constants.BasicRequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRequest {
    private final ExecutorService _executor;
    Context context;

    public UserRequest(Context context){
        _executor = Executors.newSingleThreadExecutor();
        this.context = context;
    }

    private Future<Object> sendUpdateCharacterRequest(UpdateCharacterData characterObj){
        return _executor.submit(() -> RequestsConstants.putRequest(
                RequestsConstants.serverHost + "/api/user/character",
                characterObj,
                BasicRequestResponse.class
        ));
    }

    private Future<Object> sendEditProfile(EditProfileOBJ editObj){
        return _executor.submit(() -> RequestsConstants.putRequest(
                RequestsConstants.serverHost + "/api/user/profile",
                editObj,
                BasicRequestResponse.class
        ));
    }

    private Future<Object> sendSearchRequest(String userName){
        return _executor.submit(() -> RequestsConstants.getRequest(
                RequestsConstants.serverHost + "/api/user/search/"+userName,
                SearchedUser[].class
        ));
    }

    public RequestResponse<BasicRequestResponse> updateCharacter(UpdateCharacterData characterOBJ){
        RequestResponse<BasicRequestResponse> resp;
        try {
            resp = (RequestResponse<BasicRequestResponse>)this.sendUpdateCharacterRequest(characterOBJ).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<BasicRequestResponse> editProfile(EditProfileOBJ editObj){
        RequestResponse<BasicRequestResponse> resp;
        try {
            resp = (RequestResponse<BasicRequestResponse>)this.sendEditProfile(editObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<SearchedUser[]> searchUser(String userName){
        RequestResponse<SearchedUser[]> resp;
        try {
            resp = (RequestResponse<SearchedUser[]>)this.sendSearchRequest(userName).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}