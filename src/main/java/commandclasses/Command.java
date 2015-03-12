package commandclasses;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 21.02.15
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
public class Command {
    private final static Logger logger= LogManager.getLogger(Command.class);
    protected ServletContext context;
    protected HttpServletRequest req;
    protected HttpServletResponse res;
    public void init (ServletContext context, HttpServletRequest req, HttpServletResponse res) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.ERROR, e.getStackTrace());
        }
        this.context = context;
        this.req = req;
        this.res = res;
    }
    public void process() {
        logger.log(Level.DEBUG, "process()");

    }
    public ArrayList<Integer> getListFromRequest() {
        logger.log(Level.DEBUG, "getListFromRequest()");
        ArrayList<Integer> list = new ArrayList<Integer>();
        String[] remove = req.getParameterValues("remove");
        if (remove == null) {
            return list;
        }
        for (String rem: remove) {
              list.add(Integer.valueOf(rem));
        }
        logger.log(Level.TRACE, String.format("getListFromRequest() returning %s", list.toString()));
        return list;
    }
}
