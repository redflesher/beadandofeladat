package hu.unideb.inf.view;

import hu.unideb.inf.hibernate.HibernateUtil;
import hu.unideb.inf.model.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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

    ObservableList<String> GenderList = FXCollections.observableArrayList("MALE","FEMALE", "-Kérem válasszon ...");
     
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    
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
    private ChoiceBox<String> genderChoiceBox_animal;    
    
    @FXML
    private TextField speciesTextfield;

    
    @FXML
    private TextField search_nameTextfield;
    
     @FXML
    private TextField search_animalnameTextfield;
     
       @FXML
    private TextField search_allnameTextfield;
       
       @FXML
    private TextField remove_nameTextfield;

    @FXML
    private TextField remove_animalnameTextfield;
    
    //ember ablak kiüritése
    private void ujEmberAblak(){
        dateOfBirthTextfield.setText("");
        placeOfBirthTextfiled.setText("");
        nameTextfield.setText("");
        homeAddressTextfield.setText("");
        phoneTextfiled.setText("");
        socialSecurityNumberTextfiled.setText("");
        genderChoiceBox.setValue("-Kérem válasszon ...");
    }
    
    //allat ablak kiüritése
    private void ujAllatAblak(){
        nameTextfield_animal.setText("");
        dateOfBirthTextfiled_animal.setText("");
        ownerIDTextfield.setText("");
        genderChoiceBox_animal.setValue("-Kérem válasszon ...");
        speciesTextfield.setText("");        
    }
    
    //van-e benne szam
    private boolean isSzam(TextField textField){
        for (char c : textField.getText().toCharArray()) {
            if (Character.isDigit(c)) 
                return true;
        }
        return false;
    }
    
    //helyes datum van megadva
    private boolean isDatum(TextField textField) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = format.parse(textField.getText());
            return textField.getText().length() != 10;
        } catch (ParseException ex) {
            return true;
        }        
    }
    
    //telefonszam-e
    private boolean  isPhone(TextField textField){
        String str = textField.getText();
        if (str.length() != 12) 
            return true;
        else return str.charAt(0) != '+';
    }
    
    //TAJ szám-e
    private  boolean isTaj(TextField textField){
        String str = textField.getText();
        if (str.length() != 11) 
            return true;
        else if (str.charAt(3) == '-' && str.charAt(7) == '-')
            return false;
                
        return true;
    }

    //van-e benne betu
    private boolean isBetu(TextField textField){
        for (char c : textField.getText().toCharArray()) {
            if (Character.isLetter(c)) 
                return true;
        }
        return false;
    }
    
    //van-e benne specialis karakter
    private boolean isSpecialis(TextField textField){
        for (char c : textField.getText().toCharArray()) {
            if (!Character.isSpaceChar(c)) 
                if (!Character.isLetterOrDigit(c)) 
                    return true;
        }
        return false;
    }
    
    //hibas bemenetek hibauzenetei az emberek feltöltéséhez
    private boolean hibasBemenet_ember(Alert alert)throws SQLException{
        alert.setTitle("Error!");
        alert.setHeaderText("A adatbázisba való feltöltés nem történt meg!");
        
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= rs.getMetaData().getColumnCount();i++){
                if (!nameTextfield.getText().isBlank()) 
                    if (rs.getString(i).equals(nameTextfield.getText()) && rs.getString(i-3).equals(dateOfBirthTextfield.getText()))
                        x++;
            }
        }
        
        if (nameTextfield.getText().isBlank() || placeOfBirthTextfiled.getText().isBlank() || dateOfBirthTextfield.getText().isBlank() || socialSecurityNumberTextfiled.getText().isBlank() || homeAddressTextfield.getText().isBlank() || phoneTextfiled.getText().isBlank()) {
            alert.setContentText("Kérem, töltse ki az üres mezőket!");
            return false;
        }else if (isSpecialis(nameTextfield) || isSpecialis(placeOfBirthTextfiled)|| isSpecialis(homeAddressTextfield)) {
            alert.setContentText("A bemenetek között ne szerepeltessen speciális karaktereket!");
            return false;
        }else if (isSzam(nameTextfield) ||isSzam(placeOfBirthTextfiled)) {
            alert.setContentText("A név és a születési hely nem tartalmazhat számokat!");
            return false;
        }else if (isBetu(dateOfBirthTextfield) || isBetu(socialSecurityNumberTextfiled) || isBetu(phoneTextfiled)) {
            alert.setContentText("A születési dátum, a TAJ szám és a telefonszám nem tartalmazhat betűket!");
            return false;
        }else if (x != 0) {
            alert.setContentText("Ilyen ember már szerepel az adatbázisban!");
            return false;
        }  else if (isDatum(dateOfBirthTextfield)) {
            alert.setContentText("Kérem, YYYY-MM-DD formátumban adja meg a születési dátumat!");
            return false;
        }else if (genderChoiceBox.getValue().equals("-Please, select from the list!-")) {
            alert.setContentText("Kérem, válasszon a listából!");
            return false;
        }else if (isTaj(socialSecurityNumberTextfiled)) {
            alert.setContentText("Kérem, XXX-XXX-XXX formátumban adja meg a TAJ számot!");
            return false;
        }else if (isPhone(phoneTextfiled)) {
            alert.setContentText("Kérem, '+'-val kezdje a telefonszámot és 11 szám kövesse azt!");
            return false;
        }
        
        return true;
    }
    
    //hibas bementek hibauzenetei az allatok feltöltéséhez
    private boolean hibasBemenet_animal(Alert alert) throws SQLException{
        alert.setTitle("Error!");
        alert.setHeaderText("A adatbázisba való feltöltés nem történt meg!");
        
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= rs.getMetaData().getColumnCount();i++)
                if (rs.getString(i).equals(ownerIDTextfield.getText()))
                    x++;
        }
       
        if (nameTextfield_animal.getText().isBlank() || dateOfBirthTextfiled_animal.getText().isBlank() || speciesTextfield.getText().isBlank() || ownerIDTextfield.getText().isBlank()) {
            alert.setContentText("Kérem, töltse ki az üres mezőket!");
            return false;
        }else if (isSpecialis(nameTextfield_animal) || isSpecialis(speciesTextfield) || isSpecialis(ownerIDTextfield)) {
            alert.setContentText("A bemenetek között ne szerepeltessen speciális karaktereket!");
            return false;
        }else if (isSzam(nameTextfield_animal) ||isSzam(speciesTextfield)) {
            alert.setContentText("A név és a faj nem tartalmazhat számokat!");
            return false;
        }else if (isDatum(dateOfBirthTextfiled_animal)) {
            alert.setContentText("Kérem, YYYY-MM-DD formátumban adja meg a születési dátumat!");
            return false;
        }else if (x == 0) {
            alert.setContentText("Nincs ilyen nevű ember az adatbázisban!");
            return false;
        }else if (genderChoiceBox_animal.getValue().equals("-Please, select from the list!-")) {
            alert.setContentText("Kérem, válasszon a listából!");
            return false;
        }    
        
        return true;
    }
    
    //a model beállítása és egy ember feltöltése az adatbázisba
    private void SetAndUploadModel(){
        model.getEmber().setDateOfBirth(dateOfBirthTextfield.getText());
        model.getEmber().setGender(genderChoiceBox.getValue());
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
    
    //a model beállítása és egy állat feltöltése az adatbázisba
    private void SetAndUploadModelAnimal() throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                if (rs.getString(i).equals(ownerIDTextfield.getText())){
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
        model.getAnimal().setGender(genderChoiceBox_animal.getValue());
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
        ujEmberAblak();
    }

    @FXML
    void handleUploadButtonPushed() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (hibasBemenet_ember(alert)) {
            SetAndUploadModel();
            alert.setTitle("Sikeres feltöltés!");
            alert.setHeaderText("A feltöltés megtörtént!");
            alert.setContentText(nameTextfield.getText() + " mostantól szerepel az adatbázisban!");
            ujEmberAblak();
        }
        alert.showAndWait();
    }

    @FXML
    void animal_handleCancelButtonPushed() throws IOException{
        ujAllatAblak();
    }

    @FXML
    void animal_handleUploadButtonPushed() throws SQLException{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (hibasBemenet_animal(alert)) {
            SetAndUploadModelAnimal();
            alert.setTitle("Sikeres feltöltés!");
            alert.setHeaderText("A feltöltés megtörtént!");
            alert.setContentText(nameTextfield_animal.getText()+ " mostantól szerepel az adatbázisban!");
            ujAllatAblak();
        }
        alert.showAndWait();
    }
    
    private boolean searchResult(Alert alert) throws SQLException{
        alert.setTitle("Result");
        alert.setHeaderText("!!!!!!");
        
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                if (!search_nameTextfield.getText().isBlank())                    
                if (rs.getString(i).equals(search_nameTextfield.getText())){
                    model.getEmber().setId(Integer.parseInt(rs.getString(i-4)));
                    model.getEmber().setDateOfBirth(rs.getString(i-3));
                    model.getEmber().setGender(rs.getString(i-2));
                    model.getEmber().setHomeAddress(rs.getString(i-1));
                    model.getEmber().setName(rs.getString(i));
                    model.getEmber().setPhoneNumber(rs.getString(i+1));
                    model.getEmber().setPlaceOfBirth(rs.getString(i+2));
                    model.getEmber().setTbNumber(rs.getString(i+3));
                    x = 1;
                    return true;
                }
            }
        }
        return false;
    }
    /*
    @FXML
    void handleSearchButtonPushed() throws SQLException {
        
        String str = "";
        
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        ResultSet  rs = st.executeQuery("SELECT * from people");
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                if (!search_nameTextfield.getText().isBlank())                    
                if (rs.getString(i).equals(search_nameTextfield.getText())){
                    model.getEmber().setId(Integer.parseInt(rs.getString(i-4)));
                    model.getEmber().setDateOfBirth(rs.getString(i-3));
                    model.getEmber().setGender(rs.getString(i-2));
                    model.getEmber().setHomeAddress(rs.getString(i-1));
                    model.getEmber().setName(rs.getString(i));
                    model.getEmber().setPhoneNumber(rs.getString(i+1));
                    model.getEmber().setPlaceOfBirth(rs.getString(i+2));
                    model.getEmber().setTbNumber(rs.getString(i+3));
                    x = 1;
                    str += model.getEmber().toString() + "\n\n\n";
                }
                
               /* switch(){
                    
                }*//*
            }
        }
        

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (searchResult(alert)) {
            alert.setTitle("Result");
            alert.setHeaderText("Van ilyen ember az adatbázisban.");

            //String str = model.getEmber().toString();

            Label label = new Label("A keresés eredménye:");

            TextArea textArea = new TextArea(str);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane content = new GridPane();
            content.setMaxWidth(Double.MAX_VALUE);
            content.add(label, 0, 0);
            content.add(textArea, 0, 1);

            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        }else{
            alert.setTitle("Result");
            alert.setHeaderText("Nincs ilyen ember az adatbázisban.");
            alert.showAndWait();
        }
    }
*/
    @FXML
    void handleSearchButtonPushed() throws SQLException {
        String str = "";   
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        String sql = "SELECT" +
                " name as Név," +
                " home_address as Lakhely," +
                " date_of_birth as Születési_dátum," +
                " place_of_birth as Születési_hely," +
                " tb_number as Személyi_szám," +
                " gender as Nem" +
                " FROM" +
                " PEOPLE "+
                " WHERE name = '"+search_nameTextfield.getText()+"'";
        System.out.println(sql);
        ResultSet  rs = st.executeQuery(sql);
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                str += nevek.getColumnLabel(i)+" : "+ rs.getString(i)+"\n";
                x = 1;
            }
            str += "-------------------------\n";
        }
        if(x == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keresés eredménye");
                alert.setHeaderText("Van ilyen ember az adatbázisban.");
                Label label = new Label("A keresés eredménye:");
                TextArea textArea = new TextArea(str);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane content = new GridPane();
                content.setMaxWidth(Double.MAX_VALUE);
                content.add(label, 0, 0);
                content.add(textArea, 0, 1);

                alert.getDialogPane().setContent(content);
                alert.showAndWait();
        }
        if(x == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keresés eredménye");
                alert.setHeaderText("Nincs ilyen ember az adatbázisban.");
                alert.showAndWait();
        }
        
        
    }
    
     @FXML
    void handleSearchAnimalButtonPushed() throws SQLException {
        String str = "";   
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        String sql = "SELECT" +
                " a.name as Név," +
                " p.name as Tulajdonos," +
                " a.dateofbirth as Születési_dátum," +
                " a.gender as Nem," +
                " a.species as Faj," +
                " FROM" +
                " ANIMAL a INNER JOIN PEOPLE p on a.owner_id = p.id "+
                " WHERE a.name = '"+search_animalnameTextfield.getText()+"'";
        System.out.println(sql);
        ResultSet  rs = st.executeQuery(sql);
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        while(rs.next()){
            for(int i = 1; i <= columns;i++){
                str += nevek.getColumnLabel(i)+" : "+ rs.getString(i)+"\n";
                x = 1;
            }
            str += "-------------------------\n";
        }
        if(x == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keresés eredménye");
                alert.setHeaderText("Van ilyen állat az adatbázisban.");
                Label label = new Label("A keresés eredménye:");
                TextArea textArea = new TextArea(str);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane content = new GridPane();
                content.setMaxWidth(Double.MAX_VALUE);
                content.add(label, 0, 0);
                content.add(textArea, 0, 1);

                alert.getDialogPane().setContent(content);
                alert.showAndWait();
        }
        if(x == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keresés eredménye");
                alert.setHeaderText("Nincs ilyen állat az adatbázisban.");
                alert.showAndWait();
        }
    }
     @FXML
    void handleSearchAllButtonPushed() throws SQLException {
        
        String str = "";   
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st = conn.createStatement();
        String sql = "SELECT p.name as Tulajdonos,a.name as Állat_neve FROM"+
                " PEOPLE p INNER JOIN ANIMAL a on p.id = a.owner_id "+
                 " where p.name = '"+ search_allnameTextfield.getText()+"';";
        System.out.println(sql);
        ResultSet  rs = st.executeQuery(sql);
        ResultSetMetaData nevek = rs.getMetaData();
        int columns = nevek.getColumnCount();
        int x = 0;
        int num = 0;
        while(rs.next()){
            if(num == 0){
                for(int i = 1; i <= columns;i++){
                str += nevek.getColumnLabel(i)+" : "+ rs.getString(i)+"\n";
                x = 1;
                num = 1;
                }
            }
            else{
                for(int i = 2; i <= columns;i++){
                str += nevek.getColumnLabel(i)+" : "+ rs.getString(i)+"\n";
                x = 1;
                }
            }
            str += "-------------------------\n";
        }
        if(x == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keresés eredménye");
                alert.setHeaderText(search_allnameTextfield.getText()+" állatai");
                Label label = new Label("A keresés eredménye:");
                TextArea textArea = new TextArea(str);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane content = new GridPane();
                content.setMaxWidth(Double.MAX_VALUE);
                content.add(label, 0, 0);
                content.add(textArea, 0, 1);

                alert.getDialogPane().setContent(content);
                alert.showAndWait();
        }
        if(x == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keresés eredménye");
                alert.setHeaderText(search_allnameTextfield.getText()+" nem rendelkezik egy állattal sem.");
                alert.showAndWait();
        }
    }
    @FXML
    void handleRemoveAnimalButtonPushed() throws SQLException {
        String name = "";   
        Connection conn4 = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st4 = conn4.createStatement();
        String sql4 = "SELECT" +
                " name" +
                " FROM" +
                " ANIMAL "+
                " WHERE name = '"+remove_animalnameTextfield.getText()+"'";
        System.out.println(sql4);
        ResultSet  rs4 = st4.executeQuery(sql4);
        ResultSetMetaData nevek4 = rs4.getMetaData();
        int columns4 = nevek4.getColumnCount();
        int van = 0;
        while(rs4.next()){
            for(int i = 1; i <= columns4;i++){
                name += nevek4.getColumnLabel(i)+" : "+ rs4.getString(i)+"\n";
                van = 1;
            }
        }
        if(van == 1){
                int x = 0;
                if(!remove_animalnameTextfield.getText().equals("")){
                    String str = "";   
                    Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
                    Statement st = conn.createStatement();
                    String sql = "DELETE FROM ANIMAL WHERE NAME = '" + remove_animalnameTextfield.getText()+"';";
                    System.out.println(sql);
                    int  rs = st.executeUpdate(sql);
                    x = 1;
                }
                if(x == 1){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Törlés eredménye");
                        alert.setHeaderText(remove_animalnameTextfield.getText()+" sikeresen eltávolítva az adatbázisból.");
                        alert.showAndWait();
                }
        }
        if(van == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Törlés eredménye");
                alert.setHeaderText("Az eltávolítás sikertelen,mert nincs ilyen állat az adatbázisban.");
                alert.showAndWait();
        }
        
        
        
        
        

    }

    @FXML
    void handleRemoveButtonPushed() throws SQLException {
        String name = "";   
        Connection conn4 = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
        Statement st4 = conn4.createStatement();
        String sql4 = "SELECT" +
                " name" +
                " FROM" +
                " PEOPLE "+
                " WHERE name = '"+remove_nameTextfield.getText()+"'";
        System.out.println(sql4);
        ResultSet  rs4 = st4.executeQuery(sql4);
        ResultSetMetaData nevek4 = rs4.getMetaData();
        int columns4 = nevek4.getColumnCount();
        int van = 0;
        while(rs4.next()){
            for(int i = 1; i <= columns4;i++){
                name += nevek4.getColumnLabel(i)+" : "+ rs4.getString(i)+"\n";
                van = 1;
            }
        }
        if(van == 1){
                int x = 0;
                if(!remove_nameTextfield.getText().equals("")){
                    String str = "";   
                    Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
                    Statement st = conn.createStatement();
                    String sql = "SELECT id from people where name = '" +remove_nameTextfield.getText()+"'";
                    System.out.println(sql);
                    ResultSet  rs = st.executeQuery(sql);
                    ResultSetMetaData nevek = rs.getMetaData();
                    int columns = nevek.getColumnCount();
                    while(rs.next()){
                        for(int i = 1; i <= columns;i++){
                            str = rs.getString(i);
                        }
                    }
                    System.out.println("id:"+str);

                    Connection conn2 = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
                    Statement st2 = conn.createStatement();
                    String sql2 = "delete from animal where owner_id = " +str+";";
                    System.out.println(sql2);
                    int  rs2 = st.executeUpdate(sql2);

                    Connection conn3 = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
                    Statement st3 = conn3.createStatement();
                    String sql3 = "delete from people where name = '" +remove_nameTextfield.getText()+"';";
                    System.out.println(sql3);
                    int  rs3 = st.executeUpdate(sql3);
                    x = 1;
                }
                if(x == 1){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Törlés eredménye");
                        alert.setHeaderText(remove_nameTextfield.getText()+" sikeresen eltávolítva az adatbázisból.");
                        alert.showAndWait();
                }
        }
        if(van == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Törlés eredménye");
                alert.setHeaderText("Az eltávolítás sikertelen,mert nincs ilyen ember az adatbázisban.");
                alert.showAndWait();
        }

        
    }
        @FXML
        void handleSearchAllPeopleButtonPushed() throws SQLException {
            String str = "";   
            Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/./test_ember","sa","sa");
            Statement st = conn.createStatement();
            String sql = "SELECT id,name,home_address,place_of_birth,date_of_birth,phone_number,tb_number,gender"+
                        " FROM PEOPLE";
            System.out.println(sql);
            ResultSet  rs = st.executeQuery(sql);
            ResultSetMetaData nevek = rs.getMetaData();
            int columns = nevek.getColumnCount();
            int x = 0;
            while(rs.next()){
                    for(int i = 1; i <= columns;i++){
                    str += rs.getString(i) + "|";
                    x = 1;
                    }
                    str += "\n";
                
            }
            if(x == 1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Keresés eredménye");
                    alert.setHeaderText("Az adatbázisban található emberek listája");
                    Label label = new Label("A keresés eredménye:");
                    TextArea textArea = new TextArea(str);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);

                    textArea.setMaxWidth(Double.MAX_VALUE);
                    textArea.setMaxHeight(Double.MAX_VALUE);
                    GridPane.setVgrow(textArea, Priority.ALWAYS);
                    GridPane.setHgrow(textArea, Priority.ALWAYS);

                    GridPane content = new GridPane();
                    content.setMaxWidth(Double.MAX_VALUE);
                    content.add(label, 0, 0);
                    content.add(textArea, 0, 1);

                    alert.getDialogPane().setContent(content);
                    alert.setWidth(1000);
                    alert.showAndWait();
            }
            if(x == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Keresés eredménye");
                    alert.setHeaderText("Az adatbázis üres.");
                    alert.showAndWait();
            }
        }

    @FXML
    void handleRemoveCancelButtonPushed() {
        remove_animalnameTextfield.setText("");
        remove_nameTextfield.setText("");
    }

    @FXML
    void handleSearchCancelButtonPushed() {
        search_nameTextfield.setText("");
        search_animalnameTextfield.setText("");
        search_allnameTextfield.setText("");
    }
    
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb){
        genderChoiceBox.setValue("-Kérem válasszon ...");
        genderChoiceBox.setItems(GenderList);
        genderChoiceBox_animal.setValue("-Kérem válasszon ...");
        genderChoiceBox_animal.setItems(GenderList);
        
        
    }

}
