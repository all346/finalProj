package model;

//@class -> A classification will hold a String tag saying WHAT it's classified as and an int of confidenceLvl to show how strong that tag is
public class Classification {
	
	public double confidenceLvl;
	public String tag;
	
	public Classification(double confidenceLvl, String tag) {
		this.confidenceLvl = Math.round(confidenceLvl * 100.0) / 100.0;
		this.tag = tag;
	}
}
