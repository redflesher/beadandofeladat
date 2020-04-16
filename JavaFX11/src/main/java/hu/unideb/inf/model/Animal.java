package hu.unideb.inf.model;

import java.io.*;
import javax.persistence.*;

@Entity
@Table(name = "animal")
public class Animal implements Serializable{
    
    
    private int id;
    
    private int ownerID;
    
    private String gender;
    
    private String name;
    
    private String dateOfBirth;
    
    private String species;
    
}
