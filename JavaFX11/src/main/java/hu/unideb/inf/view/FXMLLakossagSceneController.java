package hu.unideb.inf.view;

import hu.unideb.inf.hibernate.HibernateUtil;
import hu.unideb.inf.model.Ember;
import hu.unideb.inf.model.Model;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FXMLLakossagSceneController implements Initializable {

    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField placeOfBirthTextfiled;

    @FXML
    private TextField dateOfBirthTextfield;

    @FXML
    private TextField genderTextfield;

    @FXML
    private TextField socialSecurityNumberTextfiled;

    @FXML
    private TextField homeAddressTextfield;

    @FXML
    private TextField phoneTextfiled;
    
    @FXML
    private TextField nameTextfield_animal;

    @FXML
    private TextField dateOfBirthTextfiled_animal;

    @FXML
    private TextField ownerIDTextfield;

    @FXML
    private TextField genderTextfield_animal;

    @FXML
    private TextField speciesTextfield;
    
    void SetAndUploadModel(){
        model.getEmber().setDateOfBirth(dateOfBirthTextfield.getText());
        model.getEmber().setGender(genderTextfield.getText());
        model.getEmber().setHomeAddress(homeAddressTextfield.getText());
        model.getEmber().setName(nameTextfield.getText());
        model.getEmber().setPhoneNumber(phoneTextfiled.getText());
        model.getEmber().setPlaceOfBirth(placeOfBirthTextfiled.getText());
        model.getEmber().setTbNumber(socialSecurityNumberTextfiled.getText());
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(model.getEmber());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    void SetAndUploadModelAnimal() {
        model.getAnimal().setDateOfBirth(dateOfBirthTextfiled_animal.getText());
        model.getAnimal().setGender(genderTextfield_animal.getText());
        model.getAnimal().setSpecies(speciesTextfield.getText());
        model.getAnimal().setName(nameTextfield_animal.getText());
        model.getAnimal().setOwnerID(Ember.class.cast(ownerIDTextfield.getText()));
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(model.getAnimal());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @FXML
    void handleCancelButtonPushed() throws IOException{
        dateOfBirthTextfield.setText("");
        placeOfBirthTextfiled.setText("");
        nameTextfield.setText("");
        genderTextfield.setText("");
        homeAddressTextfield.setText("");
        phoneTextfiled.setText("");
        socialSecurityNumberTextfiled.setText("");
    }

    @FXML
    void handleUploadButtonPushed() throws IOException, ClassNotFoundException{
        SetAndUploadModel();
    }

    @FXML
    void animal_handleCancelButtonPushed() {
        nameTextfield_animal.setText("");
        dateOfBirthTextfiled_animal.setText("");
        ownerIDTextfield.setText("");
        genderTextfield_animal.setText("");
        speciesTextfield.setText("");
    }

    @FXML
    void animal_handleUploadButtonPushed() {
        SetAndUploadModelAnimal();
    }
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
