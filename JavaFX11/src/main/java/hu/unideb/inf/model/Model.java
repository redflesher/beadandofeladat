package hu.unideb.inf.model;

public class Model {

    private Ember ember;

    public Ember getEmber() {
        return ember;
    }

    public Model() {
        ember = new Ember("Torda Péter", "Budapest", "1997/12/25", "MALE", "111222333", "Piros rózsa utca 25.", "+36306493660");
    }    
}
