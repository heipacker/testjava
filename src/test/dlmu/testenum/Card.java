package test.dlmu.testenum;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Card {
	public enum Rank {
		DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	}

	public enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	private final Rank rank;
	private final Suit suit;

	private Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Rank rank() {
		return rank;
	}

	public Suit suit() {
		return suit;
	}

	public String toString() {
		return rank + " of " + suit;
	}
	
	private static Map<Suit, Map<Rank, Card>> table = new EnumMap<Suit, Map<Rank, Card>>(Suit.class);

	static {
		for (Suit suit : Suit.values()) {
			Map<Rank, Card> suitTable = new EnumMap<Rank, Card>(Rank.class);
			for (Rank rank : Rank.values())
				suitTable.put(rank, new Card(rank, suit));
			table.put(suit, suitTable);
		}
	}

	public static Card valueOf(Rank rank, Suit suit) {
		return table.get(suit).get(rank);
	}
	
	private static final List<Card> protoDeck = new ArrayList<Card>();

	/**
	 * Initialize prototype deck
	 */
	static {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				protoDeck.add(Card.valueOf(rank, suit));
			}
		}
	}

	/**
	 * Return copy of prototype deck
	 * 
	 * @return
	 */
	public static ArrayList<Card> newDeck() {
		return new ArrayList<Card>(protoDeck);
	}
}