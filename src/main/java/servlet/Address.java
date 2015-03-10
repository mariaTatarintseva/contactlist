package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Address {
    private final static Logger logger= LogManager.getLogger(Address.class);
    private String country;
    private String town;
    private String street;
    private Integer house;
    private Integer place;
    private Integer postIndex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;

    public Address(String country, String town, String street, Integer house, Integer place, Integer postIndex) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.house = house;
        this.place = place;
        this.postIndex = postIndex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(Integer postIndex) {
        this.postIndex = postIndex;
    }
}
