/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.model;

/**
 *
 * @author Dean
 */
public class Address
{
    public String ZIPCode;
    public String City;
    public String StreetName;

    public String getZIPCode() {
        return ZIPCode;
    }

    public String getCity() {
        return City;
    }

    public Address(String HomeAddress) {
        this.ZIPCode = HomeAddress.split(":")[0];
        this.City = HomeAddress.split(":")[1];
        this.StreetName = HomeAddress.split(":")[2];
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setZIPCode(String ZIPCode) {
        this.ZIPCode = ZIPCode;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }
    
    public void setAddress(String HomeAddress) {
        this.ZIPCode = HomeAddress.split(":")[0];
        this.City = HomeAddress.split(":")[1];
        this.StreetName = HomeAddress.split(":")[2];
    }

    @Override
    public String toString() {
        return ZIPCode + ", " + City + " " + StreetName;
    }
    
    
}
