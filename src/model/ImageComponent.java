package model;

import java.util.ArrayList;

public class ImageComponent {
	public String query, imageLink;
	public ArrayList<Classification> imageClassifications = new ArrayList<Classification>();
	
	public ImageComponent(String query, String imageLink, ArrayList<Classification> tags) {
		this.query = query;
		this.imageLink = imageLink;
		this.imageClassifications = tags;
	}
}
