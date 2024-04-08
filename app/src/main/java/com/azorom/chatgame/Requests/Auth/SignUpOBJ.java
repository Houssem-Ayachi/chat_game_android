package com.azorom.chatgame.Requests.Auth;

public class SignUpOBJ {
    public String userName;
    public String email;
    public String password;
    public SignUpOBJ(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
