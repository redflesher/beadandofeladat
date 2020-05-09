package hu.unideb.inf.model;

import javafx.beans.property.SimpleStringProperty;


public class Emberek {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty home_address;
    private final SimpleStringProperty place_of_birth;
    private final SimpleStringProperty date_of_birth;
    private final SimpleStringProperty phone_number;
    private final SimpleStringProperty tb_number;
    private final SimpleStringProperty gender;

    public Emberek(String id,String name,String home_address,String place_of_birth,String date_of_birth,String phone_number,String tb_number,String gender) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.home_address = new SimpleStringProperty(home_address);
        this.place_of_birth = new SimpleStringProperty(place_of_birth);
        this.date_of_birth = new SimpleStringProperty(date_of_birth);
        this.phone_number = new SimpleStringProperty(phone_number);
        this.tb_number = new SimpleStringProperty(tb_number);
        this.gender = new SimpleStringProperty(gender);
    }

    public String getid(){
        return id.get();
    }
    public void setid(String id){
        this.id.set(id);
    }
    
    public String getname(){
        return name.get();
    }
    public void setname(String name){
        this.name.set(name);
    }
    
    public String gethome_address(){
        return home_address.get();
    }
    public void sethome_address(String home_address){
        this.home_address.set(home_address);
    }
    
    public String getplace_of_birth(){
        return place_of_birth.get();
    }
    public void setplace_of_birth(String place_of_birth){
        this.place_of_birth.set(place_of_birth);
    }
    
    public String getdate_of_birth(){
        return date_of_birth.get();
    }
    public void setdate_of_birth(String date_of_birth){
        this.date_of_birth.set(date_of_birth);
    }
    
    public String getphone_number(){
        return phone_number.get();
    }
    public void setphone_number(String phone_number){
        this.phone_number.set(phone_number);
    }
    
    public String gettb_number(){
        return tb_number.get();
    }
    public void settb_number(String tb_number){
        this.tb_number.set(tb_number);
    }
    
    public String getgender(){
        return gender.get();
    }
    public void setgender(String gender){
        this.gender.set(gender);
    }
    
    
    
    

    
    
    
}
