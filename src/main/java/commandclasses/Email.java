package commandclasses;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 23.02.15
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public class Email extends Command {
    private final static Logger logger= LogManager.getLogger(Email.class);
    @Override
    public void process() {
        ArrayList<Integer> toMail = getListFromRequest();
      //  ArrayList<String> emails = DataAccessObject.getEmails(toRemove);
        String request = toMail.size() > 0 ? "email.jsp?to=" : "email.jsp";
        String mails = StringUtils.join(toMail, "&to=");
        mails = StringUtils.substring(mails, 0, mails.length());
        try {
       res.sendRedirect(toMail.size() > 0 ? String.format("%s%s", request, mails) : request);
             } catch (IOException e) {
                 e.printStackTrace();
             }
    }
}
