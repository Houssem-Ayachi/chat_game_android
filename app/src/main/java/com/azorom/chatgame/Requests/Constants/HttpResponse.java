package com.azorom.chatgame.Requests.Constants;

public class HttpResponse<ResponseType> extends RequestResponse<ResponseType, HttpRequestError>{
    public HttpResponse(ResponseType response, HttpRequestError error) {
        super(response, error);
    }
}
