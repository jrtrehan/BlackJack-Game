package deckOfCards;

/** This class represents a playing card that would be included in 
 * a standard deck of 52 cards.  
 * 
 *
 */
public class Card {
	
	private Suit suit;
	private Rank rank;
	
	public Card(Rank rank, Suit suit) {
		this.suit = suit;
		this.rank = rank;
	}
	/*
	 * Gets the rank of the Card. 
	 * @return the rank of the Card.  
	 */
	public Rank getRank() {
		return rank;
	}
	/*
	 * Gets the suit of the Card. 
	 * @return the suit of the Card. 
	 */
	public Suit getSuit() {
		return suit;
	}
	/*
	 * Prints the Card's rank and suit. 
	 * @return the string of the Card description. 
	 */
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	/*
	 * Determines if the Cards are equal. 
	 * @return true if they are the same, false otherwise. 
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Card)) {
			return false;
		}
		Card card = (Card)other;
		return card.suit == suit && card.rank == rank;
	}
} 
