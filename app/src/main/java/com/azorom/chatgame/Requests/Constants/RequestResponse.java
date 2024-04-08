package com.azorom.chatgame.Requests.Constants;

public class RequestResponse<ResponseType> {
    public ResponseType response;
    public RequestError error;
    public RequestResponse(ResponseType response, RequestError error){
        this.response = response;
        this.error = error;
    }
}
