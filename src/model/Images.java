package model;

import java.util.ArrayList;

public class Images {
	public static int currImage = 0;
	public static String query;
	public static ArrayList<String> imageLinks = new ArrayList<String>();
	
	public Images(String search, ArrayList<String> list) {
		query = search;
		imageLinks = list;
	}
}
