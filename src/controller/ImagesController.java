package controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ResourceBundle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.APIConnector;
import model.Classifications;
import model.ImageComponent;
import model.Images;


//@class -> Will control handling the images and placing the backend data onto frontend
//@methods -> creates the connections with the required API's and used the query to call the API's, later formats them into our model formats.
// After formatted into desired categories, we will iterate through each piece of data and display on frontend.
//@methods -> Go home button will handle returning to home page
public class ImagesController implements Initializable {
	
	@FXML
    private AnchorPane imagesPane;

	@FXML
    private ImageView foundImage;

	@FXML
    private Text lvl1;

    @FXML
    private Text lvl2;

    @FXML
    private Text lvl3;

    @FXML
    private Text lvl4;

    @FXML
    private Text lvl5;

    @FXML
    private Text tag1;

    @FXML
    private Text tag2;

    @FXML
    private Text tag3;

    @FXML
    private Text tag4;

    @FXML
    private Text tag5;
    
    @FXML
    private Text lvl6;
    
    @FXML
    private Text tag6;
	
    @FXML
    private VBox imageClassifications;
	
	public final String imageUrl = "https://imsea.herokuapp.com/api/1?q=";
	public final String detectorUrl = "https://api.imagga.com/v2/tags?image_url=";
	public final String basicAuth = Base64.getEncoder().encodeToString("acc_68a450756f55a92:7521d991f3a1533eca834b446f5b4c9b".getBytes(StandardCharsets.UTF_8));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Classifications classifications = new Classifications();
		APIConnector imgConnection = new APIConnector(imageUrl);
		APIConnector mlConnection = new APIConnector(detectorUrl);
		refresh();

		//Local Image
		if(Images.isLocal) {
			String result = mlConnection.connectLocalImage(Images.imagePath);
			JSONParser parser = new JSONParser();
			
			JSONObject uploadID;
			try {
				uploadID = (JSONObject) parser.parse(result);
				uploadID = (JSONObject) uploadID.get("result");
				JSONArray tagsData = mlConnection.callLocalImage((String) uploadID.get("upload_id"));
				
				ImageComponent.allImageData.add(classifications.toClassification(tagsData, Images.imagePath, result));
				updateUI();

				
			} catch (ParseException e) {
				e.printStackTrace();
			}

			
		//Query Image
		}else {
			JSONObject imgObj = imgConnection.connectAPI(Images.query);
			JSONArray images = new JSONArray();
			images = (JSONArray) imgObj.get("results");
			
			int randomPic = (int) (0 + (Math.random() * ((images.size()-1) - 0)));
			
			JSONArray tagsData = mlConnection.authConnectApi((String) images.get(randomPic));
			ImageComponent.allImageData.add(classifications.toClassification(tagsData, (String) images.get(randomPic), Images.query));
			
			updateUI();
		}
		
	}
	
	public void refresh() {
		Images.imageLinks.clear();
		ImageComponent.allImageData.clear();
		Images.currImage = 0;
	}
	
	public void updateUI() {
		ImageComponent.currentComponent = ImageComponent.allImageData.get(Images.currImage);
		Image image = new Image(ImageComponent.currentComponent.imageLink);
		foundImage.setImage(image);
		
		//Hard Coding Because JavaFX sux
		tag1.setText(ImageComponent.currentComponent.imageClassifications.get(0).tag);
		tag2.setText(ImageComponent.currentComponent.imageClassifications.get(1).tag);
		tag3.setText(ImageComponent.currentComponent.imageClassifications.get(2).tag);
		tag4.setText(ImageComponent.currentComponent.imageClassifications.get(3).tag);
		tag5.setText(ImageComponent.currentComponent.imageClassifications.get(4).tag);
		tag6.setText(ImageComponent.currentComponent.imageClassifications.get(5).tag);

		
		lvl1.setText(String.valueOf(ImageComponent.currentComponent.imageClassifications.get(0).confidenceLvl));
		lvl2.setText(String.valueOf(ImageComponent.currentComponent.imageClassifications.get(1).confidenceLvl));
		lvl3.setText(String.valueOf(ImageComponent.currentComponent.imageClassifications.get(2).confidenceLvl));
		lvl4.setText(String.valueOf(ImageComponent.currentComponent.imageClassifications.get(3).confidenceLvl));
		lvl5.setText(String.valueOf(ImageComponent.currentComponent.imageClassifications.get(4).confidenceLvl));
		lvl6.setText(String.valueOf(ImageComponent.currentComponent.imageClassifications.get(5).confidenceLvl));

	}
	
	@FXML
    void homeBtn(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../application/Main.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.hide();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@FXML
    void nextBtn(ActionEvent event) {
		if(Images.currImage < 2) {
			Images.currImage++;
			updateUI();
		}
    }

    @FXML
    void prevBtn(ActionEvent event) {
    	if(Images.currImage != 0) {
    		Images.currImage--;
			updateUI();
    	}
    }
	
}
