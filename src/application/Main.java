//Allef Soares
//all346
package application;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class Main extends Application { 
    public static void main(String[] args) { 
        launch(args); 
    } 
     
    /*
     *@class -> starts and loads our fxml onto the stage as a scene. 
     *Starts the application 
     */
    @Override 
    public void start(Stage primaryStage) throws IOException { 
    	try {
    		primaryStage.setTitle("Image Detection"); 
    		Parent root = FXMLLoader.load(getClass().getResource("./Main.fxml"));
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    } 
}