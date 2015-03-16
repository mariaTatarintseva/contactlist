package view;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 20.02.15
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.DataAccessObject;
import dataclasses.Contact;
import org.joda.time.format.DateTimeFormatter;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableViewHelper extends SimpleTagSupport{
    private final static Logger logger= LogManager.getLogger(TableViewHelper.class);
    public void setOnPage(int onPage) {
        this.onPage = onPage;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private int onPage;
    private int page;

    public void doTag() throws JspException, IOException
    {
        ArrayList<Contact> list = null;
        try {
            list = DataAccessObject.getFromDatabase(page, onPage);
        }  catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        }
        JspWriter out = getJspContext().getOut();
         String table="";
        int i = 0;
        for (Contact contact: list) {
            table = String.format("%s<div class=\"row color%d\"><div class=\"col c5\"><input type=\"checkbox\" name=\"remove\" value = %d></div><div class=\"col c20\"/><a href=\"add.jsp?id=%d\">%s</a></div><div class=\"col c20\">%s</div><div class=\"col c20\">%s</div><div class=\"col c20\">%s</div></div>", table, i%2, contact.getId(), contact.getId(), contact.getFullName(), contact.getBirthday() == null? "" : DateFormatUtils.format(contact.getBirthday().toDate(), "dd MMM yyyy"), contact.getAddressString(), contact.getJob() == null ? "" : contact.getJob());
            ++i;
        }
        table = String.format("<div class = \"table\" id=\"table\">%s</div>", table);
           out.println(table);
    }
}