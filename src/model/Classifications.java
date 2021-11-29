package model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Classifications {
	public ArrayList<Classification> classifications = new ArrayList<Classification>();
	
	@SuppressWarnings("unchecked")
	public ImageComponent toClassification(JSONArray tags, String imageUrl, String query) {
		this.classifications.clear();
		tags.forEach(item -> {
			JSONObject newItem = (JSONObject)item;
//			System.out.println(item);
			try {
				JSONObject newTag = (JSONObject) newItem.get("tag");
				this.classifications.add(new Classification((double)newItem.get("confidence"), (String)newTag.get("en")));
			}catch(Exception e) {
				
			}
		});
		return new ImageComponent(query, imageUrl, this.classifications);
	}
}
