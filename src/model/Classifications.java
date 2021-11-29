package model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//@class -> Holds an array of classifications, sole purpose is to create a classification object from the data received from our API's. In other words formatting our data
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
