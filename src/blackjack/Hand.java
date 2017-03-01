/**
 * Representation of a blackjack hand, or all of the cards a player has.
 * Consists of an array list of cards.
 * Features insertion sort methods for sorting cards, and other methods for 
 * checking hand's value or if it can split, as well as toString representation.
 * @author Victor Riveros
 */
package blackjack;
import java.util.ArrayList;

public class Hand {
    ArrayList<Card> hand = new ArrayList<Card>();
    ArrayList<Card> secondHand = new ArrayList<Card>();
    
    /*Method goes through player's cards (Card ArrayList) and calculates the value of the player's hand.*/
    public int getHandValue(){
        int value = 0;
        int aces = 0;
        for (int i = 0; i < hand.size(); i++) {
            int x = hand.get(i).getValue();
            value += x;
            if(hand.get(i).getCard() == 14){
                aces++;
            }
        }
            if(value > 21){
                value = value - (10 * aces);
            }
            
        return value;
    }

    
    /*Checks if first two cards are same value, if so returns true for split method.*/
    public boolean canSplit(){
        int valueCard1 = hand.get(0).getCard();
        int valueCard2 = hand.get(1).getCard();
        if(valueCard1 == valueCard2){
            return true;
        }
        else{
            return false;
        }
    }
    /*removes card from hand to be sent to second hand.*/
    public Card split(){
        Card card = hand.get(1);
        hand.remove(1);
        return card;
    }
    
    
    public void printPlayerHand(){         
        for(int i = 0; i < hand.size(); i++){
            System.out.println(hand.get(i).toString());
        }
    }
    
    public ArrayList getHand(){
        return hand;
    }
    
    /*Sets the cards in deal to the hand.*/
    public void deal(ArrayList<Card> cards){
        hand = cards;
    }
    
    public String toString(){
        String string = "";
        for(int i=0; i < hand.size(); i++){
            string += hand.get(i).getName() + "\n";
    }
        return string;
    }
    /*Adds a card from hit to the hand.*/
    public void hit(Card card){
        hand.add(card);
    }
    //checks to see if the hand has an ace.
    public boolean checkAce(){
        boolean ace = false;
        for(int i = 0; i< hand.size(); i++){
            if(hand.get(i).getCard() == 14){
                ace = true;
            }
        }
        return ace;
    }
    
    public void changeAceValue(){
        for(int i = 0; i< hand.size(); i++){
            if(hand.get(i).getCard() == 14){
                hand.get(i).setValue(1);
            }
        }
    }
    
    public void insertionSort(){ 
        for(int j = 1; j < hand.size(); j++){
            Card keyCard = hand.get(j);
            int keyCardValue = keyCard.getValue();
            int i = j-1;
            while ( (i > -1)&&(hand.get(i).getValue() > keyCardValue)){
                Card card1 = hand.get(i+1);
                Card card2 = hand.get(i);
                hand.set(i+1, card2);
                hand.set(i, card1);
            }
        }
    } 
    
    public void clearHand(){
        hand.clear();
    }
    
    
    

}
