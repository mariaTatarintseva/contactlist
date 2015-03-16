package commandclasses;

import com.mysql.jdbc.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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
            super.process();
            String query = "SELECT contact.ID, NAME, SURNAME, FATHERNAME, JOB, BIRTHDAY FROM contact LEFT JOIN adress ON contact.adress_id = adress.id WHERE 1=1";
           try {
            if (!StringUtils.isNullOrEmpty(req.getParameter("name"))) {
                query = String.format("%s AND NAME LIKE '%%%s%%'", query, req.getParameter("name"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("surname"))) {
                query = String.format("%s AND SURNAME LIKE '%%%s%%'", query, req.getParameter("surname"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("fathername"))) {
                query = String.format("%s AND FATHERNAME LIKE '%%%s%%'", query, req.getParameter("fathername"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("dayL")) && !StringUtils.isNullOrEmpty(req.getParameter("yearL")) && !StringUtils.isNullOrEmpty(req.getParameter("monthL"))) {
                query = String.format("%s AND BIRTHDAY <= '%%%s%%'", query, DateFormatUtils.ISO_DATE_FORMAT.format(new Date(Integer.valueOf(req.getParameter("yearL")), Integer.valueOf(req.getParameterValues("monthsL")[0]), Integer.valueOf(req.getParameter("dayL")))));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("dayU")) && !StringUtils.isNullOrEmpty(req.getParameter("yearU"))&& !StringUtils.isNullOrEmpty(req.getParameter("monthU"))) {
                query = String.format("%s AND BIRTHDAY >= '%%%s%%'", query, DateFormatUtils.ISO_DATE_FORMAT.format(new Date(Integer.valueOf(req.getParameter("yearU")), Integer.valueOf(req.getParameterValues("monthsU")[0]), Integer.valueOf(req.getParameter("dayU")))));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("day"))) {
                query = String.format("%s AND DAY(BIRTHDAY) = '%s'", query, req.getParameter("day"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("months"))) {
                query = String.format("%s AND MONTH(BIRTHDAY) = '%s'", query, req.getParameter("months"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("year"))) {
                query = String.format("%s AND YEAR(BIRTHDAY) = '%s'", query, req.getParameter("year"));
            }
               //DATES
            if (!StringUtils.isNullOrEmpty(req.getParameter("gender"))) {
                query = String.format("%s AND GENDER = '%s'", query, req.getParameter("gender"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("country"))) {
                query = String.format("%s AND country LIKE '%%%s%%'", query, req.getParameter("country"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("house"))) {
                query = String.format("%s AND house LIKE '%%%s%%'", query, req.getParameter("house"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("street"))) {
                query = String.format("%s AND street LIKE '%%%s%%'", query, req.getParameter("street"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("town"))) {
                query = String.format("%s AND town LIKE '%%%s%%'", query, req.getParameter("town"));
            }
            if (!StringUtils.isNullOrEmpty(req.getParameter("place"))) {
                query = String.format("%s AND place LIKE '%%%s%%'", query, req.getParameter("place"));
            }
            logger.log(Level.TRACE, String.format("process() query formed %s", query));
            req.getSession().setAttribute("results", query);
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