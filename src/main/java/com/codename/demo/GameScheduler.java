package com.codename.demo;

import com.codename.demo.game.gamecore.*;
import com.codename.demo.game.messageManager.MsgEncoder;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.crypto.MacSpi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameScheduler {
    private static ArrayList<Game> games;
    private static HashMap<String,ArrayList<WebSocketPlayerA>> pendingPlayers;

    public static void newPlayerHasConnected(WebSocketSession session){
        System.out.println("New player has connected");


    }
    public static void newPlayerMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        //MSGTYPE:CREATEGAME,USERNAME:stevlulz,TEAM:TEAM,GAMEID:,GAMETYPE:MULTIPLAYER
        //USERNAME:stevlulz,TEAM:TEAM,GAMEID:,GAMETYPE:MULTIPLAYER
        String content;
        String receivedMsg = textMessage.getPayload();
        if (receivedMsg.contains("MSGTYPE:CREATEGAME")) {
            content = receivedMsg.replace("MSGTYPE:CREATEGAME,", "");
            createGame(session,content);

        } else if (receivedMsg.contains("MSGTYPE:JOIN,")) {
            content = receivedMsg.replace("MSGTYPE:JOIN,","");
            joinGame(session,content);
        }
        else if(receivedMsg.contains("MSGTYPE:SELECTCARD,")){
            content = receivedMsg.replace("MSGTYPE:SELECTCARD,","");
            // x,y
            selectCard(session,content);
        }
        else if(receivedMsg.contains("MSGTYPE:HINT,")){
            content = receivedMsg.replace("MSGTYPE:HINT,","");
            giveHint(session,content);
        }
        else if(receivedMsg.contains("MSGTYPE:MSG,")){
            content = receivedMsg.replace("MSGTYPE:MSG,","");
            broadCastMessage(session,content);
        }
        else if(receivedMsg.contains("MSGTYPE:SWITCH")){
            session.sendMessage(new TextMessage(MsgEncoder.desactivateView()));
            getGameBySession(session).switchTurn();

        }


    }
    public static void playerHasDisconnected(WebSocketSession session, CloseStatus status){
        System.out.println("ID : "+session.getId()+" has disconnected");


    }
    static void init(){
        games = new ArrayList<>();
        pendingPlayers = new HashMap<>();
    }
    private static boolean addNewGame(Game g){
        boolean exist = gameAlreadyExist(g);
        if(!exist)
            games.add(g);
        return exist;
    }
    private static boolean gameAlreadyExist(Game g){
        for (Game game : games)
            if(game.getGameId().equals(g.getGameId()))
                return true;
        return false;
    }
    private static Game getGameById(String id){
        for (Game game : games)
            if(game.getGameId().equals(id))
                return game;
        throw new NullPointerException();
    }
    private static Game getGameBySession(WebSocketSession session){
        for (Game game : games) {
            if (game.hasPlayer(session))
                return game;
            if(game.hasSpyMaster(session))
                return game;
        }


        throw new NullPointerException();
    }

     static void createGame(WebSocketSession session,String  content) throws IOException {
        System.out.println("CREATE A NEW GAME");

        String[] str_arr = content.split(",");

        String game_name = str_arr[3].split(":")[1];
        String player_name = str_arr[0].split(":")[1];
        String team_name = str_arr[1].split(":")[1];
        System.out.println(player_name);

        //see if timer is activated
        int timer = 10;
        Team t1 = new Team(), t2 = new Team();
        String game_id = session.getId().substring(0,5);
        Game game = new Game(game_id);
        game.timer(100);

        session.sendMessage(new TextMessage(MsgEncoder.gameId(game_id)));
        System.out.println("ID : "+game_id);

        game.setT1(t1);
        game.setT2(t2);
        //game.activateTimer(timer);

        games.add(game);

       // pendingPlayers.put(game_id, new ArrayList<>());

        WebSocketSpyMaster a = new WebSocketSpyMaster(session);
        a.setName(player_name);

        if(team_name.equals("TEAM1")){
            System.out.println("SPYMASTER ADDED TO TEAM 1");
            a.setTeam(t1);
            game.addST1(a);
        }
        else{
            System.out.println("SPYMASTER ADDED TO TEAM 2");
            a.setTeam(t2);
            game.addST2(a);
        }


        //pendingPlayers.get(game_id).add(a);

       // session.sendMessage(new TextMessage("ID:"+game_id));

    }
     static void joinGame(WebSocketSession session,String content){
         String[] str_arr = content.split(",");
         String game_id = str_arr[2].split(":")[1];
         String player_name = str_arr[0].split(":")[1];
         System.out.println(player_name);
         String team_name = str_arr[1].split(":")[1];
         System.out.println("JOIN\n"+"gamename:"+game_id+"\tplayername:"+player_name+"\tteamname"+team_name);
         Game game = null;
         for (Game game1 : games) {
             if(game1.getGameId().equals(game_id)){
                 game = game1;
                 break;
             }
         }

         if (game == null) {
             System.out.println("GAME DOESN4T EXISIT");
             return;
         }

         //right game

         if (team_name.equals("TEAM1")){
             if(game.numOfT1Players() == 0){
                 WebSocketSpyMaster tmp = new WebSocketSpyMaster(session);
                 game.addST1(tmp);
                 tmp.setName(player_name);
                 tmp.setTeam(game.getT1());
                 System.out.println("ADDED SPYMASTER TO TEAM 1");
             }
             else{
                 WebSocketPlayer tmp = new WebSocketPlayer(session);
                 game.addPT1(tmp);
                 tmp.setName(player_name);
                 tmp.setTeam(game.getT1());
                 System.out.println("ADDED player to TEAM1");
             }
             System.out.println("TEAM1 num : "+game.numOfT1Players());
         }
         else{//TEAM2
             if(game.numOfT2Players() == 0){
                 WebSocketSpyMaster tmp = new WebSocketSpyMaster(session);
                 game.addST2(tmp);
                 tmp.setName(player_name);
                 tmp.setTeam(game.getT2());
                 System.out.println("ADDED SPYMASTER TO TEAM 2");
             }
             else{
                 WebSocketPlayer tmp = new WebSocketPlayer(session);
                 game.addPT2(tmp);
                 tmp.setName(player_name);
                 tmp.setTeam(game.getT2());
                 System.out.println("ADDED player to TEAM2");
             }
             System.out.println("TEAM2 num : "+game.numOfT2Players());
         }


         if(game.numOfT1Players() > 1 && game.numOfT2Players() > 1){
             //run game
             System.out.println("START GAME");
             game.start();

             //Thread thread = new Thread(game);
             //thread.run();
         }

     }
     static void selectCard(WebSocketSession session,String content){
        System.out.println("SELECT CARD REQUEST");
        int x,y;
        x = Integer.parseInt(content.split(",")[0]);
        y = Integer.parseInt(content.split(",")[1]);
        System.out.println("REQUEST SELECT CARD FROM"+session.getId().substring(0,10)+" x:"+x+",y:"+y);
        Game game = getGameBySession(session);
        WebSocketPlayer player = game.getPlayerBySession(session);
        game.validateCard(player,x,y);
     }
     static void giveHint(WebSocketSession session,String content){
        System.out.println("GIVE HINT REQUEST");
        String clue;
        int num;
        clue = content.split(",")[0].split(":")[1];
        num = Integer.parseInt(content.split(",")[1].split(":")[1]);
        System.out.println("HINT clue : "+clue+"  NUM: "+num);
        Game game = getGameBySession(session);
        WebSocketSpyMaster spyMaster;
        System.out.println("Current Session" +session.getId());
        System.out.println("SPYMASTER1[TEAM1] SessionID" +game.getT1().getSpyMaster().getSessionId());
        System.out.println("SPYMASTER[TEAM2] SessionID" +game.getT2().getSpyMaster().getSessionId());

         if(game.getT1().getSpyMaster().getSessionId().equals(session.getId())){
            System.out.println("SPYMASTER[TEAM1]");
             spyMaster = game.getT1().getSpyMaster();

        }
        else{
             System.out.println("SPYMASTER[TEAM2]");
            spyMaster = game.getT2().getSpyMaster();
        }
        game.giveHint(spyMaster,clue,num);
     }
     static void broadCastMessage(WebSocketSession session,String message){
        System.out.println("==BroadCastMessage()==");
        Game game = getGameBySession(session);
        WebSocketPlayer p = game.getPlayerBySession(session);
        if(p != null)
             p.broadCastMessage(message);
     }
}
