package commandclasses;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.DataAccessObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 22.02.15
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public class RemoveContacts extends Command {
    private final static Logger logger= LogManager.getLogger(RemoveContacts.class);
    @Override
    public void process() {
        super.process();
        ArrayList<Integer> toRemove = getListFromRequest();
        try {
            DataAccessObject.deleteFromDatabase(toRemove);
        } catch (SQLException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp?msg=0");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
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
