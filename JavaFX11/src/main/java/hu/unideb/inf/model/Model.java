package hu.unideb.inf.model;

public class Model {

    private Ember ember;
    private Animal animal;

    public Animal getAnimal() {
        return animal;
    }
    
    public Ember getEmber() {
        return ember;
    }

    public Model() {
        //ember = new Ember("Torda Péter 2", "Budapest", "1997/12/25", "MALE", "111222333", "Piros rózsa utca 25.", "+36306493660");
        ember = new Ember(null, null, null, null, null, null, null);
        animal =  new Animal("male", "Oszkár", "2019/02/02", "dog");
    }    
}
