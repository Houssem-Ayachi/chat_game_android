package com.azorom.chatgame.WS;

public class WSSingleton {

    private static WSClient wsc;

    public static WSClient getClient(){
        if(wsc == null){
            wsc = new WSClient();
        }
        return wsc;
    }

    public static void disconnect(){
        if(wsc != null){
            wsc.disconnect();
            wsc = null;
        }
    }

}
