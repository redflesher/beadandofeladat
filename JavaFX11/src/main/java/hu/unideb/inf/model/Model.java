package hu.unideb.inf.model;

@javax.persistence.Entity
public class Model {
    private Ember ember;

    public Ember getEmber() {
        return ember;
    }

    public void setEmber(String name, String placeOfBirth, String dateOfBirth, Gender gender, String tbNumber, String homeAddress, String phoneNumber) {
            this.ember = new Ember(name, placeOfBirth, dateOfBirth, gender, tbNumber, homeAddress, phoneNumber);
    }
    
    public Model() {
        ember = new Ember("Torda Péter", "Budapest", "1997/12/25", Gender.MALE, "111222333", "4000:Debrecen:Piros rózsa utca 25.", "+36306493660");
    }    
}
