package jogame.main;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	private Random r = new Random();
	private Player player;
	
	public Deck(DeckManager deck_manager, CardTypeHandler card_handler, Player player) {
		this.player = player;

		create(deck_manager, card_handler);
	}
	
	public void create(DeckManager deck_manager, CardTypeHandler card_handler) {
		int card_height = Window.act_height / 5 - 10;
		
		for(int i = 0; i < deck_manager.getNames().size(); i++) {
			for(int j = 0; j < deck_manager.getCounts().get(i); j++) {
				String type = deck_manager.getNames().get(i);
				deck.add(new Card(card_handler.getCard(type), card_height, player.getHand()));
			}
		}
	}
	
	public Card drawCard() {
		if(deck.size() > 0) {
			int card_num = r.nextInt(deck.size());
			Card card = deck.get(card_num);
			deck.remove(card_num);
			return card;
		}else
			return null;
	}
	
	public Card drawCard(String name) {
		for(int i = 0; i < deck.size(); i++) {
			if(deck.get(i).getName().equals(name))
				return deck.get(i);
		}
		return null;
	}
	
	public void add(Card card) {
		deck.add(card);
	}
	
	public void clear() {
		deck.clear();
	}
	
	public int getCardCount() {
		return deck.size();
	}
}
