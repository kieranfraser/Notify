package intercept.notification.notify;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;

import intercept.notification.notify.DatabaseHelper.DBManager;
import intercept.notification.notify.DatabaseHelper.DatabaseHelper;

public class CurrentNotificationFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    ListView notificationList;

    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    private final String[] from = new String[] {DatabaseHelper.NOTIFICATION_PACKAGETYPE,DatabaseHelper.NOTIFICATION_SENDER };
    private final int[] to = new int[] { R.id.tv_package, R.id.tv_sender};

    public CurrentNotificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);

        notificationList = (ListView) view.findViewById(R.id.lv_notifications);
        dbManager = new DBManager(getActivity());
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dbManager.fetch();
        Log.d("kk", String.valueOf(cursor.getCount()));
        if(cursor.getCount() != 0){
            adapter = new SimpleCursorAdapter(getActivity(), R.layout.notification_view_record, cursor,
                    from,to,0);
            //adapter.notifyDataSetChanged();
            notificationList.setAdapter(adapter);
        }
        else {
            Toast.makeText(getActivity(), "No saved Notifications", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {

        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }
}
