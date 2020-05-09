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
        //ember = new Ember("Sprint Elek", "Budapest", "1805/10/05", "MALE", "111222333", "valamilyen utca 401.", "+36457489225");
        ember = new Ember(null, null, null, null, null, null, null);
        animal =  new Animal("MALE", "Oszkár", "2019/02/02", "dog");
    }    
}
