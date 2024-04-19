package com.azorom.chatgame.WS;

import android.util.Log;

public class WSSingleton {

    private static WSClient wsc;

    public static WSClient getClient(){
        if(wsc == null){
            wsc = new WSClient();
        }
        return wsc;
    }
}
