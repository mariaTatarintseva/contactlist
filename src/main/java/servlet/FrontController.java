package servlet;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import commandclasses.Command;
import dao.DataAccessObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
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
//        if (!DataAccessObject.isInit()) {
//            DataAccessObject.setInit(true);
//        Properties properties = new Properties();
//    InputStream inputStream =getServletContext().getResourceAsStream("/WEB-INF/properties/contactlist.properties");
//        try {
//            properties.load(inputStream);
//            DataAccessObject.setUSER(properties.getProperty("databaseUser"));
//            DataAccessObject.setPASSWORD(properties.getProperty("databasePassword"));
//            DataAccessObject.setURL(properties.getProperty("databaseURL"));
//            DataAccessObject.setDRIVER(properties.getProperty("databaseDriver"));
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        }
        logger.log(Level.DEBUG, "processRequest()");
        String className;
        if (req.getParameter("command") == null) {
            className = "commandclasses.Error";
            if (ServletFileUpload.isMultipartContent(req)) {
//                try
//                {
//                    ServletFileUpload upload = FileUpload.getUpload();
//                    List items = upload.parseRequest(req);
//                    Iterator iterator = items.iterator();
//                    while (iterator.hasNext())
//                    {
//                        FileItem item = (FileItem) iterator.next();
//
//                        if (item.isFormField()) //your code for getting form fields
//                        {
//                            String name = item.getFieldName();
//                            String value = item.getString();
//                            System.out.println(name+value);
//                            if ("command".equals(name)) {
//                                className = String.format("%s.%s", this.getClass().getPackage().getName(), value);
//                                break;
//                            }
//                        }
//
//                        if (!item.isFormField())
//                        {
//                            //your code for getting multipart
//                        }
//                    }
                className = "commandclasses.FileUpload";

            } else {
            //handle a mistake

            try {
                resp.sendRedirect("error.jsp");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    /**
     * Default constructor. 
     */
    public FrontController() {
        // TODO Auto-generated constructor stub

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
