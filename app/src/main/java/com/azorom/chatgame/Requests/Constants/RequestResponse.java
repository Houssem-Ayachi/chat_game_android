package com.azorom.chatgame.Requests.Constants;

public class RequestResponse<ResponseType, ErrorType> {
    public ResponseType response;
    public ErrorType error;
    public RequestResponse(ResponseType response, ErrorType error){
        this.response = response;
        this.error = error;
    }
}
