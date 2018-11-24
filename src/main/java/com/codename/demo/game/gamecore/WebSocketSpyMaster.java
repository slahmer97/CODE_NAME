package com.codename.demo.game.gamecore;

import com.codename.demo.game.gamecore.PlayerXXInterfaces.GPlayer;
import com.codename.demo.game.gamecore.PlayerXXInterfaces.SpyMaster;
import com.codename.demo.game.messageManager.MsgEncoder;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


public class WebSocketSpyMaster extends WebSocketPlayerA implements GPlayer, SpyMaster {

    private int numHint;
    public WebSocketSpyMaster(WebSocketSession s){
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
    public void announceHint(String hint,int num) {
        //numHint = num+1;
        numHint = num;
        getTeam().announceHint(hint,num);
    }

    public int getNumHint() {
        return numHint;
    }
}
