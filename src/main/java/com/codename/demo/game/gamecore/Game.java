package com.codename.demo.game.gamecore;

import com.codename.demo.game.gamecore.PlayerXXInterfaces.SpyMaster;
import com.codename.demo.game.gamecore.enums.TURN;
import com.codename.demo.game.messageManager.MsgEncoder;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;

public class Game implements Runnable {
    private boolean gameEnded;
    private String gameId;
    private Board board;
    private Team t1,t2;//T1=>red T2=>blue
    private TURN currentTurn;
    private int numOfLastPlays;
    private Score score;
    private int timer;
    private int lastPlayer;
    public Game(String id){
        lastPlayer = 0;
        gameId = id;
        timer = 0;
        board = new Board();
        numOfLastPlays = 0;
        gameEnded = false;
        score = new Score();
    }

    public String getGameId() {
        return gameId;
    }
    public void start(){
            onGameStateChange();
        try {
            t2.getSpyMaster().getSession().sendMessage(new TextMessage(MsgEncoder.desactivateView()));
            ArrayList<WebSocketPlayer> players = t2.getPlayers();
            for(WebSocketPlayer t : players)
                t.session.sendMessage(new TextMessage(MsgEncoder.desactivateView()));

            players = t1.getPlayers();
            for(WebSocketPlayer t : players)
                t.session.sendMessage(new TextMessage(MsgEncoder.desactivateView()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentTurn = TURN.REDSPYMASTER;
        t1.getSpyMaster().onTurnRequest();


    }

    //websocket
    public void giveHint(WebSocketSpyMaster s,String hint,int num){
        /*
        if(s.getId().equals(t1.getSpyMaster().getSessionId()))
            tmp = t1.getSpyMaster();
        if(s.getId().equals(t2.getSpyMaster().getSessionId()))
            tmp = t2.getSpyMaster();
        */
        System.out.println("1HINT");

        if(s == null )
            return;

        System.out.println("2HINT");
       // if(!canSpyMasterPlay(s))
        //    return;
        System.out.println("GONNA SEND HINT");
        s.announceHint(hint,num);
        switchTurn();

        try {
            s.session.sendMessage(new TextMessage(MsgEncoder.desactivateView()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //websocket
    public void validateCard(WebSocketPlayer s,int x,int y){
        if(s == null)
            return;
        System.out.println("Can playerPlay");
       // if(!canPlayerPlay(s))
        //    return;
        int tmp = 0;//go here
        try {
            tmp = board.selectCard(x,y,currentTurn);
            System.out.println("CARD SELECTED ,RESULT : "+tmp);
            if(tmp == 0) {//NEUTRAL CARD SELECTED
                s.getSession().sendMessage(new TextMessage(MsgEncoder.desactivateView()));
                switchTurn();
                return;
            }
            else if(tmp  == -1){//ASSASSIN CARD SELECTED
                if(s.getTeam() == t1)
                    score.setS2Zero();
                else
                    score.setS1Zero();
                onGameEnd();
                return;
            }
            else if(tmp == 1){//RED CARD SELECTED
                score.decS1();
                if(s.getTeam() ==  t2){
                    s.getSession().sendMessage(new TextMessage(MsgEncoder.desactivateView()));
                    switchTurn();

                }
            }
            else if(tmp == 2){//BLUE CARD SELECTED
                score.decS2();
                if(s.getTeam() == t1){
                    s.getSession().sendMessage(new TextMessage(MsgEncoder.desactivateView()));
                    switchTurn();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(score.canWeStop())
            onGameEnd();
        numOfLastPlays++;
        if(numOfLastPlays == s.getTeam().getSpyMaster().getNumHint()) {
            try {
                s.getSession().sendMessage(new TextMessage(MsgEncoder.desactivateView()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            switchTurn();

        }else {
            onGameStateChange();
        }
    }

    public void broadCastMessage(String message, WebSocketPlayer player){
        String msgF = MsgEncoder.chatMsgF(message,player.getName());
        String msg =  MsgEncoder.chatMsgP(message,player.getName());
        if(t1.isMemberPlayer(player)){
            t1.broadCastMessage(msg);
            t2.broadCastMessage(msgF);

        }
        else{
            t1.broadCastMessage(msgF);
            t2.broadCastMessage(msg);
        }
    }
    private void onGameStateChange(){
        String viewS = MsgEncoder.viewSpyMasterMsg(board);
        String viewP = MsgEncoder.viewPlayerMsg(board);
        viewP.replace("\\", "");
        viewS.replace("\\", "");
        t1.onGameStateChange(viewS,viewP);
        t2.onGameStateChange(viewS,viewP);
    }
    private void onGameEnd(){
        String lost = MsgEncoder.gameEndLoserView();
        String win = MsgEncoder.gameEndWinnerView();
        try {
            String view = MsgEncoder.desactivateView();
            t1.onGameStateChange(view,view);
            t2.onGameStateChange(view,view);
            view = MsgEncoder.viewSpyMasterMsg(board);
            t1.onGameStateChange(view,view);
            t2.onGameStateChange(view,view);
            if(score.getT1Score() == 0){

                    t1.onGameEnd(win);
                    t2.onGameEnd(lost);
            }
            else{
                t2.onGameEnd(win);
                t1.onGameEnd(lost);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameEnded = true;
    }

    private boolean canSpyMasterPlay(WebSocketSpyMaster spyMaster){
        boolean ret = (t1.isMemberSpyMaster(spyMaster) && currentTurn == TURN.REDSPYMASTER )||
                      (t2.isMemberSpyMaster(spyMaster) && currentTurn == TURN.BLUEPLAYERS);
        System.out.println("CanSpyMasterPlayer");
        System.out.println("CurrentTurn " + currentTurn);
        System.out.println("RETURN"+ret);
        if(spyMaster.getTeam() == t1)
            System.out.println("TEAM1");
        else
            System.out.println("TEAM2");


        return ret;
    }
    private boolean canPlayerPlay(WebSocketPlayer player){
        boolean ret =  (t1.isMemberPlayer(player) && currentTurn == TURN.REDPLAYERS)||
                (t2.isMemberPlayer(player) && currentTurn == TURN.BLUEPLAYERS);

        System.out.println("RET can playerPlay "+ret);
        return ret;
    }

    //=======================================================================
    public void setT1(Team t1) {
        this.t1 = t1;
        t1.setGame(this);
    }
    public void setT2(Team t2) {
        this.t2 = t2;
        this.t2.setGame(this);
    }
    public void addPT1(WebSocketPlayer a){
        t1.addPlayer(a);
    }
    public void addPT2(WebSocketPlayer a){
        t2.addPlayer(a);
    }
    public void addST1(WebSocketSpyMaster a){
        t1.setSpyMaster(a);
    }
    public void addST2(WebSocketSpyMaster a){
        t2.setSpyMaster(a);
    }
    //========================================================================

    public void switchTurn()  {
        numOfLastPlays = 0;
        System.out.println("SWITCH TURN");
        ArrayList<WebSocketPlayer> players = null;
        onGameStateChange();
        switch (currentTurn){
            case REDSPYMASTER:
                currentTurn = TURN.REDPLAYERS;
                t1.onPlayersTurnRequest();
                break;
            case REDPLAYERS:
               // players = t1.getPlayers();
                currentTurn = TURN.BLUESPYMASTER;
                t2.onSpyMasterTurnRequest();
                break;
            case BLUESPYMASTER:
                currentTurn = TURN.BLUEPLAYERS;
                t2.onPlayersTurnRequest();
                break;
            case BLUEPLAYERS:
                //players = t2.getPlayers();
                currentTurn = TURN.REDSPYMASTER;
                t1.onSpyMasterTurnRequest();
        }
        try {
            if(players != null)
                for (WebSocketPlayer player : players) {
                    player.getSession().sendMessage(new TextMessage(MsgEncoder.desactivateView()));
                    System.out.println("[DESACTIVATE VIEW FOR "+player.getName()+"]");
                }
        } catch (IOException e) {
        e.printStackTrace();
    }


    }

    private WebSocketPlayer getPlayerRef(WebSocketSession s){
        if(s == null)
            return null;
        for (WebSocketPlayer player : t1.getPlayers())
            if(player.getSessionId().equals(s.getId()))
                return player;
        for (WebSocketPlayer player2 : t2.getPlayers())
            if(player2.getSessionId().equals(s.getId()))
                return player2;
        return null;
    }
    public boolean gameEnded(){
        return gameEnded;
    }

     public Board getBoard() {
         return board;
     }

    public Team getT1() {
        return t1;
    }

    public Team getT2() {
        return t2;
    }

    public boolean canGameStart(){
        return t1.isCompleted() && t2.isCompleted();
    }

    public boolean hasPlayer(WebSocketSession session){
        return t1.hasPlayer(session) || t2.hasPlayer(session);
    }
    public int numOfT1Players(){
        int ret = 0;
        if(t1.getSpyMaster() != null)
            ret++;
        ret += t1.getPlayers().size();
        return ret;
    }
    public int numOfT2Players(){
        int ret = 0;
        if(t2.getSpyMaster() != null)
            ret++;
        ret += t2.getPlayers().size();
        return ret;
    }
    public WebSocketPlayer getPlayerBySession(WebSocketSession session){
        WebSocketPlayer player = null;
        player = t1.getPlayerBySession(session);
        if(player != null)
            return player;
        player = t2.getPlayerBySession(session);
        if(player != null)
            return player;
        throw new NullPointerException("getPlayerBySession return NULL");
    }
    public WebSocketSpyMaster getSpyMasterBySession(WebSocketSession session){
        WebSocketSpyMaster spyMaster = null;
        spyMaster = t1.getSpyMaster();
        if(spyMaster != null && spyMaster.getSession().getId().equals(session.getId()))
            return spyMaster;
        spyMaster = t2.getSpyMaster();
        if(spyMaster != null && spyMaster.getSession().getId().equals(session.getId()))
            return spyMaster;

        throw new NullPointerException("getSpyMasterBySession throw exception ");
    }

    public void activateTimer(int i){
        Thread thread= new Thread(this);
        thread.run();
    }
    public void timer(int i){
        this.timer = i;
    }

    @Override
    public void run() {
        if(timer == 0)
            timer = 10;
        try {
            while (!gameEnded){
                int tmp = lastPlayer;
                Thread.sleep(timer*1000);
                 if(tmp == lastPlayer)
                     switchTurn();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasSpyMaster(WebSocketSession session) {
        return t1.getSpyMaster().getSessionId().equals(session.getId()) || t2.getSpyMaster().getSessionId().equals(session.getId());
    }
}
