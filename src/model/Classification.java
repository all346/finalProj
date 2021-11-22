package model;

public class Classification {
	
	public double confidenceLvl;
	public String tag;
	
	public Classification(double confidenceLvl, String tag) {
		this.confidenceLvl = Math.round(confidenceLvl * 100.0) / 100.0;
		this.tag = tag;
	}
}
