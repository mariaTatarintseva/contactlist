package commandclasses;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 21.02.15
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
public class Command {
    protected ServletContext context;
    protected HttpServletRequest req;
    protected HttpServletResponse res;
    public void init (ServletContext context, HttpServletRequest req, HttpServletResponse res) {
        Logger logger= LogManager.getLogger(this.getClass());
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
        Logger logger= LogManager.getLogger(this.getClass());
        Set<String> keys = req.getParameterMap().keySet();
        StringBuilder stringBuilder = new StringBuilder("process()\n");
        for (String key: keys) {
            stringBuilder.append(key);
            stringBuilder.append(" : ");
            stringBuilder.append(Arrays.toString(req.getParameterMap().get(key)));
        }
        logger.log(Level.DEBUG, stringBuilder.toString());
    }
    public ArrayList<Integer> getListFromRequest() {
        Logger logger= LogManager.getLogger(this.getClass());
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
        Set<String> keys = req.getParameterMap().keySet();
        StringBuilder stringBuilder = new StringBuilder("getListFromRequest()\n");
        for (String key: keys) {
            stringBuilder.append(key);
            stringBuilder.append(" : ");
            stringBuilder.append(Arrays.toString(req.getParameterMap().get(key)));
        }
        logger.log(Level.DEBUG, stringBuilder.toString());
        String[] remove = req.getParameterValues("remove");
        if (remove == null) {
            return list;
        }
        for (String rem: remove) {
              list.add(Integer.valueOf(rem));
        }
        logger.log(Level.TRACE, String.format("getListFromRequest() returning %s", list.toString()));
        } catch (Exception e) {
            try {
                res.sendRedirect("error.jsp?msg=1");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
        }
        return list;
    }
}
