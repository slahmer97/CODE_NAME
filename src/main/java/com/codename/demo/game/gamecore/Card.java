package com.codename.demo.game.gamecore;

import com.codename.demo.game.gamecore.enums.STATE;
import com.codename.demo.game.gamecore.enums.TYPE;

/**
 * This Class represents the main structure of Card
 * it has :
 *          id attribute : this will helps to make a card value independed from the language that will be used in game
 *          state attribute : this will helps to keep tracking of current card {selected,default=not-selected}
 *          type attribute : this will helps to keep tracking of the type of card{RedCard,BlueCard,NeutralCard,AssassinCard,DoubleCard}
 * all attributes in this class are private so they can not be accessed only with the interfaces that are provided in this class{Methods descriprion below}
 */
class Card {
    private int id;
    private STATE state;
    private TYPE type;

    protected Card(int id_,TYPE type_){
        id = id_;
        state = STATE.DEFAULT;
        type = type_;
    }

    protected int getId(){
        return this.id;
    }
    protected TYPE getType(){
        return type;
    }

    /**
     * this is the main method for changing the state of card from default{#not-selected} to selected
     */
    protected void setSelected(){
       state = STATE.SELECTED;
    }

    /**
     * this is the method that allow us to access the state attribute by checking if the state attribute
     * @return {TRUE if card is already selected | FALSE if card is in default state{#not-selected}}
     */
    protected boolean isSelected(){
        return state == STATE.SELECTED;
    }


}
