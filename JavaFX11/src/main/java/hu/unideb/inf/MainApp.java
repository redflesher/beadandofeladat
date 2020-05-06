package hu.unideb.inf;

import hu.unideb.inf.model.Model;
import hu.unideb.inf.view.FXMLLakossagSceneController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLLakossagScene.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Lakosság & Állat Nyilvántartás");
        stage.setScene(scene);
        ((FXMLLakossagSceneController)loader.getController()).setModel(new Model());
        
        
        stage.show();
    }    
 

    public static void main(String[] args) {
        launch(args);
    }

}
