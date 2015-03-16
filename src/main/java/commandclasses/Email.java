package commandclasses;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
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
        super.process();
        try {
        ArrayList<Integer> toMail = getListFromRequest();
        String request = toMail.size() > 0 ? "email.jsp?to=" : "email.jsp";
        String mails = StringUtils.join(toMail, "&to=");
        mails = StringUtils.substring(mails, 0, mails.length());
       res.sendRedirect(toMail.size() > 0 ? String.format("%s%s", request, mails) : request);
       } catch (Exception e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
             }
    }
}
