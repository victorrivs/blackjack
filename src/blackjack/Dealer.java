/**
 * Dealer class that checks if dealer is over value of 17, as well as returning
 * the first card face up at the beginning of the game. 
 * @author Victor Riveros
 */
package blackjack;
import java.util.ArrayList;


public class Dealer extends Hand {
    //ArrayList <Card> dealerHand = new ArrayList <Card>(); 
    
    public boolean checkOver17(){
        boolean hit = false;    
        if (getHandValue() > 16) {  //returns true or false if dealer hand value is over or under 17
            hit = false;
        }
        else {
            hit = true;
        }
        return hit;
    }
    
    private void printDealerHand(){
        for(int i = 0; i < hand.size(); i++){
            System.out.println(hand.get(i).toString());
        }
    }
    
    public String getFaceUp(){
        return hand.get(1).getName();
}
}

