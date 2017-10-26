package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Hand{
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Map<Card, Integer> res_cards = new ConcurrentHashMap<Card, Integer>();

	private int food_count = 0;
	private int lumber_count = 0;
	private int mana_count = 0;
	private Scene scene;
	private Player player;
	
	public Hand(Scene scene, Player player) {
		this.scene = scene;
		this.player = player;
	}
	
	//Adds a card to the hand
	public void add(Card card) {
		if(card != null) {
			if(card.getCard().getId() == Id.Resource) {
				boolean found = false;
				for(Card key : res_cards.keySet()) 
					if(!key.getName().equals(card.getName())) 
						continue;
					else {
						found = true;
						res_cards.replace(key, res_cards.get(key) + 1);
						break;
					}
				if(!found) {
					card.setHandPos(res_cards.size());
					res_cards.put(card, 1);
				}
				updateResCount();
				updateResCardWidth();
				card.setPlayable(false);
			}else {
				card.setHandPos(cards.size());
				cards.add(card);
				updateCardWidth();
			}
		}
		updatePlayable();
	}
	
	//Renders all cards in the hand
	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 20);
		for(Card key : res_cards.keySet()) {
			key.render(g);
			g.setFont(fnt);
			g.setColor(Color.black);
			g.drawString("x" + res_cards.get(key), (int)(key.getBounds().getX() + key.getBounds().getWidth()/2 - Game.getStringWidth("x" + res_cards.get(key), fnt)/2), 
					(int)(key.getBounds().getY() + key.getBounds().getHeight()/10*9));
		}
		for(int i = 0; i < cards.size(); i ++) 
			cards.get(i).render(g);
	}
	
	//Removes a card from the hand and shifts all cards
	public void shift(Card card) {
		int spot = card.getHandPos();
		if(card.getCard().getId() == Id.Resource)
			subResCount(card);
		cards.remove(spot);
		for(int i = 0; i < cards.size(); i++) {
			Card temp = cards.get(i);
			temp.setHandPos(i);
		}
		updateCardWidth();
		updatePlayable();
	}
	
	//Removes all resource cards needed to play the card
	public void subResCount(Card card) {
		if(card.getName().equals("Food"))
			food_count--;
		if(card.getName().equals("Lumber"))
			lumber_count--;
		if(card.getName().equals("Mana"))
			mana_count--;
	}
	
	//Increases resource count if resource card is drawn
	public void updateResCount() {
		boolean food_found = false;
		boolean lumber_found = false;
		boolean mana_found = false;
		for(Card key: res_cards.keySet()) {
			if(key.getName().equals("Food")) {
				food_count = res_cards.get(key);
				food_found = true;
			}
			else if(key.getName().equals("Lumber")) {
				lumber_count = res_cards.get(key);
				lumber_found = true;
			}
			else if(key.getName().equals("Mana")) {
				mana_count = res_cards.get(key);
				mana_found = true;
			}
		}
		if(!food_found)
			food_count = 0;
		if(!lumber_found)
			lumber_count = 0;
		if(!mana_found)
			mana_count = 0;
	}
	
	//Changes the card widths depending on the number of cards in hand
	public void updateCardWidth() {
		int new_width = 0;
		if(cards.size() > 6)
			new_width = Window.act_width / 16 * 9 / cards.size() - 5;
		else
			new_width = Window.act_width / 16 * 9 / 6 - 5;
		for(int i = 0; i < cards.size(); i++) 
			cards.get(i).updatePos(new_width);
	}
	
	public void updateResCardWidth() {
		int new_width = 0;
		if(res_cards.size() > 2)
			new_width = Window.act_width / 16 * 3 / res_cards.size() - 5;
		else
			new_width = Window.act_width / 16 * 3 / 2 - 5;
		for(Card key : res_cards.keySet()) 
			key.updatePos(new_width);
	}
	
	//Checks the cards' resources against available resources in hand
	public void updatePlayable() {
		for(int i = 0; i < cards.size(); i++) {
			Card temp = cards.get(i);
			String res1 = temp.getRes1();
			String res2 = temp.getRes2();
			int cost1 = temp.getCost1();
			int cost2 = temp.getCost2();
			if(temp.getCard().getId() == Id.Resource)
				temp.setPlayable(false);
			else if(res1.equals("Food") && cost1 > food_count)
				temp.setPlayable(false);
			else if(res1.equals("Lumber") && cost1 > lumber_count)
				temp.setPlayable(false);
			else if(res1.equals("Mana") && cost1 > mana_count)
				temp.setPlayable(false);
			else if(res2.equals("Food") && cost2 > food_count)
				temp.setPlayable(false);
			else if(res2.equals("Lumber") && cost2 > lumber_count)
				temp.setPlayable(false);
			else if(res2.equals("Mana") && cost2 > mana_count) 
				temp.setPlayable(false);
			else
				temp.setPlayable(true);
		}
	}
	
	//Removes all the resources needed when a card is played
	public void removeRes(Card card) {
		String res1 = card.getRes1();
		String res2 = card.getRes2();
		int cost1 = card.getCost1();
		int cost2 = card.getCost2();
		for(Card key: res_cards.keySet()) {
			if(key.getName().equals(res1))
				res_cards.replace(key, res_cards.get(key) - cost1);
			else if(key.getName().equals(res2))
				res_cards.replace(key, res_cards.get(key) - cost2);
			if(res_cards.get(key) == 0) {
				res_cards.remove(key);
				updateResCardWidth();
			}
		}
		updateResCount();
	}
	
	//Removes single resource by string name
	public void removeRes(String name) {
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).getName().equals(name)) {
				cards.remove(cards.get(i));
				return;
			}
		}
	}
	
	//Clears the hand
	public void clear() {
		cards.clear();
		res_cards.clear();
		food_count = 0; 
		lumber_count = 0;
		mana_count = 0;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public boolean isTurn() {
		return player.isTurn();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getFoodCount() {
		return food_count;
	}
	
	public int getLumberCount() {
		return lumber_count;
	}
	
	public int getManaCount() {
		return mana_count;
	}
}
