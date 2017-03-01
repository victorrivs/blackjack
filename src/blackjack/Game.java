/*
 * Main game loop that reads in a text file, determines if there is a 
 * previously saved game. If there is, offers to load game and reads deck data.
 * Otherwise, creates a new deck and blackjack game begins.
 * @author Victor Riveros
 */
package blackjack;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
private static Hand player;
private static Hand secondHand;
private static Dealer dealer;
private static Deck deck = new Deck();
private static String username ;
//private static boolean split;

    /**
     * The main method and loop that runs the game and interacts with user.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Boolean split = false;
        Boolean gameOver = false;
        
        System.out.println("Welcome to Riveros Casino's Blackjack game.");
        System.out.println("Please enter your name");
        username = scanner.next();
        System.out.println("\nHello " + username + ".");
        File inputFile = new File("deck.txt");
        try{
            Scanner inputScan = new Scanner(inputFile);
            if(inputScan.hasNext()){
            System.out.println("Would you like to load a saved deck?");
            String response = scanner.next();
            Boolean validEntry;
            do{
                validEntry = checkYesOrNo(response);
                if(validEntry == false){
                    System.out.println("Enter yes or no.");
                    response = scanner.next();
                }    
            }while(validEntry == false);
            if(response.equals("yes")){
                loadDeck();
            }
            else{
                deck.populateDeck();
            }
            }
            else{
                deck.populateDeck();
            }
        }
        catch(Exception e){
           System.out.println("Error encountered " + e.toString());
       }
        
        
        do{
            player = new Hand();
            dealer = new Dealer();
            System.out.println("The cards will now be dealt. Good Luck");
            
            /*Call the methods to deal cards here*/    
            
            player.deal(deck.deal());
            dealer.deal(deck.deal());
            
            
            //These next to lines are intended to show the player initial hands
            player.insertionSort();
            System.out.println("Your hand is:\n" + player.toString());
            //System.out.println(player.getHandValue());
            System.out.println("The dealer has: " + dealer.getFaceUp() + "\n");
            
            if(player.canSplit() == true){
                System.out.println("Would you like to split?");
                String input = scanner.next();
            
                //This loop makes sure that the use enters yes or no
                Boolean validEntry;
                do{
                    validEntry = checkYesOrNo(input);
                    if(validEntry == false){
                        System.out.println("Enter yes or no.");
                        input = scanner.next();
                    }    
                }while(validEntry == false);
                
                if(input.equalsIgnoreCase("yes")){
                    //handle the splitting here
                    split = true;
                    secondHand = new Hand();
                    secondHand.hit(player.split()); 
                    secondHand.hit(deck.hit());
                    player.hit(deck.hit());
                    player.insertionSort();
                    secondHand.insertionSort();
                    System.out.println("You now have two hands.");
                    System.out.println("Hand 1:\n" +  player.toString());
                    System.out.println("Hand 2:\n" +  secondHand.toString());
                }
                
            }
            
            System.out.println("Would you like to hit, stand, or fold.");
            
            Boolean playerTurn = true;
            Boolean playerLoses = false;
            
            do{
            String playerCommand = scanner.next(); 
                    boolean validEntry;
                do{
                    validEntry = checkValidAction(playerCommand);
                    if(validEntry == false){
                        System.out.println("Enter hit, stand, or fold.");
                        playerCommand = scanner.next();
                    }    
                }while(validEntry == false);
                if(playerCommand.equalsIgnoreCase("hit")){
                    //handle the hitting here
                    if(split == true){
                        System.out.println("Which hand would you like to hit?");
                        playerCommand = scanner.next();
                        do{
                            validEntry = checkValidHand(playerCommand);
                            if(validEntry == false){
                                System.out.println("Please enter a valid hand.");
                                playerCommand = scanner.next();
                            }    
                        }while(validEntry == false);
                        if((playerCommand.equals("1"))||(playerCommand.equals("one"))){
                            //hit hand 1
                            if(player.getHandValue() < 21){
                                player.hit(deck.hit());
                                player.insertionSort();
                                System.out.println("Hand 1:\n" +  player.toString());
                                System.out.println("Hand 2:\n" +  secondHand.toString());
                            }
                            else{
                                System.out.println("You have lost this hand. Please select another hand to hit.");
                            }
                        }
                        else if((playerCommand.equals("2"))||(playerCommand.equals("two"))){
                            //hit hand 2
                            if(secondHand.getHandValue() < 21){
                                secondHand.hit(deck.hit());
                                secondHand.insertionSort();
                                System.out.println("Hand 1:\n" +  player.toString());
                                System.out.println("Hand 2:\n" +  secondHand.toString());
                            }
                            else{
                                System.out.println("You have lost this hand. Please select another hand to hit.");
                            }
                        }
                        System.out.println("\nWould you like to hit, stand, or fold.");
                    }
                    else{
                        player.hit(deck.hit());
                        player.insertionSort();
                        System.out.println("Hand 1:\n" +  player.toString());
                        //System.out.println(player.getHandValue());
                        System.out.println("Would you like to hit, stand, or fold.");
                    }
                    if((player.getHandValue() > 21)&&(split == false)){
                        playerLoses = true;
                        playerTurn = false;
                    }
                    if((split == true)&&(player.getHandValue() > 21)&&(secondHand.getHandValue() > 21)){
                        playerLoses = true;
                        playerTurn = false;
                    }
                    
                    //After hitting the player's hand needs to be checked to see if it is 21 or over. If so that means that the player will lose.
                }
                else if(playerCommand.equalsIgnoreCase("fold")){
                    //handle the folding here
                    playerTurn = false;
                    playerLoses = true;
                }
                 else if(playerCommand.equalsIgnoreCase("stand")){
                  //handle the standing here
                  playerTurn = false;
                }
            }while(playerTurn == true);
            
            Boolean dealerUnder17 = true;//Here we need to check if the dealer's hand is 16 or under.
            Boolean dealerLoses = false; // Keeps track if the dealer goes over 21
                do{
                    if(dealerUnder17 == true){
                        //The dealer needs to hit
                        dealer.hit(deck.hit());
                        dealer.insertionSort();
                        if(dealer.getHandValue() > 16){
                            dealerUnder17 = false;
                        }
                        
                        if(dealer.getHandValue() > 21){
                            dealerLoses = true;
                        }
                    }
                }while(dealerUnder17==true);
            
            Boolean dealerWins;
            if(playerLoses == true){
                dealerWins = true;
            }
            else if(dealerLoses == true){
                dealerWins = false;
            }
            else{
                if(split == true){
                    dealerWins = compareHands(player, secondHand, dealer);
                }
                else{
                dealerWins = compareHands(player, dealer); //Compares the value of the player and dealer's hand
                }
            }
            
            //Show the player the player and dealer's hand
            System.out.println("The dealer's hand is:\n" + dealer.toString());
            System.out.println(dealer.getHandValue());
            System.out.println("Your hand is:\n" + player.toString());
            //System.out.println(player.getHandValue());
            if(split == true){
                System.out.println("Hand 2:\n" + secondHand.toString());
                //System.out.println(secondHand.getHandValue());
            }
            
            if(dealerWins == true){
                //Tell the player the dealer has one
                System.out.println("Sorry! The dealer has won.");
            }
            else{
                //Tell the player they won 
                System.out.println("Congratulations! You have won.");
            }
            
            System.out.println("Would you like to play again or save the deck? Enter 'yes' 'no' or 'save'.");
            String input = scanner.next(); //Input can have the same name as above because the other 'input' only existed in a conditional
            split = false;
            //This loop makes sure that the user enters yes or no
            Boolean validEntry;
            do{
                validEntry = checkYesOrNo(input);
                if(validEntry == false){
                    System.out.println("Enter yes, no or save.");
                    input = scanner.next();
                }    
            }while(validEntry == false);
                
            if(input.equalsIgnoreCase("yes")){
                //Check to see that the deck has more than 10 cards
                if(deck.checkCardsLeft()== false){
                    System.out.println("The deck has less than 10 cards and is being reset.");
                    deck.reset();
                }
            }
            else if(input.equalsIgnoreCase("save")){
                //save deck
                saveDeck(deck.returnDeck());
                System.out.println("Thank you for playing. Goodbye " + username + "!"); //Some sort of way to tell the user the game is over
                gameOver = true; //Ends the game
            }
            else{
                System.out.println("Thank you for playing. Goodbye " + username + "!"); //Some sort of way to tell the user the game is over
                gameOver = true; //Ends the game
            }
            
            
        }while(gameOver == false);
        
    }
    
    /*This method compares the value of the players hand to dealers to determine the winner of the round.*/
    public static Boolean compareHands(Hand playerHand, Dealer dealerHand){
        boolean dealerWins;
        
        int playerTotal = playerHand.getHandValue();
        int dealerTotal = dealerHand.getHandValue();
        
        if(dealerTotal >= playerTotal){
            dealerWins = true;
        }
        else{
            dealerWins =false;
        }
        return dealerWins;
    }
    /*Polymorphism variation of compareHands used to compare hands 1 and 2 to dealer hand if the player split.*/
    public static Boolean compareHands(Hand playerHand, Hand secondHand, Dealer dealerHand){
        boolean dealerWins;
        
        int playerTotal = playerHand.getHandValue();
        int secondHandTotal = secondHand.getHandValue();
        int dealerTotal = dealerHand.getHandValue();
        
        if((dealerTotal >= playerTotal)&&(dealerTotal >= secondHandTotal)){
            dealerWins = true;
        }
        else{
            dealerWins =false;
        }
        return dealerWins;
    }
    
    /*This method checks to see that the player has entered yes or no to answer a prompt.*/
    public static Boolean checkYesOrNo(String wordToCheck){
        if((wordToCheck.equalsIgnoreCase("yes")) || (wordToCheck.equalsIgnoreCase("no")) || (wordToCheck.equalsIgnoreCase("save"))){
            return true;
        }
        else{
            return false;
        }
    }
    /*Checks to see that player entered valid action.*/
    public static Boolean checkValidAction(String wordToCheck){
        if((wordToCheck.equalsIgnoreCase("stand")) || (wordToCheck.equalsIgnoreCase("hit")) || (wordToCheck.equalsIgnoreCase("fold"))){
            return true;
        }
        else{
            return false;
        }
    }
    /*Checks to see that player entered valid hand when splitting.*/
    public static Boolean checkValidHand(String wordToCheck){
        if((wordToCheck.equalsIgnoreCase("one")) || (wordToCheck.equalsIgnoreCase("two")) || (wordToCheck.equalsIgnoreCase("1")) || (wordToCheck.equalsIgnoreCase("2"))){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static void saveDeck(ArrayList<Card> deck){
        //save deck here
        File output = new File("deck.txt");
        try{
           PrintWriter out = new PrintWriter(output);
           for(int i = 0; i < deck.size(); i++){
               Card cardToAdd = deck.get(i);
               String string = cardToAdd.getSuit() + " " + cardToAdd.getCard() + " " + cardToAdd.getValue();
               out.println(string);
           }
           out.close();
        }
        catch(Exception e){
           System.out.println("Error encountered " + e.toString());
       }
    }
    
    public static void loadDeck(){
        File inputFile = new File("deck.txt");
        String answer;
        try{
            Scanner input = new Scanner(inputFile);
            while(input.hasNextLine()){
                String cardLine = input.nextLine();
                String[] string = cardLine.split("\\s+");
                String suit = string[0];
                int card = Integer.parseInt(string[1]);
                int value = Integer.parseInt(string[2]);
                Card cardToAdd = new Card(suit, card, value);
                deck.addCard(cardToAdd);
            } 
        } 
        catch(Exception e){
            System.out.println("The input file \"deck.txt\" was not found.");
            System.out.println(e.toString());
        }
        
    }
    
    
}