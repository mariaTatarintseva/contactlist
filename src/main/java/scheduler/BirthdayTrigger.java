package scheduler;

import org.quartz.SimpleTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 15.03.15
 * Time: 8:52
 * To change this template use File | Settings | File Templates.
 */
public class BirthdayTrigger extends SimpleTrigger {
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    private String sender;
    private String senderPassword;
    private String user;
    private String userPassword;

    public String getReminderPattern() {
        return reminderPattern;
    }

    public void setReminderPattern(String reminderPattern) {
        this.reminderPattern = reminderPattern;
    }

    private String reminderPattern;

    public String getUserShowName() {
        return userShowName;
    }

    public void setUserShowName(String userShowName) {
        this.userShowName = userShowName;
    }

    private String userShowName;
}
