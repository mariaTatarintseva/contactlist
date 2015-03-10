package servlet;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Iterator;

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
    public static final int limit = 1000000;
    public void init (ServletContext context, HttpServletRequest req, HttpServletResponse res) {
        this.context = context;
        this.req = req;
        this.res = res;
    }
    public void process() {

    }
    public ArrayList<Integer> getListFromRequest() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        String[] remove = req.getParameterValues("remove");
        for (String rem: remove) {
              list.add(Integer.valueOf(rem));
        }
        return list;
    }
}
