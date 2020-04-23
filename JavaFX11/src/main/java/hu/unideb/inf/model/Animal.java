package hu.unideb.inf.model;

import java.io.*;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "animal")
public class Animal implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private int id;
    
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Ember ownerID = null;
    
    @Column(name = "Gender")
    private String gender;
    
    @Column(name = "Name")
    private String name;
    
    @Column(name = "DateOfBirth")
    private String dateOfBirth;
    
    @Column(name = "Species")
    private String species;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ember getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Ember ownerID) {
        this.ownerID = ownerID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Animal(String gender, String name, String dateOfBirth, String species) {
        this.gender = gender;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.species = species;
    }
    
    private void readObject(ObjectInputStream str) throws IOException, ClassNotFoundException{
        this.dateOfBirth = str.readUTF();
        this.gender = str.readUTF();
        this.name = str.readUTF();
        this.species = str.readUTF();
    }
    
    private void writeObject(ObjectOutputStream str) throws IOException, ClassNotFoundException{
        str.writeUTF(dateOfBirth);
        str.writeUTF(gender);
        str.writeUTF(name);
        str.writeUTF(species);
    }
}
