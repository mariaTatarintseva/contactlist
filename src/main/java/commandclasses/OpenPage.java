package commandclasses;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 03.03.15
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class OpenPage extends Command {
    private final static Logger logger= LogManager.getLogger(OpenPage.class);
    @Override
    public void process() {
        logger.log(Level.DEBUG, "process()");
        System.out.print("OpenPage process()");
        try {
            res.sendRedirect(String.format("index.jsp?pageNumber=%s&onPage=%s", req.getParameter("page") ,req.getParameter("onPage")));
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.getStackTrace());
            }
        }
    }
}

