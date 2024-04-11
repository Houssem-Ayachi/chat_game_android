package com.azorom.chatgame.Requests.Auth;

public class VerificationCodeOBJ {
    public int code;
    public String email;
    public VerificationCodeOBJ(int code, String email){
        this.code = code;
        this.email = email;
    }
}
