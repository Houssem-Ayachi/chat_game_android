package com.azorom.chatgame.Requests.User;

import android.content.Context;

import com.azorom.chatgame.Requests.Constants.BasicRequestResponse;
import com.azorom.chatgame.Requests.Constants.HttpRequestError;
import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.azorom.chatgame.Requests.Constants.RequestsConstants;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRequests {
    private final ExecutorService _executor;
    Context context;

    public UserRequests(Context context){
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
                FilteredUser[].class
        ));
    }

    private Future<Object> sendAddFriendRequest(AddFriendOBJ addFriendOBJ){
        return _executor.submit(() -> RequestsConstants.postRequest(
                RequestsConstants.serverHost + "/api/friend/",
                addFriendOBJ,
                ChatCreatedResponse.class
        ));
    }

    public RequestResponse<ChatCreatedResponse, HttpRequestError> addFriend(AddFriendOBJ addFriendOBJ){
        RequestResponse<ChatCreatedResponse, HttpRequestError> resp;
        try {
            resp = (RequestResponse<ChatCreatedResponse, HttpRequestError>)this.sendAddFriendRequest(addFriendOBJ).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<BasicRequestResponse, HttpRequestError> updateCharacter(UpdateCharacterData characterOBJ){
        RequestResponse<BasicRequestResponse, HttpRequestError> resp;
        try {
            resp = (RequestResponse<BasicRequestResponse, HttpRequestError>)this.sendUpdateCharacterRequest(characterOBJ).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<BasicRequestResponse, HttpRequestError> editProfile(EditProfileOBJ editObj){
        RequestResponse<BasicRequestResponse, HttpRequestError> resp;
        try {
            resp = (RequestResponse<BasicRequestResponse, HttpRequestError>)this.sendEditProfile(editObj).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public RequestResponse<FilteredUser[], HttpRequestError> searchUser(String userName){
        RequestResponse<FilteredUser[], HttpRequestError> resp;
        try {
            resp = (RequestResponse<FilteredUser[], HttpRequestError>)this.sendSearchRequest(userName).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}