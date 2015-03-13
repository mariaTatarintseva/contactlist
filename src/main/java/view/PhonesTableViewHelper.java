package view;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 20.02.15
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.DataAccessObject;
import dataclasses.PhoneNumber;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.sql.SQLException;
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
        ArrayList<PhoneNumber> list = null;
        try {
            list = DataAccessObject.getPhones(id);
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        }
        JspWriter out = getJspContext().getOut();
        String table="";
        String oldN, oldT, oldC;
        int i = 0;
        for (PhoneNumber phoneNumber: list) {
            oldN = String.format("oldN%d", phoneNumber.getId());
            oldT = String.format("oldT%d", phoneNumber.getId());
            oldC = String.format("oldC%d", phoneNumber.getId());
            table = String.format("%s<div class=\"row color%d\"><div class=\"col c5\"><input type=\"checkbox\" name=\"oldPhone\" value = %d></div><div class=\"col c20\" id=\"%s\"><a href=\"phone.jsp?id=%d\">%s</a></div><div class=\"col c20\" id=\"%s\">%s</div><div class=\"col c20\" id=\"%s\">%s</div></div>", table, i%2, phoneNumber.getId(), oldN, phoneNumber.getId(), phoneNumber.toString(),oldT, phoneNumber.getPhoneType(), oldC, phoneNumber.getComment());
            ++i;
        }
        table = String.format("<div class = \"table\" id=\"table\">%s</div>", table);
        out.println(table);
    }
}