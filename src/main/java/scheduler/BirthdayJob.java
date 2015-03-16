package scheduler;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 14.03.15
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */

import dao.DataAccessObject;
import dataclasses.Contact;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class BirthdayJob implements Job{
    private final static Logger logger= LogManager.getLogger(BirthdayJob.class);
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        logger.log(Level.DEBUG, "execute()");


        try {
            ArrayList<Contact> contacts = DataAccessObject.getContactsWithBirthday();
            if (contacts.isEmpty()) {
                return;
            }
            String receivers="";
            for (Contact contact: contacts) {
          //      System.out.println(contact.getFullName());
                receivers = String.format("%s\n %s (email: %s)", receivers, contact.getFullName(), contact.getEmail());

            }
            //send email
            BirthdayTrigger trigger  = ((BirthdayTrigger)context.getTrigger());
            String messageText=String.format(trigger.getReminderPattern(), trigger.getUserShowName(), receivers);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.mail.ru");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            final String finalSender = trigger.getSender();
            final String finalPassword = trigger.getSenderPassword();
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(finalSender, finalPassword);
                        }
                    });

            MimeMessage message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(trigger.getSender()));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(trigger.getUser()));
            message.setSubject("Напоминание");
            message.setText(messageText);
            Transport.send(message);
            } catch (MessagingException e) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
        } catch (SQLException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
        }


    }

}