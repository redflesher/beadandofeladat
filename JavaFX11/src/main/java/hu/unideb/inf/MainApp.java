package hu.unideb.inf;

import hu.unideb.inf.model.Ember;
import hu.unideb.inf.model.Model;
import hu.unideb.inf.view.FXMLLakossagSceneController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApp extends Application {
    
    @Override
    public void start(Stage stage)
    {
            try
            {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLLakossagScene.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setTitle("Lakosság Nyilvántartás");
                stage.setScene(scene);
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("FXMLLakossagScene");
                EntityManager em = emf.createEntityManager();
                //FXMLLakossagSceneController LakossagScene = new FXMLLakossagSceneController(em);
 
                em.getTransaction().begin();
               // Ember ember = LakossagScene.SetAndUploadModel("Torda Péter", "Budapest", "1997/12/25", "MALE", "111222333", "4000:Debrecen:Piros rózsa utca 25.", "+36306493660");
                em.getTransaction().commit();
                //System.out.println("Persisted " + ember);
                ((FXMLLakossagSceneController)loader.getController()).setModel(new Model());

                stage.show();
              }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }    
 

    public static void main(String[] args) {
        launch(args);
    }

}
