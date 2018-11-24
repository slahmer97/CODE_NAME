package com.codename.demo.game.gamecore;

import com.codename.demo.game.gamecore.PlayerXXInterfaces.GPlayer;
import com.codename.demo.game.gamecore.PlayerXXInterfaces.Player;
import com.codename.demo.game.messageManager.MsgEncoder;
import com.google.gson.JsonObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketPlayer extends WebSocketPlayerA implements GPlayer, Player {


    public WebSocketPlayer(WebSocketSession s){
        session = s;
    }
    @Override
    public void onTurnRequest() {
        System.out.println("[onturnRequest]["+getName());
        try {
            session.sendMessage(new TextMessage(MsgEncoder.activateView()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onHintReceive(String hint, int num) {
        System.out.println("[onHintReceive]["+getName()+"] : "+hint+", num : "+num);

        //optimize it later

        JsonObject o = new JsonObject();
        o.addProperty("clue",hint);
        o.addProperty("num",num);
        System.out.println("HINT SEND TO"+this.session.getId().substring(0,10)+" HINT : "+hint+"  num:"+num);
        try {
            session.sendMessage(new TextMessage(MsgEncoder.hintMsg(o.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onMessageReceive(String message) {
        System.out.println("[onMessageReceive]  "+getName()+" : "+message);


        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void broadCastMessage(String message){
        //sent call onMessageReceive method on all players
        getTeam().getGame().broadCastMessage(message,this);
    }
}
