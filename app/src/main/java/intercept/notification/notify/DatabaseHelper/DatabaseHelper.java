package intercept.notification.notify.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;

/**
 * Created by kfraser on 31/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    public static final String DB_NAME = "NotificationDB";
    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String NOTIFICATION_ID = "_id";
    public static final String NOTIFICATION_SENDER = "sender";
    public static final String NOTIFICATION_PACKAGETYPE = "package";
    public static final String NOTIFICATION_SUBJECT = "subject";
    public static final String NOTIFICATION_BODY = "body";
    public static final String NOTIFICATION_DATE = "dateTime";

    private static final String CREATE_TABLE = "create table " + TABLE_NOTIFICATIONS +
            "("+ NOTIFICATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NOTIFICATION_SENDER + " TEXT NOT NULL, "+
            NOTIFICATION_PACKAGETYPE + " TEXT NOT NULL, "+
            NOTIFICATION_SUBJECT + " TEXT NOT NULL, "+
            NOTIFICATION_BODY + " TEXT NOT NULL, "+
            NOTIFICATION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d("oncreate database: ", CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS "+ TABLE_NOTIFICATIONS);
        onCreate(db);
        Log.d("onupgrade database: ", "");
    }
}
