package intercept.notification.notify.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.sql.SQLException;

/**
 * Created by kfraser on 31/10/2015.
 */
public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context context){
        this.context = context;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void insert(String sender, String packageType, String subject, String body){
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.NOTIFICATION_BODY, body);
        contentValue.put(DatabaseHelper.NOTIFICATION_SUBJECT, subject);
        contentValue.put(DatabaseHelper.NOTIFICATION_PACKAGETYPE, packageType);
        contentValue.put(DatabaseHelper.NOTIFICATION_SENDER, sender);

        database.insert(DatabaseHelper.TABLE_NOTIFICATIONS, null, contentValue);
   }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper.NOTIFICATION_ID, DatabaseHelper.NOTIFICATION_SENDER,
                DatabaseHelper.NOTIFICATION_PACKAGETYPE, DatabaseHelper.NOTIFICATION_SUBJECT,
                DatabaseHelper.NOTIFICATION_BODY, DatabaseHelper.NOTIFICATION_DATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NOTIFICATIONS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(int id, String sender, String packageType, String subject, String body) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NOTIFICATION_SENDER, sender);
        contentValues.put(DatabaseHelper.NOTIFICATION_PACKAGETYPE, packageType);
        contentValues.put(DatabaseHelper.NOTIFICATION_SUBJECT, subject);
        contentValues.put(DatabaseHelper.NOTIFICATION_BODY, body);
        int i = database.update(DatabaseHelper.TABLE_NOTIFICATIONS, contentValues, DatabaseHelper.NOTIFICATION_ID + "=" + id,null);
        return i;
    }

    public void delete(long id) {
        database.delete(DatabaseHelper.TABLE_NOTIFICATIONS, DatabaseHelper.NOTIFICATION_ID + "=" + id, null);
    }
}