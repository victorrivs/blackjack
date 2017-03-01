/*
 * Represents a deck of card using an arraylist of Card objects. Deals card
 * by calling a random integer based on deck size. 
 * @author Victor Riveros
 */
package blackjack;
import java.util.ArrayList;
import java.util.Random;


public class Deck {
    static ArrayList<Card> deck = new ArrayList<Card>();
    
     /*This method makes a two card ArrayList that is called in the beginning of the game.*/
     public ArrayList deal(){
        ArrayList<Card> deal = new ArrayList<>();
        Random rand = new Random();
        Card cardOne, cardTwo;
        
        int cardOneInt = rand.nextInt(deck.size());
        cardOne = deck.get(cardOneInt);
        deck.remove(cardOneInt);
        deal.add(cardOne);
        
        int cardTwoInt = rand.nextInt(deck.size());
        cardTwo = deck.get(cardTwoInt);
        deck.remove(cardTwoInt);
        deal.add(cardTwo);
        
        return deal;     
    }
     
     
    /*method creates deck ArrayList and populates it with cards.*/
    public void populateDeck() {
        for (int z = 0; z < 2; z++) {
            for (int i = 1; i < 5; i++) {
                String suit = null;
                switch (i) {
                    case 1:
                        suit = "Hearts";
                        break;
                    case 2:
                        suit = "Clubs";
                        break;
                    case 3:
                        suit = "Spades";
                        break;
                    case 4:
                        suit = "Diamonds";
                        break;
                }
                for (int x = 2; x < 15; x++) {
                    if (x < 11) {
                        deck.add(new Card(suit, x, x));
                    } else if ((x >= 11) && (x < 14)) {
                        deck.add(new Card(suit, x, 10));
                    } else if (x == 14) {
                        deck.add(new Card(suit, x, 11));
                    }
                }

            }

        }
    }
    /*removes a random card from deck and sends it to the hit method in hand.*/
    public Card hit(){
        Random rand = new Random();
        Card cardToHit;
        int x = rand.nextInt(deck.size());
        cardToHit = deck.get(x);
        deck.remove(x);
        return cardToHit;
    }
    
    public boolean checkCardsLeft(){
        if(deck.size() > 10){
            return true;
        }
        else{
            return false;
        }
    }
    /*resets the deck*/
    public void reset(){
        deck.clear();
    }
    
    public ArrayList<Card> returnDeck(){
        return deck;
    }
    
    public void addCard(Card card){
        deck.add(card);
    }

     
}
