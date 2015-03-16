package dataclasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhoneNumber {
    private final static Logger logger= LogManager.getLogger(PhoneNumber.class);

    public PhoneNumber(Integer number, PhoneType phoneType, String comment, Integer country, Integer operator) {
        this.number = number;
        this.phoneType = phoneType;
        this.comment = comment;
        this.country = country;
        this.operator = operator;
    }

    public enum PhoneType {HOME, CELL};
    private Integer id;
    private Integer number;
    private PhoneType phoneType;
    private String comment;
    private Integer country;
    private Integer operator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String toString() {
        String c = (country == null? "" : String.valueOf(country));
        String o = (operator == null? "" : String.valueOf(operator));
        String n = (number == null? "" : String.valueOf(number));
        return String.format("%s\t%s\t%s", c, o, n);
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public PhoneNumber() {
    }
}
