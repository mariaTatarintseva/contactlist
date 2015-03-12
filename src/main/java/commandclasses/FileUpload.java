package commandclasses;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import dao.DataAccessObject;
import dataclasses.Attachment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
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

    private String UPLOAD_DIRECTORY;
    private final static Logger logger= LogManager.getLogger(FileUpload.class);
    @Override
    public void process() {
        logger.log(Level.DEBUG, "process");

        Properties properties = new Properties();
        InputStream inputStream = context.getResourceAsStream("/WEB-INF/properties/contactlist.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            try {
                res.sendRedirect("error.jsp");
                return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.getMessage());
            }
        }
        UPLOAD_DIRECTORY = properties.getProperty("uploadDirectory");
        final int MAX_FILESIZE = Integer.valueOf(properties.getProperty("maxFileSize"));
        final int MAX_REQUESTSIZE = Integer.valueOf(properties.getProperty("maxRequestSize"));
        final int MEMORY_THRESHOLD = Integer.valueOf(properties.getProperty("memoryThreshold"));
        if (!ServletFileUpload.isMultipartContent(req)) {
            PrintWriter writer = null;
            try {
                writer = res.getWriter();
            } catch (IOException e) {
                logger.log(Level.ERROR, e.getMessage());
                try {
                    res.sendRedirect("error.jsp");
                    return;
                } catch (IOException e1) {
                    logger.log(Level.ERROR, e1.getMessage());
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
        upload.setFileSizeMax(MAX_FILESIZE);
        upload.setSizeMax(MAX_REQUESTSIZE);

        String directory="";// = UPLOAD_DIRECTORY_ATT;
        String name = "";
        String comment = "";
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
                        if("name".equals(nm)) {
                            name = value;
                            continue;
                        }
                        if("comment".equals(nm)) {
                            comment = value;
                            continue;
                        }
                    }
                    }

                String photo = String.format("%s%d", LocalDate.now().toString(), LocalDateTime.now().getMillisOfDay());
                String uploadPath =  UPLOAD_DIRECTORY + File.separator + directory + "-" + photo;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
             for (FileItem item : formItems) {
             if (!item.isFormField()) {
                 fileName = new File(item.getName()).getName();
                 filePath = String.format("%s%s%s",uploadPath , File.separator, fileName);
                 File storeFile = new File(filePath);
                 if ("avatars".equals(directory)) {
                 req.getSession().setAttribute("photo", filePath);//String.format("avatars/%s/%s", photo , fileName));
                 }
             item.write(storeFile);
                }
                }
                }
            } catch (Exception ex) {
            logger.log(Level.ERROR, ex.getMessage());
            try {
                res.sendRedirect("error.jsp");
                return;
            } catch (IOException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
            }

        if ("avatars".equals(directory)) {
             req.setAttribute("photo", fileName);
        }  else if ("attachments".equals(directory)) {
            Attachment attachment = new Attachment();
            attachment.setName(name);
            attachment.setContactId(id);
            attachment.setPath(filePath);
            attachment.setDate(DateTime.now());
            attachment.setComment(comment);
            try {
                DataAccessObject.addAttachment(attachment);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e.getMessage());
                try {
                    res.sendRedirect("error.jsp");
                    return;
                } catch (IOException e1) {
                    logger.log(Level.ERROR, e1.getMessage());
                }
            } catch (ClassNotFoundException e) {
                logger.log(Level.ERROR, e.getMessage());
                try {
                    res.sendRedirect("error.jsp");
                    return;
                } catch (IOException e1) {
                    logger.log(Level.ERROR, e1.getMessage());
                }
            }
        }
        try {
            res.sendRedirect(String.format("add.jsp?id=%d", id));
        } catch (IOException e) {
            try {
                res.sendRedirect("error.jsp");
                return;
            } catch (IOException e1) {
                logger.log(Level.ERROR, e1.getMessage());
            }
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
