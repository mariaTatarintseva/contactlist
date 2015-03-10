package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
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
        String[] recipients;
        if (req.getParameterValues("to").length>0) {
            recipients = req.getParameterValues("to");
        } else {
            recipients = new String[0];
        }
        String header = req.getParameter("header");
        String mailText = req.getParameter("mailText");

        STGroup group = new STGroupFile("group.stg");

        ST text = group.getInstanceOf("t1");
        text.add("arg1", "This is template 1");
        text.add("arg2", 222);
        System.out.println(text.render());




        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("mtmt.2015@mail.ru", "mtmt2015");
                    }
                });

        MimeMessage message = new MimeMessage(session);
        for (String recipient: recipients) {
        try {
            message.setFrom(new InternetAddress("mtmt.2015@mail.ru"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(header);
            message.setText(mailText);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        }
        System.out.print(req.getParameter("mailText"));
        try {
            res.sendRedirect("mailsent.jsp");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
