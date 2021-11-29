package model;

import java.util.ArrayList;

//@class -> Image component is the entire component with all data for one image component.
//Should hold a query for the image, the actual imageLink, and all the tags and confidence levels for that image. All of our data compressed into one class/object
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
