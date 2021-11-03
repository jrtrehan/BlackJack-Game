package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

public class BlackjackModel {
	
	private ArrayList <Card> playerCards; 
	private ArrayList <Card> dealerCards; 
	private Deck deck; 
	/*
	 * Gets the players cards. 
	 * @returns the arrayList of the players cards. 
	 */
	public ArrayList<Card> getPlayerCards(){
		ArrayList<Card> answer = new ArrayList<Card>(); 
		for(int i = 0; i < playerCards.size(); i++) {
			answer.add(playerCards.get(i));
		}
		return answer; 
	}
	/*
	 * Gets the dealers cards. 
	 * @return the arrayList of the dealers cards. 
	 */
	public ArrayList<Card> getDealerCards(){
		ArrayList<Card> answer = new ArrayList<Card>(); 
		for(int i = 0; i < dealerCards.size(); i++) {
			 answer.add(dealerCards.get(i));
		}
		return answer; 
	}
	/*
	 * Sets the players cards given cards. 
	 */
	public void setPlayerCards(ArrayList<Card> cards) {
		for(int i = 0; i < cards.size(); i++) {
			playerCards.set(i, cards.get(i)); 
		}
	}
	/*
	 * Sets the dealers cards given cards. 
	 */
	public void setDealerCards(ArrayList<Card> cards) {
		for(int i = 0; i < cards.size(); i++) {
			dealerCards.set(i, cards.get(i)); 
		}
	}
	/*
	 * Creates and shuffles a new deck. 
	 */
	public void createAndShuffleDeck(Random random) {
		deck = new Deck(); 
		deck.shuffle(random);
	}
	/*
	 * Initializes the dealers cards. 
	 */
	public void initialDealerCards() {
		dealerCards = new ArrayList<Card>(); 
		dealerCards.add(deck.dealOneCard()); 
		dealerCards.add(deck.dealOneCard()); 
	}
	/*
	 * Initializes the players cards. 
	 */
	public void initialPlayerCards() {
		playerCards = new ArrayList<Card>();
		playerCards.add(deck.dealOneCard()); 
		playerCards.add(deck.dealOneCard()); 
	}
	/*
	 * Deals a card to the player. 
	 */
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
	/*
	 * Deals a card to the dealer. 
	 */
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard()); 
	}
	/*
	 * Assess the current state of the hand. 
	 * @return the HandAssessment of the current hand. 
	 */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if(hand == null || hand.size() < 2) { //insufficient hand
			return HandAssessment.INSUFFICIENT_CARDS; 
		}
		
		int total = 0; //bust hand && blackjack hand 
		boolean ace = false;  
		boolean ten = false; 
		for(int i = 0; i < hand.size(); i++) {
			total += hand.get(i).getRank().getValue(); 
			if(hand.get(i).getRank().getValue() == 1) {
				ace = true; 
			}
			if(hand.get(i).getRank().getValue() == 10) {
				ten = true; 
			}
		}
		if(total > 21) {
			return HandAssessment.BUST; 
		}
		
		if(total == 11 && hand.size() == 2 && ace && ten) {
			return HandAssessment.NATURAL_BLACKJACK; 
		}
		
		return HandAssessment.NORMAL; //normal hand 
		
	}
	/*
	 * Determines the possible values of the hand given the hand arraylist. 
	 * @return an arraylist of the possible hand values. 
	 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand){
		ArrayList<Integer> answer = new ArrayList<Integer>(); 
		int total = 0; 
		boolean ace = false; 
		for(int i = 0; i < hand.size(); i++) {
			total += hand.get(i).getRank().getValue();
			if(hand.get(i).getRank().getValue() == 1) {
				ace = true; 
			}
		}
		
		if(!ace) {
			answer.add(total); 
		}
		if(ace) {
			total--; 
			if(total + 11 <= 21) {
				answer.add(total + 1);
				answer.add(total + 11); 
			}
			if(total + 11 > 21 && total + 1 <= 21) {
				answer.add(total + 1); 
			}
			if(total + 11 > 21 && total + 1 > 21) {
				answer.add(total + 1); 
			}
		}
		return answer;
	}
	/*
	 * Determine if the dealer should take another card given the current hand total. 
	 * @return true if the dealer should take another card, false otherwise. 
	 */
	public boolean dealerShouldTakeCard() {
		int total = 0; 
		boolean ace = false; 
		for(int i = 0; i < dealerCards.size(); i++) {
			total += dealerCards.get(i).getRank().getValue(); 
			if(dealerCards.get(i).getRank().getValue() == 1) {
				ace = true; 
			}
		}

		if( !ace && total == 17) {
			return false; 
		}
		if(total == 7 && ace) {
			return true;
		}
		if( ace && total >= 8) {
			return false; 
		}
		if(total >= 18) {
			return false;
		}
		if(total <= 16) {
			return true; 
		}
		return false; 
		
	}
	/*
	 * Assess the result of the game given the hands the player and dealer are holding. 
	 * @return the GameResult enum. 
	 */
	public GameResult gameAssessment() {
		ArrayList<Integer> playerValues = possibleHandValues(playerCards); 
		ArrayList<Integer> dealerValues = possibleHandValues(dealerCards);
		
		if(assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK) {
			if(assessHand(dealerCards) == HandAssessment.NATURAL_BLACKJACK) {
				return GameResult.PUSH;  
			}else {
				return GameResult.NATURAL_BLACKJACK; 
			}
		}
		
		if(assessHand(playerCards) == HandAssessment.BUST) {
			return GameResult.PLAYER_LOST; 
		}
		if(assessHand(dealerCards) == HandAssessment.BUST) {
			return GameResult.PLAYER_WON; 
		}
		
		
		int highestPlayerValue = 0;
		int highestDealerValue = 0; 
		for(int i = 0; i < playerValues.size(); i++) {
			if(playerValues.get(i) > highestPlayerValue && playerValues.get(i) < 22) {
				highestPlayerValue = playerValues.get(i); 
			}
		}
		for(int i = 0; i < dealerValues.size(); i++) {
			if(dealerValues.get(i) > highestDealerValue && dealerValues.get(i) < 22) {
				highestDealerValue = dealerValues.get(i); 
			}
		}
		if(highestDealerValue < highestPlayerValue) {
			return GameResult.PLAYER_WON;
		}
		if(highestDealerValue > highestPlayerValue) {
			return GameResult.PLAYER_LOST;
		}
		
		if(highestDealerValue == highestPlayerValue) {
			return GameResult.PUSH; 
		}
		return GameResult.PLAYER_WON; 
	}
}
