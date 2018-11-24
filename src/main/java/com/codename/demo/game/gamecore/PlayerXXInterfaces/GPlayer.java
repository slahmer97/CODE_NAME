package com.codename.demo.game.gamecore.PlayerXXInterfaces;

public interface GPlayer{
    /**
     * @brief this interface is used by both normal players and spymaster
     *        it notify the player for his turn [you turn came, you can play]
     */
    void onTurnRequest();
}
