package com.codename.demo.game.gamecore;

import com.codename.demo.game.gamecore.enums.TEAM;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketPlayerA {
    WebSocketSession session;
    private String name;
    private Team team;


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
    public Team getTeam() {
        return team;
    }

    public boolean isEqual(WebSocketPlayerA a){
        return name.equals(a.getName());
        //return this.session.equals(a.session) && name.equals(a.getName());
    }

    protected void onGameStateChange(String newstate){
        System.out.println("[ongamestatechange]");
        try {
            session.sendMessage(new TextMessage(newstate));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setSession(WebSocketSession session) {
        this.session = session;
    }
    public WebSocketSession getSession(){
        return session;
    }
    public String getSessionId(){return session.getId();}
}
