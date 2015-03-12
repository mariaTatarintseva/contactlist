package commandclasses;

import com.mysql.jdbc.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 23.02.15
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class Search extends Command {
    private final static Logger logger= LogManager.getLogger(Search.class);
        @Override
        public void process() {
            logger.log(Level.DEBUG, "process()");
            System.out.println("SearchProcess");
            String query = "SELECT contact.ID, NAME, SURNAME, FATHERNAME, JOB, BIRTHDAY FROM contact LEFT JOIN adress ON contact.adress_id = adress.id WHERE 1=1";
            if (!StringUtils.isNullOrEmpty(req.getParameter("name"))) {
                query = String.format("%s AND NAME LIKE '%%%s%%'", query, req.getParameter("name"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("surname"))) {
                query = String.format("%s AND SURNAME LIKE '%%%s%%'", query, req.getParameter("surname"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("fathername"))) {
                query = String.format("%s AND FATHERNAME LIKE '%%%s%%'", query, req.getParameter("fathername"));
            }
               //DATES

            if (!StringUtils.isNullOrEmpty(req.getParameter("country"))) {
                query = String.format("%s AND FATHERNAME LIKE '%%%s%%'", query, req.getParameter("country"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("house"))) {
                query = String.format("%s AND FATHERNAME LIKE '%%%s%%'", query, req.getParameter("house"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("street"))) {
                query = String.format("%s AND FATHERNAME LIKE '%%%s%%'", query, req.getParameter("street"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("town"))) {
                query = String.format("%s AND FATHERNAME LIKE '%%%s%%'", query, req.getParameter("town"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("place"))) {
                query = String.format("%s AND FATHERNAME LIKE '%%%s%%'", query, req.getParameter("place"));
            }
            logger.log(Level.TRACE, String.format("process() query formed %s", query));
            req.getSession().setAttribute("results", query);
            try {
                res.sendRedirect("index.jsp");
            } catch (IOException e) {
                try {
                    res.sendRedirect("error.jsp");
                    return;
                } catch (IOException e1) {
                    logger.log(Level.ERROR, e1.getStackTrace());
                }
            }
        }
}