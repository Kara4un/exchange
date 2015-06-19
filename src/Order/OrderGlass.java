package Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;

import OrderParams.OrderMainParam;

public class OrderGlass {
	
	private ArrayList<BidOrder> bids = new ArrayList<>();
	private ArrayList<AskOrder> asks = new ArrayList<>();
	private double sum = 0;
	private double value = 0;

	public int runCycle(Order order){
		ArrayList<AskOrder> delAsks = new ArrayList<>();
		ArrayList<BidOrder> delBids = new ArrayList<>();
		int count = 0;			
		if (order.hasStop()){
			return 0;
		}
		if (order.getMainParam().equals(OrderMainParam.MKR)){
			if (order instanceof AskOrder){
				mktIt((AskOrder)order);
			} else {
				mktIt((BidOrder)order);
			}
		} else {
			for (BidOrder bid : bids){
				for (AskOrder ask : asks){
					double res = bid.makeOperation(ask);
					if (res != 0) {
						count++;
						checkBuyStops(whatWasPrice(bid, ask));
						if (ask.getVolume() == 0) delAsks.add(ask);
						value+=res;
						sum+=res*whatWasPrice(bid, ask);
						
						System.out.println("Продано акций: " + res);
						System.out.println("Продано на сумму: " + res*whatWasPrice(bid, ask));
						System.out.println("--------------------");
					} else {
						break;
					}
				}			
				asks.removeAll(delAsks);
				if (bid.getVolume() == 0 || 
						(bid.getMainParam().equals(OrderMainParam.MKR) && 
								!bid.hasStop)) delBids.add(bid);				
			}						
			bids.removeAll(delBids);
		}		
		return count;
	}
	
	public double getSum() {
		return sum;
	}
	
	public double getValue(){
		return value;
	}
	
	public void checkBuyStops(double price){
		for (BidOrder bid : bids){
			if (bid.hasStop() && bid.getPrice() < price){
				bid.openStop();
			}
		}
	}
	
	public double whatWasPrice(BidOrder bid, AskOrder ask){		
		if (bid.getPrice() == 0) {
			return ask.getPrice();
		}
		if (ask.getPrice() == 0) {
			return bid.getPrice();
		}		
 		return (bid.getPrice() < ask.getPrice()) ? bid.getPrice() : ask.getPrice();
	}
	
	public int getMinimumPrice(ArrayList<Order> orders){
		int ret = 0;
		for (int i = 0; i < orders.size(); i++){
			if (orders.get(ret).getPrice() >= orders.get(i).getPrice()) ret = 0; 
		}
		return ret;
	}
	
	public void mktIt(AskOrder order){		
		ArrayList<BidOrder> delBids = new ArrayList<>();
		int count = 0;
		while (count < bids.size()){			
			BidOrder bid = getMaxPricedBid();
			double ret = bid.makeOperation(order);			
			if (bid.getVolume() == 0 ) delBids.add(bid);
			if (ret != 0) {
				count++;				
				value+=ret;
				sum+=ret*whatWasPrice(bid, order);
				
				System.out.println("Продано акций: " + ret);
				System.out.println("Продано на сумму: " + ret*whatWasPrice(bid, order));
				System.out.println("--------------------");
			} else {
				break;
			}					
								
		}						
		bids.removeAll(delBids);		
		asks.remove(order);
	}
	
	
	public void mktIt(BidOrder order){
		ArrayList<AskOrder> delAsks = new ArrayList<>();		
		for (AskOrder ask : asks){						
			double ret = order.makeOperation(ask); 
			if (ask.getVolume() == 0 ) delAsks.add(ask);
			if (ret == 0) {
				break;				
			}		
			value+=ret;
			sum+=ret*whatWasPrice(order, ask);
			
			System.out.println("Продано акций: " + ret);
			System.out.println("Продано на сумму: " + ret*whatWasPrice(order, ask));
			System.out.println("--------------------");								
		}						
		bids.remove(order);		
		asks.removeAll(delAsks);
	}

	private BidOrder getMaxPricedBid() {
		BidOrder ret = null;
		for (BidOrder bid : bids){
			if (ret == null || ret.getPrice() < bid.getPrice()){
				ret = bid;
			}
		}
		return ret;
	}	
	
	public void addOrder(Order newOrder){
		if (newOrder instanceof AskOrder) asks.add((AskOrder)newOrder);
		if (newOrder instanceof BidOrder) bids.add((BidOrder)newOrder);
		Collections.sort(asks, new OrderComparator());
	}				
	
	private class OrderComparator implements Comparator<Order>{

		@Override
		public int compare(Order o1, Order o2) {
			return o1.compareTo(o2);
		}
		
	}
	
	public ArrayList<AskOrder> getAsks(){
		return asks;
	}
	
	public ArrayList<BidOrder> getBids(){
		return bids;
	}
	
}
