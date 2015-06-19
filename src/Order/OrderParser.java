package Order;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import OrderParams.OrderMainParam;

public class OrderParser {
	
	private static String srcName = "E://orders.txt";
	
	public static ArrayList<Order> parseOrderList() {
		ArrayList<Order> orders = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(srcName))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] arvs = line.split(" ");
		    	Order newOrder = null;
		    	switch (arvs.length) {
		    		case 3: {
		    			if (arvs[0].equals("buy")){
		    				newOrder = new BidOrder(OrderMainParam.MKR, 
		    						Integer.parseInt(arvs[2]), 0, false);
		    				break;
		    			}
		    			else {
		    				newOrder = new AskOrder(OrderMainParam.MKR, 
		    						Integer.parseInt(arvs[2]), 0, false);
		    				break;
		    			}
		    		}
		    		case 4: {
		    			if (arvs[0].equals("buy")){
		    				newOrder = new BidOrder(OrderMainParam.LIM, 
		    						Integer.parseInt(arvs[3]), Double.parseDouble(arvs[2]), false);
		    				break;
		    			}
		    			else {
		    				newOrder = new AskOrder(OrderMainParam.LIM, 
		    						Integer.parseInt(arvs[3]), Double.parseDouble(arvs[2]), false);
		    				break;
		    			}
		    		}
		    		case 5: {
		    			newOrder = new BidOrder(OrderMainParam.MKR, 
	    						Integer.parseInt(arvs[4]), Double.parseDouble(arvs[3]), true);		    			

		    		}
		    	}
		    	orders.add(newOrder);
		    }
		} catch (FileNotFoundException fe){
			System.out.println("Ooooops... I can't find file");
		} catch (IOException io){
			System.out.println("Ooooops... I can't create reader");
		}
		
		return orders;
	}

}
