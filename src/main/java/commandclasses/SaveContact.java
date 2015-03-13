package commandclasses;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import dao.DataAccessObject;
import dataclasses.Address;
import dataclasses.Contact;
import dataclasses.PhoneNumber;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 22.02.15
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class SaveContact extends Command {
    private final static Logger logger= LogManager.getLogger(SaveContact.class);
    @Override
    public void process() {
        logger.log(Level.DEBUG, "process()");
        boolean newContact = "null".equals(req.getParameter("id"));
        logger.log(Level.TRACE, String.format("Phones: %s", req.getParameter("phones")));;
        String idsParameter = req.getParameter("ids");
        ArrayList<Integer> stay = new ArrayList<Integer>();
        if (!StringUtils.isEmpty(idsParameter)) {

        for (String idP: idsParameter.split("#\t")) {
             stay.add(Integer.valueOf(idP));
        }
        logger.log(Level.TRACE, String.format("Phones: %s", idsParameter));
        if (StringUtils.isNotEmpty(idsParameter))    {
            String[] ids = idsParameter.split("\t");
            ArrayList<Integer> notRemove = new ArrayList<Integer>();
            for (int i = 0; i<ids.length; ++i) {
                notRemove.add(Integer.valueOf(StringUtils.substringBefore(ids[i], "#")));
            }
        }
        }
        String ph = req.getParameter("phones");
        String[] newPhones = req.getParameter("phones").split("#\t");
        String photo = req.getParameter("photo");

        String[] countries = new String[newPhones.length];
        String[] operators = new String[newPhones.length];
        String[] numbers = new String[newPhones.length];

        ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();

        String[] comments = req.getParameter("comments").split("\t");
        String[] types = req.getParameter("types").split("\t");

         if (StringUtils.isNotEmpty(newPhones[0])) {
        for (int i = 0; i<numbers.length; ++i) {
            countries[i]=StringUtils.substringBefore(newPhones[i], "\t");
            operators[i]=StringUtils.substringBetween(newPhones[i], "\t", "\t");
            numbers[i]=StringUtils.substringAfterLast(newPhones[i], "\t");

            phones.add(new PhoneNumber(numbers[i], PhoneNumber.PhoneType.valueOf(StringUtils.substringBefore(types[i], "#")), StringUtils.substringBefore(comments[i], "#"), countries[i], operators[i]));
        }
         }
        Contact contact = null;
        try {
            contact = newContact ? new Contact() : DataAccessObject.getFromDatabase(Integer.valueOf(req.getParameter("id")));
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.toString());
            try {
                res.sendRedirect("error.jsp");return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e.toString());
            try {
                res.sendRedirect("error.jsp");return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        }
        contact.setName(req.getParameter("name"));
        contact.setSurname(req.getParameter("surname"));
        contact.setParentName(StringUtils.isEmpty(req.getParameter("fathername")) ? null :req.getParameter("fathername"));
        try {
        contact.setBirthday(new LocalDate(Integer.valueOf(req.getParameter("year")), Integer.valueOf(req.getParameterValues("months")[0]), Integer.valueOf(req.getParameter("day"))));
        }catch (NumberFormatException ex) {
            logger.log(Level.ERROR, ex.toString());
        }
        contact.setJob(StringUtils.isEmpty(req.getParameter("job")) ? null : req.getParameter("job"));
        contact.setCitizenship(StringUtils.isEmpty(req.getParameter("citizenship")) ? null : req.getParameter("citizenship"));
        contact.setEmail(StringUtils.isEmpty(req.getParameter("email")) ? null : req.getParameter("email"));
        contact.setGender(Contact.Gender.valueOf(req.getParameter("gender")));

        if (req.getParameter("family") != null) {
        contact.setFamilyStatus(Contact.FamilyStatus.values()[Integer.valueOf(req.getParameter("family"))]);
        }
        Integer house = StringUtils.isNotEmpty(req.getParameter("house")) ? Integer.valueOf(req.getParameter("house")) : null;
        Integer place = StringUtils.isNotEmpty(req.getParameter("place")) ? Integer.valueOf(req.getParameter("place")) : null;
        Integer postIndex = StringUtils.isNotEmpty(req.getParameter("postIndex")) ? Integer.valueOf(req.getParameter("postIndex")) : null;

        Address address = new Address(req.getParameter("country"),  req.getParameter("town"), req.getParameter("street"),
                house, place, postIndex);
        if (contact.getAddress() != null) {
        address.setId(contact.getAddress().getId());
        }
        contact.setAddress(address);
        Integer id = null;
        try {
            id = DataAccessObject.saveToDatabase(contact);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.toString());
            try {
                res.sendRedirect("error.jsp");return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e.toString());
            try {
                res.sendRedirect("error.jsp");return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        }
        try {
            DataAccessObject.removeNumbersExcept(id, stay);
        } catch (ClassNotFoundException e) {
            try {
            res.sendRedirect("error.jsp");return;
        } catch (IOException e1) {
            logger.log(Level.ERROR, e1.toString());
        }
        } catch (SQLException e) {
         try {
             res.sendRedirect("error.jsp");return;
    } catch (IOException e1) {
        logger.log(Level.ERROR, e1.toString());
    }
        }
        if (!"null".equals(photo) && StringUtils.isNotEmpty(photo)) {
            try {
                DataAccessObject.setPhoto(photo, id);
            } catch (ClassNotFoundException e) {
                logger.log(Level.ERROR, e.toString());
                try {
                    res.sendRedirect("error.jsp");return;
                } catch (IOException e1) {
                    logger.log(Level.ERROR, e1.toString());
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, e.toString());
                try {
                    res.sendRedirect("error.jsp");return;
                } catch (IOException e1) {
                    logger.log(Level.ERROR, e1.toString());
                }
            }
        }
        try {
            DataAccessObject.addPhones(id, phones);
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e.toString());
            try {
                res.sendRedirect("error.jsp");return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.toString());
            try {
                res.sendRedirect("error.jsp");
                return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        }
        try {
            res.sendRedirect("index.jsp");
        } catch (IOException e) {
            logger.log(Level.ERROR, e.toString());
            try {
                res.sendRedirect("error.jsp");
                return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        }
    }
}
