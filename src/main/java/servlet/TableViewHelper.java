package servlet;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 20.02.15
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
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
            ArrayList<Contact> list = DataAccessObject.getFromDatabase(page, onPage);
           JspWriter out = getJspContext().getOut();
         String table="";
        for (Contact contact: list) {
            table = String.format("<div class=\"left4\">%s<div class=\"left2\"><input type=\"checkbox\" name=\"remove\" value = %d></div><div class=\"left1\"><a href=\"add.jsp?id=%d\">%s</a></div><div class=\"left1\">%s</div><div class=\"left1\">%s</div><div class=\"left1\">%s</div></div>", table, contact.getId(), contact.getId(), contact.getFullName(), contact.getBirthday() == null? "" : contact.getBirthday(), contact.getAddressString(), contact.getJob() == null ? "" : contact.getJob());
        }

           out.println(table);
    }
}