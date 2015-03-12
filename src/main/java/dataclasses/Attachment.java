package dataclasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 07.03.15
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class Attachment {
    private final static Logger logger= LogManager.getLogger(Attachment.class);
    private Integer id;
    private String name;
    private Integer contactId;
    private String path;
    private DateTime date;
    private String comment;
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }





}
