package com.codename.demo.game.gamecore;

import com.codename.demo.game.gamecore.enums.TEAM;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;

public class Team{
    private ArrayList<WebSocketPlayer> players;
    private WebSocketSpyMaster spyMaster;
    private Game game;



    //might have problem
    public Team(){
        players = new ArrayList<>();
    }

    public Team(WebSocketSpyMaster s, ArrayList<WebSocketPlayer> ps) {
        spyMaster = s;
        s.setTeam(this);
        players = new ArrayList<>();
        players.addAll(ps);
        for (WebSocketPlayer player : players) {
            player.setTeam(this);
        }
    }
    public boolean isCompleted(){
        return spyMaster != null && players.size()>0;
    }

    public void announceHint(String hint,int num){
        for (WebSocketPlayer player : players)
            player.onHintReceive(hint,num);
    }
    public void broadCastMessage(String msg){
        for (WebSocketPlayer webSocketPlayer : getPlayers())
            webSocketPlayer.onMessageReceive(msg);

    }


    protected void setSpyMaster(WebSocketSpyMaster sp) {
        if(spyMaster == null && sp != null) {
            this.spyMaster = sp;
            spyMaster.setTeam(this);
        }
    }

    public ArrayList<WebSocketPlayer> getPlayers() {
        return players;
    }
    public void addPlayer(WebSocketPlayer a){
        a.setTeam(this);
        players.add(a);
    }
    private ArrayList<WebSocketPlayer> getMyTeamPlayers(WebSocketPlayer a){
        ArrayList<WebSocketPlayer> ret = new ArrayList<>();
        for (WebSocketPlayer player : players)
            if(!player.isEqual(a))
                ret.add(player);
        return ret;
    }
    public WebSocketSpyMaster getSpyMaster() {
        return spyMaster;
    }
    protected void onGameStateChange(String viewS,String viewP){
        spyMaster.onGameStateChange(viewS);
        for (WebSocketPlayer player : players)
            player.onGameStateChange(viewP);

    }
    protected boolean isMemberPlayer(WebSocketPlayer a){
        for (WebSocketPlayer player : players)
                if(player.isEqual(a))
                    return true;
                return false;
    }
    protected boolean isMemberSpyMaster(WebSocketSpyMaster s){
        return spyMaster.isEqual(s);
    }
    public void onPlayersTurnRequest(){
        for (WebSocketPlayer player : players)
            player.onTurnRequest();
    }
    public void onSpyMasterTurnRequest(){
        spyMaster.onTurnRequest();
    }
    protected void setGame(Game game) {
        this.game = game;
    }
    protected Game getGame() {
        return game;
    }
    protected void onGameEnd(String pst) throws IOException {
        spyMaster.session.sendMessage(new TextMessage(pst));
        for (WebSocketPlayer player : players)
            player.getSession().sendMessage(new TextMessage(pst));

    }
    protected boolean hasPlayer(WebSocketSession session){
        if(spyMaster.getSessionId().equals(session.getId()))
            return true;
        for (WebSocketPlayer player : players)
            if(player.getSessionId().equals(session.getId()))
                return true;
        return false;
    }
    protected WebSocketPlayer getPlayerBySession(WebSocketSession session){
        System.out.println("getPlayerBySession==== "+session.getId());
        for (WebSocketPlayer player : players) {
            System.out.println("\t\t"+player.getSessionId());
            if(player.getSessionId().equals(session.getId()))
                return player;
        }
        return null;
    }
}


