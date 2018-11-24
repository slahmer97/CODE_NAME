package com.codename.demo.game.messageManager;

import com.codename.demo.game.gamecore.Board;
import com.codename.demo.game.gamecore.Game;
import com.google.gson.JsonObject;

public abstract class MsgEncoder {

    public static String viewSpyMasterMsg(Board b){
        JsonObject view = new JsonObject();
        view.addProperty("MSGTYPE","SPYMASTERVIEW");
        //view.addProperty("CONTENT",b.getBoardS());
        return  view.toString().replace("}",",")+"\"CONTENT\":"+b.getBoardS()+"}";

    }
    public static String viewPlayerMsg(Board b){
        JsonObject view = new JsonObject();
        view.addProperty("MSGTYPE","PLAYERVIEW");
        //view.addProperty("CONTENT",b.getBoardS());
        return view.toString().replace("}",",")+"\"CONTENT\":"+b.getBoardS()+"}";
    }
    public static String chatMsgF(String msg,String src){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","CHATMSGF");

        ret.addProperty("CONTENT","<b>"+src+": </b>"+msg);
        return ret.toString();
    }
    public static String chatMsgP(String msg,String src){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","CHATMSGP");
        ret.addProperty("CONTENT","<b>"+src+": </b>"+msg);
        return ret.toString();
    }
    public static String hintMsg(String hint){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","HINT");
        return ret.toString().replace("}",",")+"\"CONTENT\":"+hint+"}";

    }
    public static String activateView(){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","ACTIVATEVIEW");
        return ret.toString();
    }
    public static String desactivateView(){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","DESACTIVATEVIEW");
        return ret.toString();
    }
    public static String gameEndWinnerView(){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","WIN");
        return ret.toString();
    }
    public static String gameEndLoserView(){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","LOST");
        return ret.toString();
    }
    /*
    public static String chatMsg(String msg,String src){
        JsonObject ret = new JsonObject();
        ret.addProperty("MESSAGE",msg);
        ret.addProperty("FROM",src);
        return ret.toString();
    }
    */
    public static String gameId(String id){
        JsonObject ret = new JsonObject();
        ret.addProperty("MSGTYPE","GAMEID");
        ret.addProperty("CONTENT",id);
        return ret.toString();

    }

}
