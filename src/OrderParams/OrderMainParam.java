package OrderParams;

public enum OrderMainParam {
	
	LIM("lim"),
	MKR("mkt");
	
	public String tag = "";
	
	private OrderMainParam(String aTag){
		tag = aTag;
	}
	
	public String toString(){
		return tag;
	}

}
