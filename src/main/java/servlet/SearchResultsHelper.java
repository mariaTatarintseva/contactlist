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
        ArrayList<Contact> contacts = DataAccessObject.getBySearch(ids); //unique method!!
        JspWriter out = getJspContext().getOut();
        String table="";
        for (Contact contact: contacts) {

             table = String.format("%s%s%d%s%d%s%s%s%s%s%s%s%s%s", table,  "<div class=\"left2\"><input type=\"checkbox\" name=\"remove\" value = ", contact.getId() , "></div><div class=\"left1\"><a href=\"add.jsp?id=",
                     contact.getId(), "\">", contact.getFullName(), "</a></div><div class=\"left1\">", contact.getBirthday(),
                     "</div><div class=\"left1\">", contact.getAddressString(), "</div><div class=\"left1\">", contact.getJob(), "</div>");
        }

        out.println(table);
    }
}