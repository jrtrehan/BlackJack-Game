package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

	private ArrayList<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				cards.add(new Card(Rank.values()[j], Suit.values()[i]));
			}
		}
	}
	/*
	 * Shuffles the deck using a random number generator.
	 */
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(cards, randomNumberGenerator);
	}
	/*
	 * Deals one Card. 
	 * @return the Card that was dealed. 
	 */
	public Card dealOneCard() {
		return cards.remove(0); 
	}
	
	
}
