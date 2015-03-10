package servlet;

import com.mysql.jdbc.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

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
            System.out.println("SearchProcess");
            String query = "SELECT contact.ID FROM contact LEFT JOIN adress ON contact.adress_id = adress.id WHERE 1=1";
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

            // ArrayList<Integer> results = DataAccessObject.getBySearch(query);
            req.getSession().setAttribute("results", query);
            try {
                res.sendRedirect("index.jsp");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
}