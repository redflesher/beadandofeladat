package hu.unideb.inf;

import hu.unideb.inf.model.Model;
import hu.unideb.inf.view.FXMLLakossagSceneController;
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception { 
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLLakossagScene.fxml"));
        
        Scene scene = new Scene(loader.load());
        stage.setTitle("Lakosság & Állat Nyilvántartás");
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        ((FXMLLakossagSceneController)loader.getController()).setModel(new Model());
        
        stage.show();
    }    
 

    public static void main(String[] args) {
        launch(args);
    }

}
