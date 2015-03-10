package servlet;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

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

    static String URL = "jdbc:mysql://localhost:3306/contacts_tatarintseva";
    private static String USER = "username";

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        DataAccessObject.init = init;
    }

    private static boolean init = false;
    static String PASSWORD = "password";
    static String DRIVER = "com.mysql.jdbc.Driver";

    public static void setUSER(String USER) {
        DataAccessObject.USER = USER;
    }

   // private static String user;
    public static int saveToDatabase (Contact contact) {
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            int address = saveAdress(contact.getAddress());
            if(contact.getId() != null) {
                statement = connection.prepareStatement("UPDATE contact SET NAME=?, SURNAME=?, FATHERNAME=?,  JOB=?, EMAIL=?, BIRTHDAY=?, adress_id=? WHERE ID =? ");
                statement.setInt(8, contact.getId());
                statement.setInt(7, address);
                statement.setDate(6, contact.getBirthday() == null? null : java.sql.Date.valueOf(contact.getBirthday().toString()));
                statement.setString(1, contact.getName());
                statement.setString(2, contact.getSurname());
                statement.setString(3, contact.getParentName());
                statement.setString(4, contact.getJob());
                statement.setString(5, contact.getEmail());
                statement.execute();
                //statement = connection.prepareStatement("INSERT INTO adress (COUNTRY, TOWN, STREET, HOME, PLACE, INDEX) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            } else {
            statement = connection.prepareStatement("INSERT INTO contact (NAME, SURNAME, FATHERNAME,  JOB, EMAIL, BIRTHDAY, adress_id) VALUES (?, ?,?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getSurname());
            statement.setString(3, contact.getParentName());
            statement.setString(4, contact.getJob());
            statement.setString(5, contact.getEmail());
            statement.setDate(6, contact.getBirthday() == null ? null : java.sql.Date.valueOf(contact.getBirthday().toString()));
            statement.setInt(7, address);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                   contact.setId(rs.getInt("GENERATED_KEY"));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }   finally {

        closeConnection(connection, statement);
        }
        return contact.getId();
    }
    public static ArrayList<Integer> getPhonesOf (Integer id) {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }   finally {
            closeConnection(connection, statement);
        }
        return res;
    }
    public static ArrayList<Integer> getAttachmentsOf (Integer id) {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }   finally {
            closeConnection(connection, statement);
        }
        return res;
    }
    public static ArrayList<String> getEmails(ArrayList<Integer> ids) {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeConnection(connection, preparedStatement);
        }
            return res;
    }

    public static ArrayList<Contact> getFromDatabase (int pageNumber, int onPage) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT  NAME, SURNAME, FATHERNAME, JOB, BIRTHDAY, EMAIL, ID FROM contact ORDER BY name ASC LIMIT ?, ?");
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
                contacts.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }    finally {
            closeConnection(connection, statement);
        }
        return contacts;

    }
    public static Contact getFromDatabase (int id) {
        Contact contact = new Contact();
        contact.setId(id);
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT NAME, SURNAME, FATHERNAME, JOB, BIRTHDAY, EMAIL, PHOTO FROM contact WHERE ID =?");
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
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  finally {

        closeConnection(connection, statement);
        }
        return contact;

    }
    public static void deleteFromDatabase (ArrayList<Integer> ids) {
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
            String remove = StringUtils.join(ids, "', '");
            remove = StringUtils.substring(remove, 0, remove.length());
            statement = connection.prepareStatement(String.format("DELETE FROM contact WHERE ID IN %s", String.format("('%s')", remove)));
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
        closeConnection(connection, statement);
        }
    }

    public static int total() {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  finally {
            closeConnection(connection, statement);
        }
        return total;
    }
	 private static Connection connectToDatabase() {
         Connection connection= null;
         try {
             Class.forName(DRIVER);
             connection = DriverManager.getConnection(URL, USER, PASSWORD);
         } catch (ClassNotFoundException e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
         } catch (SQLException e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
    public static ArrayList<PhoneNumber> getPhones(int contactId) {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeConnection(connection, statement);
        }
        return phoneNumbers;
    }
    private static int saveAdress(Address address) {
        Connection connection = connectToDatabase();
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeConnection(connection, statement);
        }
        return address.getId();
    }
        public static void setPhoto (String photo, int id) {
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
    public static ArrayList<Contact> getBySearch(String query) {
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
                contact.setEmail(rs.getString(7));
                contact.setPhoto(rs.getString(8));
                contacts.add(contact);

            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }        finally {
            closeConnection(connection, statement);
        }
        return contacts;
    }
    public static ArrayList<Contact> getFromDatabase (ArrayList<Integer> ids) {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        closeConnection(connection, statement);
        return contacts;
    }
    public static void setPhoto (Integer id, String photo) {
        Connection connection = connectToDatabase();
        PreparedStatement statement = null;
        try {
                statement = connection.prepareStatement("UPDATE contact SET PHOTO=? WHERE ID =? ");
                statement.setInt(2, id);
                statement.setString(1, photo);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }   finally {

              closeConnection(connection, statement);
          }
          return contact.getId();
      }  */

        public static void addPhones (Integer id, ArrayList<PhoneNumber> phones) {
            Connection connection = connectToDatabase();
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


            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }   finally {

                closeConnection(connection, statement);
            }
        }
    public static void addAttachment (Attachment attachment) {
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }   finally {

            closeConnection(connection, statement);
        }
    }
}