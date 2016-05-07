package intercept.notification.notify.DatabaseObjects;

import java.util.Date;

/**
 * Created by kfraser on 31/10/2015.
 */
public class Notification {

    private int id;
    private String sender;
    private String packageType;
    private String subject;
    private String body;
    private Date dateTime;

    public Notification(String sender, String packageType, String subject, String body, Date dateTime){
        this.sender = sender;
        this.packageType = packageType;
        this.subject = subject;
        this.body = body;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString(){
        return "Notification [id: "+id+" Sender: "+sender+" Package: "+packageType+
                " DateTime: "+dateTime.toString()+"]";
    }
}
