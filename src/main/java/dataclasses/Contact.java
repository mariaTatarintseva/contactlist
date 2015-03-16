package dataclasses;

import com.mysql.jdbc.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable, Comparable<Contact>{
    private final static Logger logger= LogManager.getLogger(Contact.class);
    public Contact() {
        this.phoneNumbers = new ArrayList<PhoneNumber>();
        this.attachments = new ArrayList<Attachment>();
    }
	private static final long serialVersionUID = -5962530188216852276L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getParentName() {
        if (parentName == null) {
            return "";
        }
        return parentName;
    }

    public void setParentName(String parentName) {

        this.parentName = parentName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        if (email == null) {
            return "";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int compareTo(Contact contact) {
        return getFullName().compareTo(contact.getFullName());
    }

    public enum FamilyStatus {NOT_SPECIFIED, DIVORCED, DATING, MARRIED, SINGLE, WIDOW};
	private Integer id;
	private String name;
    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return String.format("%s %s %s", surname, name, (StringUtils.isNullOrEmpty(parentName) ? "" : parentName));
    }
    public String getAddressString() {
        if (address == null) {
            return "";
        }
        return String.format("%s %s %s %s %s %s", StringUtils.isNullOrEmpty(address.getStreet())? "" : address.getStreet(),  address.getHouse() == null ? "" : String.valueOf(address.getHouse()) ,
                address.getPlace() == null ? "" : String.valueOf(address.getPlace())  , address.getPostIndex() == null ? "" : String.valueOf(address.getPostIndex())  ,  StringUtils.isNullOrEmpty(address.getTown())? "" : address.getTown(), StringUtils.isNullOrEmpty(address.getCountry())? "" : address.getCountry());

    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String surname;
	private Integer age;
	private String parentName;
	private LocalDate birthday;
	public enum Gender {MALE, FEMALE};
	private Gender gender;
	private String citizenship;
	private String webSite;
	private String email;
	private String job;
	private Address address;

    public FamilyStatus getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(FamilyStatus familyStatus) {
        this.familyStatus = familyStatus;
    }

    private FamilyStatus familyStatus;


    public ArrayList<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    private ArrayList<PhoneNumber> phoneNumbers;

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    private ArrayList<Attachment> attachments;
	private String photo;
}
