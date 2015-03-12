package commandclasses;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 06.03.15
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class Error extends Command {
    private final static Logger logger= LogManager.getLogger(Error.class);
    @Override public void process () {
        logger.log(Level.DEBUG, "process()");
        //log the mistake
        try {
            res.sendRedirect("error.jsp");
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getStackTrace());
        }
    }
}
