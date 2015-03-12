package commandclasses;

import dao.DataAccessObject;
import dataclasses.Contact;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 25.02.15
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 */
public class SendEmail extends Command {
    private final static Logger logger= LogManager.getLogger(SendEmail.class);
    @Override
    public void process() {
        logger.log(Level.DEBUG, "process()");
        String sender = null;
        String senderName = null;
        String password = null;
         try {
        Properties properties = new Properties();
        InputStream inputStream =req.getServletContext().getResourceAsStream("/WEB-INF/properties/contactlist.properties");
        properties.load(inputStream);
             sender = properties.getProperty("userEmail");
             password = properties.getProperty("userEmailPassword");
             senderName= properties.getProperty("userShowName");
         } catch (IOException e) {
             logger.log(Level.ERROR, e.getMessage());
             try {
                 res.sendRedirect("error.jsp");
             } catch (IOException e1) {
                 logger.log(Level.ERROR, e1.getMessage());
             }
             return;
         }


        String[] recipients;
        if (req.getParameterValues("to").length>0) {
            recipients = req.getParameterValues("to");
        } else {
            recipients = new String[0];
        }
        ArrayList<Integer> rec = new ArrayList<Integer>();
        for (int i = 0; i<recipients.length; ++i) {
            rec.add(Integer.valueOf(recipients[i]));
        }
        ArrayList<Contact> contacts = null;
        try {
             contacts = DataAccessObject.getFromDatabase(rec);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String header = req.getParameter("header");
        String mailText = req.getParameter("mailText");

        STGroup group = new STGroupFile("group.stg");

        ST text = group.getInstanceOf("t1");
        //text.add("arg1", "This is template 1");
        text.add("arg2", mailText);
        text.add("arg3", senderName);
        System.out.println(text.render());




        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        final String finalSender = sender;
        final String finalPassword = password;
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(finalSender, finalPassword);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        for (Contact contact: contacts) {
        try {

            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(contact.getEmail()));
            message.setSubject(header);
            text.add("arg1", contact.getName());
            message.setText(text.render());

            Transport.send(message);
        } catch (MessagingException e) {
            try {
                res.sendRedirect("error.jsp");
                return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.getStackTrace());
            }
        }

        }
        System.out.print(req.getParameter("mailText"));
        try {
            res.sendRedirect("mailsent.jsp");
            return;
        } catch (IOException e) {
        }
        try {
            res.sendRedirect("error.jsp");
            return;
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getStackTrace());
        }
    }

}
