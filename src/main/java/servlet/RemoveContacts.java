package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 22.02.15
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public class RemoveContacts extends Command {
    private final static Logger logger= LogManager.getLogger(RemoveContacts.class);
    @Override
    public void process() {
        ArrayList<Integer> toRemove = getListFromRequest();
        DataAccessObject.deleteFromDatabase(toRemove);
        try {
            res.sendRedirect("index.jsp");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
