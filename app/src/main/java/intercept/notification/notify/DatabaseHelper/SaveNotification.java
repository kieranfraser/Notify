package intercept.notification.notify.DatabaseHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by kfraser on 31/10/2015.
 */
public class SaveNotification implements Runnable {
    private Context context;
    private StatusBarNotification sbn;
    private DBManager dbManager;

    public SaveNotification(Context context, StatusBarNotification sbn){
        this.context = context;
        this.sbn = sbn;
        dbManager = new DBManager(context);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Log.d("Kieran Fraser ", "Notification will be saved now");
        String sender, packageType, subject, body;
        CharSequence subjectChar, bodyChar;
        sender = sbn.getNotification().tickerText.toString();
        packageType = sbn.getPackageName().toString();
        subjectChar = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT);
        bodyChar = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_BIG_TEXT);

        if(subjectChar != null){
            subject = subjectChar.toString();
        } else {
            subject = "";
        }
        if(bodyChar != null){
            body = bodyChar.toString();
        } else {
            body = "";
        }

        dbManager.insert(sender,packageType,subject,body);
        Log.d(this.toString(), "Saved Notification [packageType: "+packageType+" sender: "+
        sender+" ]");

        Cursor cursor = dbManager.fetch();
        Log.d("Cursor: ", cursor.toString());
        Log.d("Cursor: ", String.valueOf(cursor.getCount()));
        Log.d("Cursor: ", cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTIFICATION_SENDER)));
    }
}
