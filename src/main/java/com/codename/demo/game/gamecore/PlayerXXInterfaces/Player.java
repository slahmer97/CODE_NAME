package com.codename.demo.game.gamecore.PlayerXXInterfaces;

import com.codename.demo.game.gamecore.WebSocketPlayer;

public interface Player{
    /**
     * @brief this interface is used when spymaster announces his {hint,num=[number of allowed play]} for current round
     *        so each player in his team will be notified by this interface in order to get hint and start their round
     *
     * @param hint {a string that contains the hint given by spymaster to his team}
     * @param num {for each a given hint by the current spymaster, it will be associated with a number that indicate
     *             to players of his team how much [MAX] cards they can choose, [Min] number is defined as default by 1}
     */
    void onHintReceive(String hint,int num);

    /**
     * @brief this interface is used when a player send a chat message to his team, so each member on his team
     *         will be notified by this interface in order to receive the message sent and from who{message,sender}
     *
     * @param message {a string message that contains the message that was sent by a player to his team}
     */
    void onMessageReceive(String message);

    /**
     * @brief this interface is used when a player want to send a chat message to all his team's members
     *         when message is sent all players that belong to his team will be notified by previous method
     *         [onMessageReceive], and also the identity of player who want to send this message
     *                                  {player which execute @broadcastmessage}
     *
     * @param message {a string message that contains the message that will be send to player chat tab}
     */
    void broadCastMessage(String message);
}
