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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FXMLLakossagSceneController implements Initializable {
   public EntityManager em;
    
    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }
    void FXMLLakossagSceneControl(EntityManager em){
        this.em=em;
    }
    
    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField placeOfBirthTextfield;

    @FXML
    private TextField dateOfBirthTextfield;

    @FXML
    private TextField genderTextfield;

    @FXML
    private TextField socialSecurityNumberTextfield;

    @FXML
    private TextField homeAddressTextfield;

    @FXML
    private TextField phoneTextfield;
    
    @FXML
    private TextField nameTextfield1;

    @FXML
    private TextField placeOfBirthTextfield1;

    @FXML
    private TextField dateOfBirthTextfield1;

    @FXML
    private TextField genderTextfield1;

    @FXML
    private TextField socialSecurityNumberTextfield1;

    @FXML
    private TextField homeAddressTextfield1;

    @FXML
    private TextField phoneTextfiled1;
    
    @FXML
    private TextField idSearcher;
    
    public Ember SetAndUploadModel(String DateOfBirth,String Gender ,String HomeAddress,String Name, String PhoneNumber,String PlaceOfBirth, String TbNumber){
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("ember");
        EntityManager em = emf.createEntityManager();
        Ember ember = new Ember();
        ember.setDateOfBirth(DateOfBirth);
        ember.setGender(Gender);
        ember.setHomeAddress(HomeAddress);
        ember.setName(Name);
        ember.setPhoneNumber(PhoneNumber);
        ember.setPlaceOfBirth(PlaceOfBirth);
        ember.setTbNumber(TbNumber);
        em.getTransaction().begin();
        em.persist(ember);
        em.getTransaction().commit();
        em.close();
        emf.close();
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
          return ember;
}

    @FXML
    void handleCancelButtonPushed() throws IOException{
        dateOfBirthTextfield.setText("");
        placeOfBirthTextfield.setText("");
        nameTextfield.setText("");
        genderTextfield.setText("");
        homeAddressTextfield.setText("");
        phoneTextfield.setText("");
        socialSecurityNumberTextfield.setText("");
    }

    @FXML
    void handleUploadButtonPushed() throws IOException, ClassNotFoundException{
        SetAndUploadModel(dateOfBirthTextfield.getText(),genderTextfield.getText(),homeAddressTextfield.getText(),nameTextfield.getText(),phoneTextfield.getText(),placeOfBirthTextfield.getText(),socialSecurityNumberTextfield.getText());
    }    
    @FXML
    void handleSearchButtonPushed() throws IOException, ClassNotFoundException{
    SearchbyID(Integer.parseInt(idSearcher.getText()));
    }    
    @FXML 
    void handleRemoveButtonPushed() throws IOException, ClassNotFoundException{
    RemoveEmber(Integer.parseInt(idSearcher.getText()));
    }    
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    void SearchbyID(int id) {
        Ember aktember = findEmber(id);
        dateOfBirthTextfield1.setText(aktember.getDateOfBirth());
        placeOfBirthTextfield1.setText(aktember.getPlaceOfBirth());
        nameTextfield1.setText(aktember.getName());
        genderTextfield1.setText(aktember.getGender());
        homeAddressTextfield1.setText(aktember.getHomeAddress());
        phoneTextfiled1.setText(aktember.getPhoneNumber());
        socialSecurityNumberTextfield1.setText(aktember.getTbNumber());
    }

    void RemoveEmber(int id) {
        Ember ember = findEmber(id);
        if(ember!= null){
            em.remove(ember);
        }
    }

    private Ember findEmber(int id) {
        return model.getEmber();
    }

}
