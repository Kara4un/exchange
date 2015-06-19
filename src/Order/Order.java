package Order;

import OrderParams.OrderMainParam;
import OrderParams.OrderType;

public abstract class Order implements Comparable<Order> {
		
	protected OrderMainParam param = null;
	protected int volume = 0;
	protected double price = 0;
	protected boolean hasStop = false;
	
	
	public Order (OrderMainParam aParam, int aVolume, double aPrice, boolean aHasStop){		
		param = aParam;
		volume = aVolume;
		price = aPrice;
		hasStop = aHasStop;
	}
	
	public abstract double makeOperation(Order order);
	
	public int getVolume (){
		return volume;
	}
	
	public boolean hasStop(){
		return hasStop;
	}
	
	public void openStop(){
		hasStop = false;
		price = 0;
	}
	
	public double getPrice (){
		return price;
	}
	
	public OrderMainParam getMainParam() {
		return param;
	}
	
	public void buy(int aVolume){
		volume-=aVolume;
				
	}
	
	@Override
	public int compareTo(Order other){
		if (price < other.price) return -1;
		if (price > other.price) return 1;
		return 0;
	}
	
	@Override
	public String toString(){
		return " " + param + " " + volume + " " + price + " " + hasStop;
	}
	
}
