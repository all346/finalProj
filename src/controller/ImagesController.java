package controller;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

import javax.swing.GroupLayout.Alignment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.APIConnector;
import model.Classification;
import model.Classifications;
import model.ImageComponent;
import model.Images;

public class ImagesController implements Initializable {
	
	@FXML
    private AnchorPane imagesPane;

	@FXML
    private ImageView foundImage;

    @FXML
    private VBox imageClassifications;
	
	public final String imageUrl = "https://imsea.herokuapp.com/api/1?q=";
	public final String detectorUrl = "https://api.imagga.com/v2/tags?image_url=";
	public final String basicAuth = Base64.getEncoder().encodeToString("acc_68a450756f55a92:7521d991f3a1533eca834b446f5b4c9b".getBytes(StandardCharsets.UTF_8));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<ImageComponent> allImageData = new ArrayList<ImageComponent>();
		Classifications classifications = new Classifications();
		APIConnector imgConnection = new APIConnector(imageUrl);
		APIConnector mlConnection = new APIConnector(detectorUrl);
		
		JSONObject imgObj = imgConnection.connectAPI(Images.query);

		JSONArray images = new JSONArray();
		images = (JSONArray) imgObj.get("results");
		
		//Adding to ArrayList or URL's
		for(int i = 0; i < images.size(); i++) { 
			if(i % 2 == 0) {
				Images.imageLinks.add((String) images.get(i));
			}
			if(i > 8) {
				break;
			}
		}

		//Iterating through list of URL's to send them to ML and receive back Classifications
		//Cannot call API more because of call limit
		for(int i = 0; i < 2; i++) {
			JSONArray tagsData = mlConnection.authConnectApi(Images.imageLinks.get(i));
			allImageData.add(classifications.toClassification(tagsData, Images.imageLinks.get(i), Images.query));
		}
		
//		for(int i = 0; i < allImageData.size(); i++) {
//			System.out.println(allImageData.get(i).imageLink);
//		}
		
		//Iterating through classifications to display them
		Image image = new Image(allImageData.get(0).imageLink);
		foundImage.setImage(image);
		
		for(Classification tag : allImageData.get(0).imageClassifications) {
			
//			for(int i = 0;  i < allImageData.size(); i++) {
//				Classification tag  = allImageData.get(i).imageClassifications.get(1);
				
//				System.out.println(allImageData.get(i));
				
				HBox tagsBox = new HBox();
				tagsBox.setMaxWidth(300);
				tagsBox.setAlignment(Pos.CENTER);
				
				Label tagLabel = new Label(tag.tag);
				tagLabel.getStyleClass().add("label");
				tagLabel.setTranslateX(-60);
				
				Label confidenceLabel = new Label(String.valueOf(tag.confidenceLvl));
				confidenceLabel.getStyleClass().add("label");
				confidenceLabel.setTranslateX(50);
	
				tagsBox.getChildren().addAll(tagLabel, confidenceLabel);
				imageClassifications.getChildren().add(tagsBox);
			}
	}
	
}
