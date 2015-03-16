package commandclasses;

import dao.DataAccessObject;
import dataclasses.Address;
import dataclasses.Attachment;
import dataclasses.Contact;
import dataclasses.PhoneNumber;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
        super.process();

        boolean newContact = "null".equals(req.getParameter("id"));
        logger.log(Level.TRACE, String.format("Phones: %s", req.getParameter("numbers")));
        String idsParameter = req.getParameter("ids");
        String idsParameterAtt = req.getParameter("idsAtt");


        ArrayList<Integer> stay = new ArrayList<Integer>();
        ArrayList<Integer> stayAtt = new ArrayList<Integer>();
        if (!StringUtils.isEmpty(idsParameter)) {

        for (String idP: idsParameter.split("#\t")) {
             stay.add(Integer.valueOf(idP));
        }
        logger.log(Level.TRACE, String.format("Ids: %s", idsParameter));
        if (StringUtils.isNotEmpty(idsParameter))    {
            String[] ids = idsParameter.split("\t");
            ArrayList<Integer> notRemove = new ArrayList<Integer>();
            for (int i = 0; i<ids.length; ++i) {
                notRemove.add(Integer.valueOf(StringUtils.substringBefore(ids[i], "#")));
            }
        }
        }

        if (!StringUtils.isEmpty(idsParameterAtt)) {

            for (String idP: idsParameterAtt.split("#\t")) {
                stayAtt.add(Integer.valueOf(idP));
            }
            logger.log(Level.TRACE, String.format("IdsAtt: %s", idsParameterAtt));
            if (StringUtils.isNotEmpty(idsParameterAtt))    {
                String[] idsAtt = idsParameterAtt.split("\t");
                ArrayList<Integer> notRemoveAtt = new ArrayList<Integer>();
                for (int i = 0; i<idsAtt.length; ++i) {
                    notRemoveAtt.add(Integer.valueOf(StringUtils.substringBefore(idsAtt[i], "#")));
                }
            }
        }


        String[] newPhones = req.getParameter("numbers").split("#\t");
        String photo = req.getParameter("photo");

        Integer[] countries = new Integer[newPhones.length];
        Integer[] operators = new Integer[newPhones.length];
        Integer[] numbers = new Integer[newPhones.length];

        ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
        ArrayList<Attachment> attachments = new ArrayList<Attachment>();

        String[] comments = req.getParameter("comments").split("\t");
        String[] types = req.getParameter("types").split("\t");

        String[] commentsAtt = req.getParameter("commentsAtt").split("\t");
        String[] namesAtt = req.getParameter("namesAtt").split("\t");
        String[] datesAtt = req.getParameter("datesAtt").split("\t");

         if (StringUtils.isNotEmpty(newPhones[0])) {
        for (int i = 0; i<numbers.length; ++i) {
            String c = StringUtils.substringBefore(newPhones[i], "\t");
            String o = StringUtils.substringBetween(newPhones[i], "\t", "\t");
            String n = StringUtils.substringAfterLast(newPhones[i], "\t");
            operators[i]= StringUtils.isEmpty(o) ? null : Integer.valueOf(o);
            countries[i]= StringUtils.isEmpty(c) ? null : Integer.valueOf(c);

            numbers[i]=  StringUtils.isEmpty(n) ? null : Integer.valueOf(n);

            phones.add(new PhoneNumber(numbers[i], PhoneNumber.PhoneType.valueOf(StringUtils.substringBefore(types[i], "#")), StringUtils.substringBefore(comments[i], "#"), countries[i], operators[i]));
        }
         }

        if (StringUtils.isNotEmpty(namesAtt[0])) {
            for (int i = 0; i<numbers.length; ++i) {
                try {
                Attachment attachment = new Attachment();
                attachment.setName(StringUtils.substringBefore(namesAtt[i], "#"));
                attachment.setDate(DateTime.now());
                attachment.setComment(StringUtils.substringBefore(commentsAtt[i], "#"));
                attachments.add(attachment);
                } catch (Exception e) {
                    logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
                }
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
        contact.setWebSite(StringUtils.isEmpty(req.getParameter("webSite")) ? null : req.getParameter("webSite"));
        contact.setCitizenship(StringUtils.isEmpty(req.getParameter("citizenship")) ? null : req.getParameter("citizenship"));
        contact.setEmail(StringUtils.isEmpty(req.getParameter("email")) ? null : req.getParameter("email"));
        contact.setGender(Contact.Gender.valueOf(req.getParameter("gender")));

        if (!StringUtils.isEmpty(req.getParameter("family"))) {
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
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp?msg=0");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
        }
        try {
            DataAccessObject.removeNumbersExcept(id, stay);
            DataAccessObject.removeAttachmentsExcept(id, stayAtt);
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp?msg=0");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
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
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
                try {
                    res.sendRedirect("error.jsp?msg=0");
                } catch (IOException e1) {
                    logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
                }
            }
        }
        try {
            DataAccessObject.addPhones(id, phones);
            DataAccessObject.addAttachments(id,  attachments);
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
                res.sendRedirect("error.jsp?msg=0");
                return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.toString());
            }
        }
        try {
            res.sendRedirect("index.jsp");
        } catch (IOException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
        }
    }
}
