package view;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 20.02.15
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.DataAccessObject;
import dataclasses.Contact;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchResultsHelper extends SimpleTagSupport{

    private final static Logger logger= LogManager.getLogger(SearchResultsHelper.class);
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    private String ids;

    public void doTag() throws JspException, IOException
    {
        if ("null".equals("ids")) {
            return;
        }
        ArrayList<Contact> contacts = null; //unique method!!
        try {
            contacts = DataAccessObject.getBySearch(ids);
        }  catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        }
        JspWriter out = getJspContext().getOut();
//        String table="";
//        for (Contact contact: contacts) {
//
//             table = String.format("%s%s%d%s%d%s%s%s%s%s%s%s%s%s", table,  "<div class=\"left2\"><input type=\"checkbox\" name=\"remove\" value = ", contact.getId() , "></div><div class=\"left1\"><a href=\"add.jsp?id=",
//                     contact.getId(), "\">", contact.getFullName(), "</a></div><div class=\"left1\">", contact.getBirthday() == null? "" : contact.getBirthday(),
//                     "</div><div class=\"left1\">", contact.getAddressString(), "</div><div class=\"left1\">", contact.getJob() == null? "" : contact.getJob(), "</div>");
//        }
        String table="";
        int i = 0;
        for (Contact contact: contacts) {
            table = String.format("%s<div class=\"row color%d\"><div class=\"col c5\"><input type=\"checkbox\" name=\"remove\" value = %d></div><div class=\"col c20\"/><a href=\"add.jsp?id=%d\">%s</a></div><div class=\"col c20\">%s</div><div class=\"col c20\">%s</div><div class=\"col c20\">%s</div></div>", table, i%2, contact.getId(), contact.getId(), contact.getFullName(), contact.getBirthday() == null? "" : DateFormatUtils.format(contact.getBirthday().toDate(), "dd MMM yyyy"), contact.getAddressString(), contact.getJob() == null ? "" : contact.getJob());
            ++i;
        }
        table = String.format("<div class = \"table\" id=\"table\">%s</div>", table);
        out.println(table);
//        out.println(table);
    }
}