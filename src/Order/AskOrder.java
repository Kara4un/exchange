package Order;

import OrderParams.OrderMainParam;

public class AskOrder extends Order  {

	public AskOrder(OrderMainParam aParam, int aVolume, double aPrice,
			boolean aHasStop) {
		super(aParam, aVolume, aPrice, aHasStop);
		// TODO Auto-generated constructor stub
	}
	
	public double makeOperation(Order order){
		if (order instanceof AskOrder){
			return 0;
		}
		if (((param.equals(OrderMainParam.LIM) && order.getPrice() >= price) || param.equals(OrderMainParam.MKR)
				|| order.getMainParam().equals(OrderMainParam.MKR)) && !hasStop){
			if (param.equals(OrderMainParam.MKR) && order.getMainParam().equals(OrderMainParam.MKR)){
				return 0;
			}
			int volume0 = volume;
			volume-=order.volume;
			if (volume < 0) volume = 0;
			((BidOrder)order).buy(volume0 - volume);
			double retPrice = 0;
			if (price == 0) {
				retPrice = order.getPrice();
			} else if (order.getPrice() == 0){
				 retPrice = price;
			} else {
				retPrice = Math.min(price, order.getPrice());
			}
			return (volume0 - volume);//*retPrice;
		}
		return 0;
	}	
	
	@Override
	public String toString(){
		return "sell" + super.toString();
	}

}
