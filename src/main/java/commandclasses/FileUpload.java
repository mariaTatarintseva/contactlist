package commandclasses;

import com.mysql.jdbc.StringUtils;
import dataclasses.Contact;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import dao.DataAccessObject;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 28.02.15
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class FileUpload extends Command {

    public static String UPLOAD_DIRECTORY;
    private static int  MAX_FILESIZE, MAX_REQUESTSIZE, MEMORY_THRESHOLD;
    private final static Logger logger= LogManager.getLogger(FileUpload.class);
    public static void initProperties(ServletContext context) {

        logger.log(Level.DEBUG, String.format("init: %s", context.getServerInfo()));
        Properties properties = new Properties();
        try {
            InputStream inputStream = context.getResourceAsStream("/WEB-INF/properties/contactlist.properties");
            properties.load(inputStream);

        UPLOAD_DIRECTORY = properties.getProperty("uploadDirectory"); //Command.getResourcesPath(req);
        MAX_FILESIZE = Integer.valueOf(properties.getProperty("maxFileSize"));
        MAX_REQUESTSIZE = Integer.valueOf(properties.getProperty("maxRequestSize"));
        MEMORY_THRESHOLD = Integer.valueOf(properties.getProperty("memoryThreshold"));
        } catch (IOException e) {
        logger.log(Level.ERROR, e.getMessage());
    }
    }
    @Override
    public void process() {
        super.process();
         initProperties(context);
        if (!ServletFileUpload.isMultipartContent(req)) {
            PrintWriter writer = null;
            try {
                writer = res.getWriter();
            } catch (Exception e) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
                try {
                    res.sendRedirect("error.jsp");
                } catch (IOException e1) {
                    logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
                }
            }
            writer.println("Error: Form must has enctype=multipart/form-data.");
        writer.flush();
        return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        String fileName = null;
        String filePath = null;
    //    String filePath2 = null;
        upload.setFileSizeMax(MAX_FILESIZE);
        upload.setSizeMax(MAX_REQUESTSIZE);

        String directory="";// = UPLOAD_DIRECTORY_ATT;
       Integer id = null;
        try {
           @SuppressWarnings("unchecked")
           List<FileItem> formItems = upload.parseRequest(req);
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        String nm = item.getFieldName();
                        String value = item.getString();
                        if("directory".equals(nm)) {
                            directory = value;
                            continue;
                        }
                        if("id".equals(nm)) {
                            id = Integer.valueOf(value);
                            continue;
                        }
                 }
                    }

                String photo = String.valueOf(id);
                String uploadPath =  String.format("%s%s%s%s%s", UPLOAD_DIRECTORY, File.separator, directory, File.separator, photo);
                if (id != null) {
                Contact contact = DataAccessObject.getFromDatabase(id);
                if ("avatars".equals(directory) && !StringUtils.isNullOrEmpty(contact.getPhoto())) {
                    File oldPhoto = (new File(String.format("%s%s%s", UPLOAD_DIRECTORY, File.separator, contact.getPhoto()))).getParentFile();
                    FileUtils.deleteDirectory(oldPhoto);
                }
                }

                File uploadDirB = new File(String.format("%s%s%s", UPLOAD_DIRECTORY, File.separator, directory));
                if (!uploadDirB.exists()) {
                    uploadDirB.mkdir();
                }

                File uploadDir = new File(uploadPath);
           //     File uploadDir2 = new File(uploadPath2);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
             for (FileItem item : formItems) {
             if (!item.isFormField()) {
                 fileName = new File(item.getName()).getName();
                 filePath = String.format("%s%s%s",uploadPath , File.separator, fileName);
                 File storeFile = new File(filePath);
                 if ("avatars".equals(directory)) {
                 req.getSession().setAttribute("photo", String.format("%s/%s/%s", directory, photo, fileName));//String.format("avatars/%s/%s", photo , fileName));
                 }
             item.write(storeFile);
                }
                }
                }
            } catch (Exception ex) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(ex));
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
            }
        try {
            res.sendRedirect(String.format("add.jsp?id=%d", id));
        } catch (IOException e) {
            logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e));
            try {
                res.sendRedirect("error.jsp");
            } catch (IOException e1) {
                logger.log(Level.ERROR, ExceptionUtils.getStackTrace(e1));
            }
        }
    }
}
