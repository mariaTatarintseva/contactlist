package commandclasses;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 21.02.15
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class AddContact extends Command {
    private final static Logger logger= LogManager.getLogger(AddContact.class);
    @Override
    public void process() {
        logger.log(Level.DEBUG, "process()");
        req.getSession().setAttribute("contact", null);
        try {
            res.sendRedirect("add.jsp");
        } catch (IOException e) {
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.getStackTrace());
            }
        }
    }
}
