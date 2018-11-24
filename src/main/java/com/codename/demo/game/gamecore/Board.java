package com.codename.demo.game.gamecore;

import com.codename.demo.game.gamecore.enums.TURN;
import com.codename.demo.game.gamecore.enums.TYPE;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represent board of game, it contains a 5*5 matrix of card
 * board object can be accessed only by the interfaces provided by the class in order not to let other object mess
 * with the content of it once it is initialized
 */
public class Board {
    private Card cards[][];

    public Board(){
        cards = new Card[5][5];
        initBoardWithRandomWords();

    }

    /**
     *  this function has role of initializing the board with random values (card.value)
     *
     */
    private void initBoardWithRandomWords(){
        //TODO
        int tmp = 0;
        int[] t = ThreadLocalRandom.current().ints(0, 699).distinct().limit(25).toArray();
        int[] a={0,1,2,3,4};
        int[] b={0,1,2,3,4};
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < a.length; ++i)
            for (int j = 0; j < b.length; ++j)
                list.add(new int[] {a[i], b[j]});
        Collections.shuffle(list);
        for (int[] ints : list) {
            if(tmp <8)
                cards[ints[0]][ints[1]] = new Card(t[tmp], TYPE.RED);
            else if (tmp == 9)
                cards[ints[0]][ints[1]] = new Card(t[tmp], TYPE.DOUBLE);
            else if(tmp >9 && tmp <=17)
                cards[ints[0]][ints[1]] = new Card(t[tmp], TYPE.BLUE);
            else if(tmp == 18){
                cards[ints[0]][ints[1]] = new Card(t[tmp], TYPE.ASSASSIN);
            }
            else
                cards[ints[0]][ints[1]] = new Card(t[tmp], TYPE.NEUTRAL);
            tmp++;
        }



    }


    /**
     * @param x {x coordonate in board}
     * @param y {y coordonate in board}
     * @param t {t current turn}
     * @return {1 if card successufully selected | 0 if card is already selected | -1 false coordonates}
     */
    protected int selectCard(int x, int y, TURN t) throws Exception {
        if(isGoodCoordonate(x,y)){
            System.out.println("CARD STATE BEFORE SELECTION : "+cards[x][y].isSelected());
            if(cards[x][y].isSelected()){
                System.out.println("Card is already Selected");
                return 0;
            }
            else{
                System.out.println("Card TYPE :"+cards[x][y].getType()+"  ===  currentTurn"+t);
                cards[x][y].setSelected();
                if(cards[x][y].getType() == TYPE.RED)
                    return 1;
                if(cards[x][y].getType() == TYPE.BLUE)
                    return 2;
                if(cards[x][y].getType() == TYPE.NEUTRAL)
                    return 0;
                if(cards[x][y].getType() == TYPE.ASSASSIN)
                    return -1;
                if(cards[x][y].getType() == TYPE.DOUBLE){
                   return 1;
                }
                if(t == TURN.REDPLAYERS && cards[x][y].getType() == TYPE.RED)
                    return 1;
                if(t == TURN.BLUEPLAYERS && cards[x][y].getType() == TYPE.RED)
                    return 2;
            }
        }
        throw new Exception("coordonate out of range");
    }

    protected int getScore(int x,int y) throws Exception {
        if(isGoodCoordonate(x,y)){
            switch (cards[x][y].getType()){
                case RED:
                    return 1;
                case BLUE:
                    return 1;
                case DOUBLE:
                    return 1;
                case NEUTRAL:
                    return 0;
                case ASSASSIN:
                    return -1;
            }
        }
        throw new Exception("coordonate out of range");
    }

    protected TYPE getCardType(int x,int y) throws Exception {
        if(isGoodCoordonate(x,y)){
            return cards[x][y].getType();
        }
        throw new Exception("coordonate out of range");


    }
    public String getBoardS(){
        Gson gson = new Gson();
       return gson.toJson(cards) ;
    }
    public String getBoardP(){
        Gson gson = new Gson();
        return gson.toJson(cards) ;
    }
    private boolean isGoodCoordonate(int x,int y){
        return (x >=0 && x<5 && y<5 && y>=0);
    }

}
