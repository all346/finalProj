package model;

import java.util.ArrayList;

public class ImageComponent {
	public String query, imageLink;
	public ArrayList<Classification> imageClassifications = new ArrayList<Classification>();
	public static ImageComponent currentComponent;
	public static ArrayList<ImageComponent> allImageData = new ArrayList<ImageComponent>();
	
	public ImageComponent(String query, String imageLink, ArrayList<Classification> tags) {
		this.query = query;
		this.imageLink = imageLink;
		this.imageClassifications = tags;
	}
}
