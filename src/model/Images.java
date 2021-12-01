package model;

import java.util.ArrayList;


//@class -> 
// Holds a query, current image count and an arraylist of links to our images.
//Not used because multipage is not functional
public class Images {
	public static int currImage = 0;
	public static boolean isLocal = false;
	public static String imagePath = "";
	public static String query;
	public static ArrayList<String> imageLinks = new ArrayList<String>();
	
	public Images(String search, ArrayList<String> list) {
		query = search;
		imageLinks = list;
	}
}
