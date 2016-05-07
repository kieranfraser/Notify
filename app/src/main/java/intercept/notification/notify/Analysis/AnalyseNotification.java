package intercept.notification.notify.Analysis;

import android.content.Context;
import android.service.notification.StatusBarNotification;

/**
 * Created by kfraser on 01/11/2015.
 */
public class AnalyseNotification implements Runnable {

    private Context context;
    private StatusBarNotification sbn;

    public AnalyseNotification(Context context, StatusBarNotification sbn) {
        this.sbn = sbn;
        this.context = context;
    }

    @Override
    public void run() {

    }
}
