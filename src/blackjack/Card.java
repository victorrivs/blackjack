/*
 * Repesentes a single card with a suit, card and value. 
 */
package blackjack;

/**
 *
 * @author Victor Riveros
 */
public class Card {
    int value, card;
    String name, suit;
    
    public Card(String suit, int card, int value){
        this.suit = suit;
        this.card = card;
        this.value = value;
    }
    
     public String getName() {
        switch (card) {
            case 2:
                name = "The Two of";
                break;
            case 3:
                name = "The Three of";
                break;
            case 4:
                name = "The Four of";
                break;
            case 5:
                name = "The Five of";
                break;
            case 6:
                name = "The Six of";
                break;
            case 7:
                name = "The Seven of";
                break;
            case 8:
                name = "The Eight of";
                break;
            case 9:
                name = "The Nine of";
                break;
            case 10:
                name = "The Ten of";
                break;
            case 11:
                name = "The Jack of";
                break;
            case 12:
                name = "The Queen of";
                break;
            case 13:
                name = "The King of";
            case 14:
                name = "The Ace of";
                break;
        }
        name += " " + suit;
        return name;
    }
     
     
     public String getSuit(){
         return suit;
     }
     
     public int getValue(){
         return value;
     }
     
     public int getCard(){
         return card;
     }
     
     public void setValue(int x){
         value = x;
     }

}


