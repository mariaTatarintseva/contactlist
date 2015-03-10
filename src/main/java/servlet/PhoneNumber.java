package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhoneNumber {
    private final static Logger logger= LogManager.getLogger(PhoneNumber.class);

    public PhoneNumber(String number, String phoneType, String comment, String country, String operator) {
        this.number = number;
        this.phoneType = phoneType;
        this.comment = comment;
        this.country = country;
        this.operator = operator;
    }

    //public enum PhoneType {HOME, CELL};
    private Integer id;
    private String number;
    private String phoneType;
    private String comment;
    private String country;
    private String operator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String toString() {
        return String.format("%s(%s)%s", country, operator, number);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
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
