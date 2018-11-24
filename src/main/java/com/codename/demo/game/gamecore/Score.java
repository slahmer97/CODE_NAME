package com.codename.demo.game.gamecore;

public class Score {
    int t1Score;
    int t2Score;

    public Score() {
        t1Score = 9;
        t2Score = 8;
    }

    public int getT1Score() {
        return t1Score;
    }

    public int getT2Score() {
        return t2Score;
    }
    public void decS1(){
            t1Score --;
    }
    public void decS2(){
        t2Score--;
    }
    public void setS2Zero(){
        t2Score = 0;
    }
    public void setS1Zero(){
        t1Score = 0;
    }

    public boolean canWeStop(){
        return t1Score == 0 || t2Score == 0;
    }
}
