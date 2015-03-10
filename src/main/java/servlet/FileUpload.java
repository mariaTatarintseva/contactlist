package servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 28.02.15
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class FileUpload extends Command {

    public static final String UPLOAD_DIRECTORY_PHOTOS = "avatars";    //move to properties!
    public static final String UPLOAD_DIRECTORY_ATT = "attachments";
    private final static Logger logger= LogManager.getLogger(FileUpload.class);
    @Override
    public void process() {
        logger.log(Level.TRACE, "process");
        if (!ServletFileUpload.isMultipartContent(req)) {
        // if not, we stop here
            PrintWriter writer = null;
            try {
                writer = res.getWriter();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            writer.println("Error: Form must has enctype=multipart/form-data.");
        writer.flush();
        return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(Command.limit);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        //ServletFileUpload upload = getUpload();
        String fileName = null;
        String filePath = null;
        // sets maximum size of upload file
        upload.setFileSizeMax(limit);

        // sets maximum size of request (include file + form data)
        upload.setSizeMax(limit);

        String directory = UPLOAD_DIRECTORY_ATT;
        String name = "";
        String comment = "";
        Integer id = null;
        // constructs the directory path to store upload file
        // this path is relative to application's directory
         //System.out.print( + );

        try {
           // parses the request's content to extract file data
           @SuppressWarnings("unchecked")
           List<FileItem> formItems = upload.parseRequest(req);
            if (formItems != null && formItems.size() > 0) {
             // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
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
                    }
                    }

                String photo = String.format("%s%d", LocalDate.now().toString(), LocalDateTime.now().getMillisOfDay());
                //String uploadPath = String.format("%s/%s", directory,  photo);//context.getRealPath("")
                String uploadPath = context.getRealPath("")+ File.separator + directory + File.separator + photo;
                // + File.separator + UPLOAD_DIRECTORY;//context.getRealPath("") + File.separator + UPLOAD_DIRECTORY;// +File.separator + ((Contact)req.getSession().getAttribute("contact")).getId() ;
                // creates the directory if it does not exist

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
             for (FileItem item : formItems) {
             // processes only fields that are not form fields
             if (!item.isFormField()) {

                 fileName = new File(item.getName()).getName();
                 filePath = String.format("%s%s%s",uploadPath , File.separator, fileName);
                 File storeFile = new File(filePath);
                 if ("avatars".equals(directory)) {
                 req.getSession().setAttribute("photo", String.format("avatars/%s/%s", photo , fileName));
                 }

                 // saves the file on disk
             item.write(storeFile);
             req.setAttribute("message",
                "Upload has been done successfully!");
             req.setAttribute("message",
                         "Upload has been done successfully!");
                }
                }
                }
            } catch (Exception ex) {
            req.setAttribute("message",
               "There was an error: " + ex.getMessage());
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
            DataAccessObject.addAttachment(attachment);
        }
        try {
            res.sendRedirect(String.format("add.jsp?id=%d", id));
          //  context.getRequestDispatcher("/add.jsp?id=" + req.getParameter("id") ).forward(req, res);
//        } catch (ServletException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public static ServletFileUpload getUpload() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(Command.limit);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        return upload;
    }
}
