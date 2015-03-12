package dao;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import dataclasses.Address;
import dataclasses.Attachment;
import dataclasses.Contact;
import dataclasses.PhoneNumber;

import java.sql.*;
import java.util.ArrayList;

public class DataAccessObject {
    private final static Logger logger= LogManager.getLogger(DataAccessObject.class);

    public static void setURL(String URL) {
        DataAccessObject.URL = URL;
    }

    public static void setPASSWORD(String PASSWORD) {
        DataAccessObject.PASSWORD = PASSWORD;
    }

    public static void setDRIVER(String DRIVER) {
        DataAccessObject.DRIVER = DRIVER;
    }

    static String URL;// = "jdbc:mysql://localhost:3306/contacts_tatarintseva";
    private static String USER;// = "username";

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        DataAccessObject.init = init;
    }

    private static boolean init = false;
    static String PASSWORD;// = "password";
    static String DRIVER;// = "com.mysql.jdbc.Driver";

    public static void setUSER(String USER) {
        DataAccessObject.USER = USER;
    }

   // private static String user;
    public static int saveToDatabase (Contact contact) throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        connection.setAutoCommit(false);
        PreparedStatement statement = null;
        try {
            Integer address = saveAdress(contact.getAddress(), connection);
            if(contact.getId() != null) {
                statement = connection.prepareStatement("UPDATE contact SET NAME=?, SURNAME=?, FATHERNAME=?,  JOB=?, EMAIL=?, BIRTHDAY=?, adress_id=?, GENDER=?, FAMILY=?, CITIZENSHIP=? WHERE ID =? ", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(11, contact.getId());
                statement.setString(10, contact.getCitizenship());
                statement.setString(9, contact.getFamilyStatus().name());
                if (contact.getGender() == Contact.Gender.FEMALE) {
                    statement.setString(8, "FEMALE");
                } else if (contact.getGender() == Contact.Gender.MALE) {
                    statement.setString(8, "MALE");
                } else {
                        statement.setNull(8, Types.VARCHAR);
                }
                statement.setInt(7, address);
                statement.setDate(6, contact.getBirthday() == null? null : java.sql.Date.valueOf(contact.getBirthday().toString()));
                statement.setString(1, contact.getName());
                statement.setString(2, contact.getSurname());
                if (StringUtils.isNotEmpty(contact.getParentName()))  {
                statement.setString(3, contact.getParentName());
                } else {
                    statement.setNull(3, Types.VARCHAR);
                }
                if (StringUtils.isNotEmpty(contact.getJob()))  {
                    statement.setString(4, contact.getJob());
                } else {
                    statement.setNull(4, Types.VARCHAR);
                }
                if (StringUtils.isNotEmpty(contact.getEmail()))  {
                    statement.setString(5, contact.getEmail());
                } else {
                    statement.setNull(5, Types.VARCHAR);
                }
                statement.execute();
                //statement = connection.prepareStatement("INSERT INTO adress (COUNTRY, TOWN, STREET, HOME, PLACE, INDEX) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            } else {
            statement = connection.prepareStatement("INSERT INTO contact (NAME, SURNAME, FATHERNAME,  JOB, EMAIL, BIRTHDAY, adress_id, GENDER, FAMILY, CITIZENSHIP) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getSurname());
                if (StringUtils.isNotEmpty(contact.getParentName()))  {
                    statement.setString(3, contact.getParentName());
                } else {
                    statement.setNull(3, Types.VARCHAR);
                }
                if (StringUtils.isNotEmpty(contact.getJob()))  {
                    statement.setString(4, contact.getJob());
                } else {
                    statement.setNull(4, Types.VARCHAR);
                }
                if (StringUtils.isNotEmpty(contact.getEmail()))  {
                    statement.setString(5, contact.getEmail());
                } else {
                    statement.setNull(5, Types.VARCHAR);
                }
            statement.setDate(6, contact.getBirthday() == null ? null : java.sql.Date.valueOf(contact.getBirthday().toString()));
            statement.setInt(7, address);
                if (contact.getGender() == Contact.Gender.FEMALE) {
                    statement.setString(8, "FEMALE");
                } else if (contact.getGender() == Contact.Gender.MALE) {
                    statement.setString(8, "MALE");
                } else {
                    statement.setNull(8, Types.VARCHAR);
                }
                if (StringUtils.isNotEmpty(contact.getCitizenship()))  {
                    statement.setString(10, contact.getJob());
                } else {
                    statement.setNull(10, Types.VARCHAR);
                }
                statement.setString(9, contact.getFamilyStatus().name());
            }
            statement.execute();
            connection.commit();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                   contact.setId(rs.getInt("GENERATED_KEY"));
                }


        } catch (SQLException e) {
            throw e;
        }   finally {

        closeConnection(connection, statement);
        }
        return contact.getId();
    }
    public static ArrayList<Integer> getPhonesOf (Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (id == null) {
            return res;
        }
        try {
            statement = connection.prepareStatement("SELECT ID FROM phone WHERE contact_id=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw e;
        }   finally {
            closeConnection(connection, statement);
        }
        return res;
    }
    public static ArrayList<Integer> getAttachmentsOf (Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (id == null) {
            return res;
        }
        try {
            statement = connection.prepareStatement("SELECT ID FROM attachment WHERE CONTACT_ID=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw e;
        }   finally {
            closeConnection(connection, statement);
        }
        return res;
    }
    public static ArrayList<String> getEmails(ArrayList<Integer> ids) throws SQLException, ClassNotFoundException {
        ArrayList<String> res = new ArrayList<String>();
        Connection connection = connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT EMAIL FROM contact WHERE ID= ?");
            for (int id: ids) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (StringUtils.isNotEmpty(rs.getString(1)))  {
                res.add(rs.getString(1));
                }
            }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, preparedStatement);
        }
            return res;
    }

    public static ArrayList<Contact> getFromDatabase (int pageNumber, int onPage) throws SQLException, ClassNotFoundException {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT  NAME, SURNAME, FATHERNAME, JOB, BIRTHDAY, EMAIL, ID , adress_id FROM contact ORDER BY name ASC LIMIT ?, ?");
            statement.setInt(1, pageNumber*onPage);
            statement.setInt(2, onPage);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setName(rs.getString(1));
                contact.setSurname(rs.getString(2));
                contact.setParentName(rs.getString(3));
                contact.setJob(rs.getString(4));
          if (rs.getDate(5) != null) {
              contact.setBirthday(LocalDate.fromDateFields(rs.getDate(5)));
          }
                contact.setEmail(rs.getString(6));
                contact.setId(rs.getInt(7));
                contact.setAddress(getAddressFromDatabase(rs.getInt(8)));
                contacts.add(contact);
            }

        } catch (SQLException e) {
            throw e;
        }    finally {
            closeConnection(connection, statement);
        }
        return contacts;

    }
    public static Contact getFromDatabase (int id) throws SQLException, ClassNotFoundException {
        Contact contact = new Contact();
        contact.setId(id);
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT NAME, SURNAME, FATHERNAME, JOB, BIRTHDAY, EMAIL, PHOTO, WEBSITE, CITIZENSHIP, GENDER, FAMILY, adress_id FROM contact WHERE ID =?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                contact.setName(rs.getString(1));
                contact.setSurname(rs.getString(2));
                contact.setParentName(rs.getString(3));
                contact.setJob(rs.getString(4));
                if (rs.getDate(5) != null) {
                    contact.setBirthday(LocalDate.fromDateFields(rs.getDate(5)));
                }
                contact.setEmail(rs.getString(6));
                contact.setPhoto(rs.getString(7));
                contact.setWebSite(rs.getString(8));
                contact.setCitizenship(rs.getString(9));
                String gender = rs.getString(10);
                if (!rs.wasNull()) {
                contact.setGender(Contact.Gender.valueOf(gender));
                }
                String family = rs.getString(11);
                if (!rs.wasNull()) {
                contact.setFamilyStatus(Contact.FamilyStatus.valueOf(family));
                }
                contact.setAddress(getAddressFromDatabase(rs.getInt(12)));
            }

        } catch (SQLException e) {
            throw e;
        }  finally {

        closeConnection(connection, statement);
        }
        return contact;

    }

    public static Address getAddressFromDatabase (int id) throws SQLException, ClassNotFoundException {
        Address address = new Address();
        if (id == 0) {
            return address;
        }
        address.setId(id);
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT country, town, street, house, place, post_index FROM adress WHERE id =?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                address.setCountry(rs.getString(1));
                address.setTown(rs.getString(2));
                address.setStreet(rs.getString(3));
                int house = rs.getInt(4);
                if (!rs.wasNull()) {
                    address.setHouse(house);
                }
                int place = rs.getInt(5);
                if (!rs.wasNull()) {
                    address.setPlace(place);
                }
                int postIndex = rs.getInt(6);
                if (!rs.wasNull()) {
                    address.setPostIndex(postIndex);
                }

            }

        } catch (SQLException e) {
            throw e;
        }  finally {

            closeConnection(connection, statement);
        }
        return address;

    }
    public static void deleteFromDatabase (ArrayList<Integer> ids) throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            String remove = StringUtils.join(ids, "', '");
            remove = StringUtils.substring(remove, 0, remove.length());
            statement = connection.prepareStatement(String.format("DELETE FROM contact WHERE ID IN %s", String.format("('%s')", remove)));
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        } finally {
        closeConnection(connection, statement);
        }
    }

    public static int total() throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        Statement statement = null;
        int total = 0;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT  COUNT(*) FROM contact");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        }  finally {
            closeConnection(connection, statement);
        }
        return total;
    }
	 private static Connection connectToDatabase() throws ClassNotFoundException, SQLException {
         Connection connection= null;
         try {
             Class.forName(DRIVER);
             connection = DriverManager.getConnection(URL, USER, PASSWORD);
         } catch (ClassNotFoundException e) {
            throw e;
         } catch (SQLException e) {
             throw e;
         }
         return connection;
     }
    private static void closeConnection(Connection connection, Statement statement) {
        if (statement  != null) {
            try {statement.close();}catch(Throwable tx) {}
        }
        if (connection!= null) {
            try {connection.close();}catch(Throwable tx) {}
        }

    }
    public static ArrayList<PhoneNumber> getPhones(int contactId) throws ClassNotFoundException, SQLException {
         ArrayList<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
             statement = connection.prepareStatement("SELECT  id, country, operator, number, TYPE, COMMENT FROM phone WHERE contact_id =?");
             statement.setInt(1, contactId);
             ResultSet rs = statement.executeQuery();
             while (rs.next()) {
                   PhoneNumber phoneNumber = new PhoneNumber();
                 phoneNumber.setId(rs.getInt(1));
                 phoneNumber.setCountry(rs.getString(2));
                 phoneNumber.setOperator(rs.getString(3));
                 phoneNumber.setNumber(rs.getString(4));
                 //setType
                 phoneNumber.setComment(rs.getString(6));
                 phoneNumbers.add(phoneNumber);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement);
        }
        return phoneNumbers;
    }
    private static Integer saveAdress(Address address, Connection connection) throws ClassNotFoundException, SQLException {
      //  Connection connection = connectToDatabase();

        PreparedStatement statement = null;
        try {
            if (address.getId() == null) {
            statement = connection.prepareStatement("INSERT INTO adress (country, town, street, house, place, post_index) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getTown());
            statement.setString(3, address.getStreet());
                if (address.getHouse() != null) {
            statement.setInt(4, address.getHouse());
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
                if (address.getPlace() != null) {
                    statement.setInt(5, address.getPlace());
                } else {
                    statement.setNull(5, Types.INTEGER);
                }
                if (address.getPostIndex() != null) {
                    statement.setInt(6, address.getPostIndex());
                } else {
                    statement.setNull(6, Types.INTEGER);
                }
            } else {
                statement = connection.prepareStatement("UPDATE adress SET country=?, town=?, street=?, house=?, place=?, post_index=? WHERE id =?", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, address.getCountry());
                statement.setString(2, address.getTown());
                statement.setString(3, address.getStreet());
                if (address.getHouse() != null) {
                    statement.setInt(4, address.getHouse());
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
                if (address.getPlace() != null) {
                    statement.setInt(5, address.getPlace());
                } else {
                    statement.setNull(5, Types.INTEGER);
                }
                if (address.getPostIndex() != null) {
                    statement.setInt(6, address.getPostIndex());
                } else {
                    statement.setNull(6, Types.INTEGER);
                }
                statement.setInt(7, address.getId());
            }
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                address.setId(rs.getInt("GENERATED_KEY"));
            }

        } catch (SQLException e) {
            throw e;
        }// finally {
//            closeConnection(connection, statement);
//        }
        return address.getId();
    }
        public static void setPhoto (String photo, int id) throws ClassNotFoundException, SQLException {
            //delete file
            Connection connection = connectToDatabase();
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("UPDATE contact SET PHOTO=? WHERE ID =? ");
                 statement.setString(1, photo);
                statement.setInt(2, id);
                statement.executeUpdate();
            } catch (SQLException e) {


            } finally {
                closeConnection(connection, statement);
            }
        }
    public static ArrayList<Contact> getBySearch(String query) throws ClassNotFoundException, SQLException {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Connection connection = connectToDatabase();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt(1));
                contact.setName(rs.getString(2));
                contact.setSurname(rs.getString(3));
                contact.setParentName(rs.getString(4));
                contact.setJob(rs.getString(5));
                if (rs.getDate(6) != null) {
                    contact.setBirthday(LocalDate.fromDateFields(rs.getDate(6)));
                }
      //          contact.setEmail(rs.getString(7));
              //  contact.setPhoto(rs.getString(8));
                contacts.add(contact);

            }

        } catch (SQLException e) {
            throw e;
        }        finally {
            closeConnection(connection, statement);
        }
        return contacts;
    }
    public static ArrayList<Contact> getFromDatabase (ArrayList<Integer> ids) throws ClassNotFoundException, SQLException {
        ArrayList<Contact>  contacts= new ArrayList<Contact>();
        Connection connection = connectToDatabase();
        Statement statement = null;
        String select = StringUtils.join(ids, "', '");
        select = StringUtils.substring(select, 0, select.length());
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT NAME, SURNAME, FATHERNAME, JOB, BIRTHDAY, EMAIL FROM contact WHERE ID IN %s", String.format("('%s')", select)));
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setName(rs.getString(1));
                contact.setSurname(rs.getString(2));
                contact.setParentName(rs.getString(3));
                contact.setJob(rs.getString(4));
                if (rs.getDate(5) != null) {
                    contact.setBirthday(LocalDate.fromDateFields(rs.getDate(5)));
                }
                contact.setEmail(rs.getString(6));
                contacts.add(contact);
            }

        } catch (SQLException e) {
            throw e;
        }

        closeConnection(connection, statement);
        return contacts;
    }
    public static void setPhoto (Integer id, String photo) throws ClassNotFoundException, SQLException {
        Connection connection;
        connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
                statement = connection.prepareStatement("UPDATE contact SET PHOTO=? WHERE ID =? ");
                statement.setInt(2, id);
                statement.setString(1, photo);
        } catch (SQLException e) {
            throw e;
        }     finally {
            closeConnection(connection, statement);
        }

    }
    public static void saveAttachement() {

    }
        /*   public static int saveToDatabase (Attachment attachment, int contact) {
          Connection connection = connectToDatabase();
          PreparedStatement statement = null;
          try {

              if(contact.getId() != 0) {
                  statement = connection.prepareStatement("UPDATE contact SET NAME=?, SURNAME=?, FATHERNAME=?,  JOB=?, EMAIL=?, BIRTHDAY=? WHERE ID =? ");
                  statement.setInt(7, contact.getId());
                  statement.setDate(6, contact.getBirthday() == null? null : java.sql.Date.valueOf(contact.getBirthday().toString()));
                  statement.setString(1, contact.getName());
                  statement.setString(2, contact.getSurname());
                  statement.setString(3, contact.getParentName());
                  statement.setString(4, contact.getJob());
                  statement.setString(5, contact.getEmail());
                  statement.execute();
                  statement = connection.prepareStatement("INSERT INTO adress (COUNTRY, TOWN, STREET, HOME, PLACE, INDEX) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
              } else {
                  statement = connection.prepareStatement("INSERT INTO contact (NAME, SURNAME, FATHERNAME,  JOB, EMAIL, BIRTHDAY) VALUES (?, ?,?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                  statement.setString(1, contact.getName());
                  statement.setString(2, contact.getSurname());
                  statement.setString(3, contact.getParentName());
                  statement.setString(4, contact.getJob());
                  statement.setString(5, contact.getEmail());
                  statement.setDate(6, contact.getBirthday() == null? null : java.sql.Date.valueOf(contact.getBirthday().toString()));
                  statement.execute();

                  ResultSet rs = statement.getGeneratedKeys();
                  if (rs.next()) {
                      contact.setId(rs.getInt("ID"));
                  }
              }
              contact.setId(saveAdress(contact.getAddress()));

          } catch (SQLException e) {
              throw e;
          }   finally {

              closeConnection(connection, statement);
          }
          return contact.getId();
      }  */

        public static void addPhones (Integer id, ArrayList<PhoneNumber> phones) throws ClassNotFoundException, SQLException {
            Connection connection = connectToDatabase();
            connection.setAutoCommit(false);
            PreparedStatement statement = null;
            try {
                for (PhoneNumber ph : phones)   {
                    statement = connection.prepareStatement("INSERT INTO phone (number, type, comment, contact_id, country, operator) VALUES (?, ?,?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, ph.getNumber());
                    statement.setString(2, ph.getPhoneType());
                    statement.setString(3, ph.getComment());
                    statement.setInt(4, id);
                    statement.setString(5, ph.getCountry());
                    statement.setString(6, ph.getOperator());
                    statement.execute();
                }
               connection.commit();

            } catch (SQLException e) {
                throw e;
            }   finally {

                closeConnection(connection, statement);
            }
        }
    public static void addAttachment (Attachment attachment) throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
                statement = connection.prepareStatement("INSERT INTO attachment (NAME, DATE, COMMENT, CONTACT_ID, PATH) VALUES (?, ?,?, ?, ?)");
                statement.setString(1, attachment.getName());
                statement.setTimestamp(2, new Timestamp(attachment.getDate().getMillis()));
                statement.setString(3, attachment.getComment());
                statement.setInt(4, attachment.getContactId());
                statement.setString(5, attachment.getPath());
                statement.execute();
        } catch (SQLException e) {
            throw e;
        }   finally {

            closeConnection(connection, statement);
        }
    }
    public static void removeNumbersExcept (int id, ArrayList<Integer> ids) throws ClassNotFoundException, SQLException {
        if (ids.isEmpty()) {
            return;
        }
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            String remove = StringUtils.join(ids, "', '");
            remove = StringUtils.substring(remove, 0, remove.length());
            statement = connection.prepareStatement(String.format("DELETE FROM phone WHERE contact_id=? AND ID NOT IN %s", String.format("('%s')", remove)));
            statement.setInt(1, id);
            statement.execute();
        }
        catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection, statement);
        }
    }
}
