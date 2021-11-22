package controller;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.fxml.Initializable;
import model.APIConnector;
import model.Classifications;
import model.Images;

public class ImagesController implements Initializable {
	public final String imageUrl = "https://imsea.herokuapp.com/api/1?q=";
	public final String detectorUrl = "https://api.imagga.com/v2/tags?image_url=";
	public final String basicAuth = Base64.getEncoder().encodeToString("acc_68a450756f55a92:7521d991f3a1533eca834b446f5b4c9b".getBytes(StandardCharsets.UTF_8));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		APIConnector imgConnection = new APIConnector(imageUrl);
		APIConnector mlConnection = new APIConnector(detectorUrl);
		JSONObject imgObj = imgConnection.connectAPI(Images.query);

		JSONArray images = new JSONArray();
		images = (JSONArray) imgObj.get("results");
//		System.out.println(images);

		for(int i = 0; i < images.size(); i++) { 
				Images.imageLinks.add((String) images.get(i));
//				System.out.println(Images.imageLinks.get(i));
		}
		
		JSONArray tagsData = mlConnection.authConnectApi(Images.imageLinks.get(0), basicAuth);
		Classifications.toClassification(tagsData);
//		System.out.println(tagsData);
		
	}
	
}
