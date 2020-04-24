package hu.unideb.inf.view;

import hu.unideb.inf.hibernate.HibernateUtil;
import hu.unideb.inf.model.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
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
    private ChoiceBox<String> genderChoiceBox;
    
    ObservableList<String> genderList = FXCollections.observableArrayList("MALE", "FEMALE");

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
    
    //van-e benne szam
    boolean isSzam(TextField textField){
        for (char c : textField.getText().toCharArray()) {
            if (Character.isDigit(c)) 
                return true;
        }
        return false;
    }
    
    //helyes datum van megadva
    boolean isDatum(TextField textField) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = format.parse(textField.getText());
            return textField.getText().length() != 10;
        } catch (ParseException ex) {
            return true;
        }
        
    }

    //van-e benne betu
    boolean isBetu(TextField textField){
        for (char c : textField.getText().toCharArray()) {
            if (Character.isLetter(c)) 
                return true;
        }
        return false;
    }
    
    //van-e benne specialis karakter
    boolean isSpecialis(TextField textField){
        for (char c : textField.getText().toCharArray()) {
            if (!Character.isSpaceChar(c)) 
                if (!Character.isLetterOrDigit(c)) 
                    return true;
        }
        return false;
    }
    
    boolean hibasBemenet_ember(Alert alert){
        alert.setTitle("Error!");
        alert.setHeaderText("A adatbázisba való feltöltés nem történt meg!");
        if (nameTextfield.getText().isBlank() || placeOfBirthTextfiled.getText().isBlank() || dateOfBirthTextfield.getText().isBlank() || genderTextfield.getText().isBlank() || socialSecurityNumberTextfiled.getText().isBlank() || homeAddressTextfield.getText().isBlank() || phoneTextfiled.getText().isBlank()) {
            alert.setContentText("Kérem töltse ki az üres mezőket!");
            return false;
        }else if (isSpecialis(nameTextfield) || isSpecialis(placeOfBirthTextfiled) /*|| isSpecialis(dateOfBirthTextfield) */|| isSpecialis(genderTextfield) || isSpecialis(socialSecurityNumberTextfiled) || isSpecialis(homeAddressTextfield) || isSpecialis(phoneTextfiled)) {
            alert.setContentText("A bemenetek között ne szerepeltessen speciális karaktereket!");
            return false;
        }else if (isSzam(nameTextfield) ||isSzam(placeOfBirthTextfiled) || isSzam(genderTextfield)) {
            alert.setContentText("A név, a nem és a születési hely nem tartalmazhat számokat!");
            return false;
        }else if (isBetu(dateOfBirthTextfield) || isBetu(socialSecurityNumberTextfiled) || isBetu(phoneTextfiled)) {
            alert.setContentText("A születési dátum, a TB szám és a telefonszám nem tartalmazhat betűket!");
            return false;
        }else if (isDatum(dateOfBirthTextfield)) {
            alert.setContentText("Kérem YYYY-MM-DD formátumban adja meg a születési dátumat!");
            return false;
        }
        return true;
    }
    
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
    
    void SetAndUploadModelAnimal() throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                if (rs.getString(i).equals(ownerIDTextfield.getText())){
                    //System.out.println(rs.getString(i+1));
                    model.getEmber().setId(Integer.parseInt(rs.getString(i-4)));
                    model.getEmber().setDateOfBirth(rs.getString(i-3));
                    model.getEmber().setGender(rs.getString(i-2));
                    model.getEmber().setHomeAddress(rs.getString(i-1));
                    model.getEmber().setName(rs.getString(i));
                    model.getEmber().setPhoneNumber(rs.getString(i+1));
                    model.getEmber().setPlaceOfBirth(rs.getString(i+2));
                    model.getEmber().setTbNumber(rs.getString(i+3));
                    x = 1;
                }
            }
        }        
        model.getAnimal().setDateOfBirth(dateOfBirthTextfiled_animal.getText());
        model.getAnimal().setGender(genderTextfield_animal.getText());
        model.getAnimal().setSpecies(speciesTextfield.getText());
        model.getAnimal().setName(nameTextfield_animal.getText());
        model.getAnimal().setOwnerID(model.getEmber());
        
        if (x == 1) {
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
            x = 0;
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
        //System.out.println(isDatum(socialSecurityNumberTextfiled));
    }

    @FXML
    void handleUploadButtonPushed() throws IOException, ClassNotFoundException{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (hibasBemenet_ember(alert)) {
            SetAndUploadModel();
            alert.setTitle("Sikeres feltöltés!");
            alert.setHeaderText("A feltöltés megtörtént!");
            alert.setContentText(nameTextfield.getText() + " mostantól szerepel az adatbázisban!");
        }
        alert.showAndWait();
    }

    @FXML
    void animal_handleCancelButtonPushed() throws IOException, SQLException{
        nameTextfield_animal.setText("");
        dateOfBirthTextfiled_animal.setText("");
        ownerIDTextfield.setText("");
        genderTextfield_animal.setText("");
        speciesTextfield.setText("");/*
        
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        String string = speciesTextfield.getText();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                if (rs.getString(i).equals(string)){
                    //System.out.println(rs.getString(i+1));
                    model.getEmber().setId(Integer.parseInt(rs.getString(i-4)));
                    model.getEmber().setDateOfBirth(rs.getString(i-3));
                    model.getEmber().setGender(rs.getString(i-2));
                    model.getEmber().setHomeAddress(rs.getString(i-1));
                    model.getEmber().setName(rs.getString(i));
                    model.getEmber().setPhoneNumber(rs.getString(i+1));
                    model.getEmber().setPlaceOfBirth(rs.getString(i+2));
                    model.getEmber().setTbNumber(rs.getString(i+3));
                    x++;
                }
                /*if(i > 1) 
                    System.out.print(", ");
                
                String columnValue = rs.getString(i);
                System.out.print(columnValue);
            }
        }
        //System.out.println("");
            if (x == 0)
                System.out.println("nincs ilyen");
            else{
                System.out.println("van ilyen");
                System.out.println(model.getEmber());
            }*/

    }

    @FXML
    void animal_handleUploadButtonPushed() throws SQLException{
        SetAndUploadModelAnimal();
    }
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderChoiceBox.setValue("MALE");
        genderChoiceBox.setItems(genderList);
    }

}
