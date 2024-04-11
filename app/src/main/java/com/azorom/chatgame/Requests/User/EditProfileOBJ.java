package com.azorom.chatgame.Requests.User;

public class EditProfileOBJ {
    public String userName;
    public String bio;
    public String[] funFacts;
    public EditProfileOBJ(String userName, String bio, String[] funFacts){
        this.userName = userName;
        this.bio = bio;
        this.funFacts = funFacts;
    }
}
