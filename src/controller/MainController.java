package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Images;

//@class -> MainController purpose is to take in our user input, place it into our model so we can use it later
public class MainController implements Initializable {
	@FXML
    private TextField searchInput;

    @SuppressWarnings("static-access")
	@FXML
    void searchBtn(ActionEvent event) {
    	Images.query = searchInput.getText();
    	
    	String[] tempString;
    	
    	tempString = Images.query.split(" ");
    	Images.query = String.join("%20", tempString);
    	
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../application/ImageView.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	
}
