package model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Classifications {
	public static ArrayList<Classification> classifications = new ArrayList<Classification>();
	
	
	@SuppressWarnings("unchecked")
	public static void toClassification(JSONArray tags) {
		tags.forEach(item -> {
			JSONObject newItem = (JSONObject)item;
//			System.out.println(item);
			try {
				JSONObject newTag = (JSONObject) newItem.get("tag");
//				long num = (long) newItem.get("confidence");
				classifications.add(new Classification((double)newItem.get("confidence"), (String)newTag.get("en")));
			}catch(Exception e) {
				
			}
		});
		for(Classification dank: Classifications.classifications) {
			System.out.println(dank.confidenceLvl + "|||||" + dank.tag);
		}
	}
}
