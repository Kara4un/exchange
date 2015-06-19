package App;

import java.util.ArrayList;

import Order.AskOrder;
import Order.BidOrder;
import Order.Order;
import Order.OrderGlass;
import Order.OrderParser;
import OrderParams.OrderMainParam;

public class ExchangeAutomat {
	
	private ArrayList<Order> orders = new ArrayList<Order>();
	private OrderGlass glass = new OrderGlass();
	
	public void run(){
		
		int count = 0;
		init();
		OrderParser parser = new OrderParser();
		orders = parser.parseOrderList();
		int t = 0;
		while (orders.size() != 0){						
			glass.addOrder(orders.get(0));						
			printGlass(glass, t++, orders);
			do {
				count = glass.runCycle(orders.get(0));
			} while (count != 0);
			orders.remove(0);			
		}
	}
	
	public double getSum(){
		return glass.getSum();
	}
	
	public double getValue(){
		return glass.getValue();
	}
	
	public static void main (String[] arvs){
		ExchangeAutomat automat = new ExchangeAutomat();
		automat.run();
		System.out.println("The final sum is: " + automat.getSum());
		System.out.println("The final value is: " + automat.getValue());
	}
	
	public void printGlass(OrderGlass glass, int turn, ArrayList<Order> orders){
		System.out.println("\nTurn " + turn);
		System.out.println("Asks from cheap to expensive:");
		for (int i = 0; i < glass.getAsks().size(); i++){
			System.out.println(glass.getAsks().get(i));
		}
		System.out.println("\nBids from oldest to newest:");
		for (int i = 0; i < glass.getBids().size(); i++){
			System.out.println(glass.getBids().get(i));
		}
		if (orders.size() > 1) {
			System.out.println("Ready to add: " + orders.get(1) + "\n\n");
		}
		System.out.println("===============================");
				
	}
	
	public void init() {
		Order order = new AskOrder(OrderMainParam.LIM, 4, 27.8, false);
		glass.addOrder(order);
		order = new AskOrder(OrderMainParam.LIM, 700, 28, false);
		glass.addOrder(order);
		order = new BidOrder(OrderMainParam.LIM, 3, 27.2, false);
		glass.addOrder(order);
		order = new BidOrder(OrderMainParam.LIM, 600, 27, false);
		glass.addOrder(order);
	
	}

}
