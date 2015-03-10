package servlet;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 20.02.15
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.ArrayList;

public class PhonesTableViewHelper extends SimpleTagSupport{
    private final static Logger logger= LogManager.getLogger(PhonesTableViewHelper.class);

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public void doTag() throws JspException, IOException
    {
        if (id == 0) {
            return;
        }
        ArrayList<PhoneNumber> list = DataAccessObject.getPhones(id);
        JspWriter out = getJspContext().getOut();
        String table="";
        String oldN, oldT, oldC;
        for (PhoneNumber phoneNumber: list) {
            oldN = String.format("oldN%d", phoneNumber.getId());
            oldT = String.format("oldT%d", phoneNumber.getId());
            oldC = String.format("oldC%d", phoneNumber.getId());
            table = String.format("%s<div><div class=\"left2\"><input type=\"checkbox\" name=\"oldPhone\" value = %d></div><div class=\"left1\" id=\"%s\"><a href=\"editPhone.jsp?id=%d\">%s</a></div><div class=\"left1\" id=\"%s\">%s<div class=\"left1\" id=\"%s\">%s</div></div>", table, phoneNumber.getId(), oldN, phoneNumber.getId(), phoneNumber.toString(),oldT, phoneNumber.getPhoneType(), oldC, phoneNumber.getComment());
        }

        out.println(table);
    }
}