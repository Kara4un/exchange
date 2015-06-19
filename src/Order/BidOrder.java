package Order;

import OrderParams.OrderMainParam;

public class BidOrder extends Order{

	public BidOrder(OrderMainParam aParam, int aVolume, double aPrice,
			boolean aHasStop) {
		super(aParam, aVolume, aPrice, aHasStop);
	}	

	@Override
	public double makeOperation(Order order) {
		if (order instanceof BidOrder){
			return 0;
		}
		if (((param.equals(OrderMainParam.LIM) && order.getPrice() <= price) || param.equals(OrderMainParam.MKR)
				|| order.getMainParam().equals(OrderMainParam.MKR)) && !hasStop){
			if (param.equals(OrderMainParam.MKR) && order.getMainParam().equals(OrderMainParam.MKR)){
				return 0;
			}
			return order.makeOperation(this);
		}
		return 0;		
	};
	
	@Override
	public String toString(){
		return "buy" + super.toString();
	}

}
