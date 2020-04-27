package hu.unideb.inf.view;

import hu.unideb.inf.hibernate.HibernateUtil;
import hu.unideb.inf.model.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    //SEARCH
    @FXML
    private TextField searchnameTextfield;

    @FXML
    private TextField searchplaceOfBirthTextfiled;

    @FXML
    private TextField searchdateOfBirthTextfield;

    @FXML
    private TextField searchgenderTextfield;

    @FXML
    private TextField searchsocialSecurityNumberTextfiled;

    @FXML
    private TextField searchhomeAddressTextfield;

    @FXML
    private TextField searchphoneTextfiled;

    @FXML
    private TextField nameTextfield_search;

    @FXML
    private TextField nameTextfield_animal_search;

    @FXML
    private TextField searchnameTextfield_animal;

    @FXML
    private TextField searchdateOfBirthTextfiled_animal;

    @FXML
    private TextField searchownerIDTextfield_animal;

    @FXML
    private TextField searchgenderTextfield_animal;

    @FXML
    private TextField searchspeciesTextfield_animal;
    //SEARCH
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
    }

    @FXML
    void handleUploadButtonPushed() throws IOException, ClassNotFoundException{
        SetAndUploadModel();
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
    //SEARCH
     @FXML
    void handleCancelSearchButtonPushed() {
        searchdateOfBirthTextfield.setText("");
        searchplaceOfBirthTextfiled.setText("");
        searchnameTextfield.setText("");
        searchgenderTextfield.setText("");
        searchhomeAddressTextfield.setText("");
        searchphoneTextfiled.setText("");
        searchsocialSecurityNumberTextfiled.setText("");
    }

    @FXML
    void handleCancelSearchButtonPushed_animal() {
        searchnameTextfield_animal.setText("");
        searchdateOfBirthTextfiled_animal.setText("");
        searchownerIDTextfield_animal.setText("");
        searchgenderTextfield_animal.setText("");
        searchspeciesTextfield_animal.setText("");
    }

    @FXML
    void handleSearchButtonPushed() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        String string = nameTextfield_search.getText();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        boolean van = false;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                if (rs.getString(i).equals(string)){
                    searchnameTextfield.setText(rs.getString(i));
                    searchplaceOfBirthTextfiled.setText(rs.getString(i+2));
                    searchdateOfBirthTextfield.setText(rs.getString(i-3));
                    searchgenderTextfield.setText(rs.getString(i-2));
                    searchphoneTextfiled.setText(rs.getString(i+1));
                    searchhomeAddressTextfield.setText(rs.getString(i-1));
                    searchsocialSecurityNumberTextfiled.setText(rs.getString(i+3));
                    van = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search");
                    alert.setHeaderText("");
                    alert.setContentText("People found!");
                    alert.showAndWait();
                }
            }
        }
        if(van == false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText("");
            alert.setContentText("People not found!");
            alert.showAndWait();
        }
    }

    @FXML
    void handleSearchButtonPushed_animal() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        String string = nameTextfield_search.getText();
        ResultSet  rs = st.executeQuery("SELECT * from animals");
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        boolean van = false;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                if (rs.getString(i).equals(string)){
                    searchnameTextfield_animal.setText(rs.getString(i));
                    searchgenderTextfield_animal.setText(rs.getString(i+1));
                    searchdateOfBirthTextfiled_animal.setText(rs.getString(i+2));
                    searchspeciesTextfield_animal.setText(rs.getString(i-1));
                    searchownerIDTextfield_animal.setText(rs.getString(i-2));
                    van = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search");
                    alert.setHeaderText("");
                    alert.setContentText("Pet found!");
                    alert.showAndWait();
                }
            }
        }
        if(van == false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText("");
            alert.setContentText("People not found!");
            alert.showAndWait();
        }
    }
    //SEARCH
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
