package jogame.main;

import java.io.BufferedReader;
import java.io.FileReader;

public class StdDeckManager extends DeckManager{

	public StdDeckManager(String deck) {
		super(0, 0);
		
		//Reads through CSV file to find all card types and stores in array
		String file = "files/" + deck + ".csv";
		String line = "";
		int count = 0;
		String names[] = new String[1];
		int counts[] = new int[1];
		String first_line[];
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			line = br.readLine();
			first_line = line.split(",");
			names = new String[Integer.parseInt(first_line[2])];
			counts = new int[Integer.parseInt(first_line[2])];
			while((line = br.readLine()) != null) {
				first_line = line.split(",");
				names[count] = first_line[0];
				counts[count] = Integer.parseInt(first_line[1]);
				count++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < names.length; i++)
			add(names[i], counts[i]);
	}
}
