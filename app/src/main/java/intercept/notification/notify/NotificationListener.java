package intercept.notification.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.Toast;

import intercept.notification.notify.DatabaseHelper.SaveNotification;

/**
 * Created by kfraser on 15/10/2015.
 */
public class NotificationListener extends NotificationListenerService {

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        NotifySender nSender = new NotifySender();
        IntentFilter filter = new IntentFilter();
        filter.addAction("intercept.notification.notify.NOTIFY_SERVICE");
        registerReceiver(nSender, filter);
    }

    @Override
    public StatusBarNotification[] getActiveNotifications() {
        return super.getActiveNotifications();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.d("Kieran", "Notification posted");
        Log.d("K", sbn.getNotification().toString());
        Log.d("Ticker: ", sbn.getNotification().tickerText.toString());
        String body = null;
        CharSequence bigText = (CharSequence) sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT);
        if(bigText != null){
            body = bigText.toString();
        }
        String biggerBody = null;
        CharSequence biggerText = (CharSequence) sbn.getNotification().extras.getCharSequence(Notification.EXTRA_BIG_TEXT);
        if(biggerText != null){
            biggerBody = biggerText.toString();
        }

        Log.d("Ticker body: ", body);
        Log.d("Email body hopefully: ", biggerBody);
        Log.d("ARGB: ", String.valueOf(sbn.getNotification().ledARGB));
        Log.d("Package: ", sbn.getPackageName().toString());

        new Thread(new SaveNotification(this, sbn)).start();
        //this.cancelNotification(sbn.getKey());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d("Kieran", "Notification Removed");
    }

    private class NotifySender extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("list")){

                Intent i1 = new  Intent("intercept.notification.notify.NOTIFY");

                i1.putExtra("notification_name","=====================");

                sendBroadcast(i1);

                int i=1;

                for (StatusBarNotification sbn : NotificationListener.this.getActiveNotifications()) {

                    Intent i2 = new  Intent("intercept.notification.notify.NOTIFY");

                    i2.putExtra("notification_name",i +" " + sbn.getPackageName() + "n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("cintercept.notification.notify.NOTIFY");
                i3.putExtra("notification_name","===== Notification List ====");
                sendBroadcast(i3);
            }

        }
    }
}
