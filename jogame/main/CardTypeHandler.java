package jogame.main;

import java.io.BufferedReader;
import java.io.FileReader;

public class CardTypeHandler {
	
	private String data[][];
	private int card_pos = -1;
	
	public CardTypeHandler() {
		//Reads through CSV file to find all card types and stores in array
		String file = "files/cards.csv";
		String line = "";
		String first_line[] = new String[15];
		int i = 0;
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			line = br.readLine();
			first_line = line.split(",");
			data = new String[Integer.parseInt(first_line[14])][14];
			while((line = br.readLine()) != null) {
				data[i] = line.split(",");
				i++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public CardType getCard(String name) {
		for(int i = 0; i < data.length; i++) 
			if(data[i][0].equals(name)) {
				CardType type = new CardType(data[i][0], CardClass.valueOf(data[i][1]), Integer.parseInt(data[i][2]), Integer.parseInt(data[i][3]), Integer.parseInt(data[i][4]), Integer.parseInt(data[i][5]), 
						Id.valueOf(data[i][6]), data[i][7], Integer.parseInt(data[i][8]), data[i][9], Integer.parseInt(data[i][10]), Boolean.parseBoolean(data[i][11]), data[i][12], data[i][13]);
				return type;
			}
		return null;
	}
	
	public String getClassCard(String card_class) {
		for(int i = card_pos + 1; i < data.length; i++) {
			if(data[i][1].equals(card_class)) {
				String type = data[i][0];
				card_pos = i;
				return type;
			}
		}
		return "";
	}
	
	public void setCardPos() {
		card_pos = 0;
	}
}
