package servlet;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import commandclasses.Command;
import dao.DataAccessObject;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import scheduler.BirthdayJob;
import scheduler.BirthdayTrigger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final static Logger logger= LogManager.getLogger(FrontController.class);
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
        }
        logger.log(Level.DEBUG, "processRequest()");
        String className;
        if (req.getParameter("command") == null) {
            className = "commandclasses.Error";
            if (ServletFileUpload.isMultipartContent(req)) {
                className = "commandclasses.FileUpload";
            } else {
            //handle a mistake

            try {
                resp.sendRedirect("error.jsp");
            } catch (IOException e) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            }

            }
        } else {
            className = String.format("commandclasses.%s", req.getParameter("command"));
            //this.getClass().getClassLoader().getResource("servlet")
        }
       // System.out.print(className);
        try {
            Command command = (Command)Class.forName(className).getConstructor().newInstance();
            command.init(getServletContext(), req, resp);
            command.process();
        } catch (Exception e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
        }


    }

    /**
     * Default constructor. 
     */
    public FrontController() {
        // TODO Auto-generated constructor stub

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);    //To change body of overridden methods use File | Settings | File Templates.
        JobDetail job = new JobDetail();
        job.setName("birthdayJobName");
        job.setJobClass(BirthdayJob.class);

        BirthdayTrigger trigger = new BirthdayTrigger();
        trigger.setStartTime(new Date(System.currentTimeMillis() + 24L*60L*60L*1000L));
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setRepeatInterval(24L*60L*60L*1000L);     //24L * 60L *
        trigger.setName("birthdayTrigger");

        Properties properties = new Properties();
        InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/properties/contactlist.properties");
        try {
            properties.load(inputStream);
            trigger.setSender(properties.getProperty("reminderEmail"));
            trigger.setSenderPassword(properties.getProperty("reminderEmailPassword"));
            trigger.setUser(properties.getProperty("userEmail"));
            trigger.setUserPassword(properties.getProperty("userEmailPassword"));
            trigger.setUserShowName(properties.getProperty("userShowName"));
            trigger.setReminderPattern(properties.getProperty("reminderPattern"));
        } catch (IOException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
        }


        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.log(Level.ERROR, ExceptionUtils.getRootCauseMessage(e));
        }

    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        processRequest(request, response);

	}

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        processRequest(request, response);
	}



}
