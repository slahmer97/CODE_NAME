package com.codename.demo.game.gamecore.PlayerXXInterfaces;

public interface SpyMaster {
    /**
     *@brief this interface is used when spymaster announces his hint
     *       so this interface will notify all his team member for
     *       their turn (execute onTurnRequest on all his team members)
     * @param hint {a string message that contains the hint that was given by a spymaster
     *              so this hint will be shown on screen of all  spymaster's team members}
     * @param num
     */
    void announceHint(String hint,int num);
}
