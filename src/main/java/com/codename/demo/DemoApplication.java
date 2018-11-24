package com.codename.demo;

import com.codename.demo.game.gamecore.*;
import com.codename.demo.game.gamecore.PlayerXXInterfaces.SpyMaster;
import com.codename.demo.game.messageManager.MsgEncoder;
import com.codename.demo.game.websocket.MessageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args)
    {
         GameScheduler.init();
         SpringApplication.run(DemoApplication.class, args);

        /*
        Game game = new Game("ss");
        Team t1,t2;

        WebSocketPlayer p1,p2;
        WebSocketSpyMaster s1,s2;
        WebSocketSession socketSession = new WebSocketSession() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public URI getUri() {
                return null;
            }

            @Override
            public HttpHeaders getHandshakeHeaders() {
                return null;
            }

            @Override
            public Map<String, Object> getAttributes() {
                return null;
            }

            @Override
            public Principal getPrincipal() {
                return null;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return null;
            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public String getAcceptedProtocol() {
                return null;
            }

            @Override
            public void setTextMessageSizeLimit(int i) {

            }

            @Override
            public int getTextMessageSizeLimit() {
                return 0;
            }

            @Override
            public void setBinaryMessageSizeLimit(int i) {

            }

            @Override
            public int getBinaryMessageSizeLimit() {
                return 0;
            }

            @Override
            public List<WebSocketExtension> getExtensions() {
                return null;
            }

            @Override
            public void sendMessage(WebSocketMessage<?> webSocketMessage) throws IOException {

            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public void close(CloseStatus closeStatus) throws IOException {

            }
        };
        p1 = new WebSocketPlayer(socketSession);
        p2 = new WebSocketPlayer(socketSession);
        s1 = new WebSocketSpyMaster(socketSession);
        s2 = new WebSocketSpyMaster(socketSession);
        t1 = new Team();
        t2 = new Team();
        game.setT2(t2);
        game.setT1(t1);

        p1.setName("P1");
        p2.setName("P2");
        s1.setName("S1");
        s2.setName("S2");

        game.addPT1(p1);
        game.addST1(s1);
        game.addST2(s2);
        game.addPT2(p2);
        p1.setTeam(t1);
        s1.setTeam(t1);
        p2.setTeam(t2);
        s2.setTeam(t2);

        System.out.println(t1.getSpyMaster());

        game.start();
        game.validateCard(p1,0,0);

        game.validateCard(p2,0,0);
        game.validateCard(p2,0,0);

        game.giveHint(s1,"sd",1);
        game.giveHint(s2,"sd",1);

        game.validateCard(p1,0,0);
        game.validateCard(p2,0,0);
        */


    }
}
