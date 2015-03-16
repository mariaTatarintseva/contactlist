package view;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 20.02.15
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
import dataclasses.Attachment;
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

public class AttachmentsViewHelper extends SimpleTagSupport{
    private final static Logger logger= LogManager.getLogger(AttachmentsViewHelper.class);

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public void doTag() throws JspException, IOException
    {
        if (id == 0) {
            return;
        }
        ArrayList<Attachment> list = null;
        try {
            list = DataAccessObject.getAttachmentsOf(id);
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getStackTrace());
            return;
        }
        JspWriter out = getJspContext().getOut();
        String table="";
        String oldAttN, oldAttD, oldAttC;
        int i = 0;
        for (Attachment attachment: list) {
            table = String.format("%s<div class=\"row color%d\" id=\"attOld%d\"><div class=\"col c5\"><input type=\"checkbox\" name=\"oldAtt\" value = %d/></div><div class=\"col c20\"><button type=\"button\" class=\"ref\" onclick=\"editAtt(this)\">%s</button></div><div class=\"col c20\">%s</div><div class=\"col c20\">%s</div></div>", table, i%2, attachment.getId(), attachment.getId(), attachment.getName(), "Date", attachment.getComment());
            ++i;
        }
        table = String.format("<div class = \"table\" id=\"tableAtt\">%s</div>", table);
        out.println(table);
    }
}