package intercept.notification.notify;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.ibm.watson.developer_cloud.relationship_extraction.v1.RelationshipExtraction;
import com.ibm.watson.developer_cloud.relationship_extraction.v1.model.RelationshipExtractionDataset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import intercept.notification.notify.DatabaseHelper.DatabaseHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MainFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content

        String[] values = { "Current Notifications",
                            "Export Database",
                            "Button 3"};

        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        //new Persona().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {


            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }

            switch (position) {
                case 0:
                    Toast.makeText(getActivity(), "No. 1", Toast.LENGTH_LONG).show();
                    transition(new CurrentNotificationFragment());
                    break;
                case 1:
                    Toast.makeText(getActivity(), "No. 2", Toast.LENGTH_LONG).show();
                    exportDB();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "No. 3", Toast.LENGTH_LONG).show();
                    break;
            }
    }

    private void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "intercept.notification.notify" +"/databases/"+DatabaseHelper.DB_NAME;
        String backupDBPath = DatabaseHelper.DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(getActivity(), "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public void transition(Fragment fragment)
    {
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction().addToBackStack(null);
        fragTran.replace(getId(), fragment);
        fragTran.commit();
    }

    private class Persona extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("K", "1");


            // Relationship extraction example
            RelationshipExtraction service = new RelationshipExtraction();
            service.setUsernameAndPassword("102e0231-c1b2-4d50-a920-14910a508687", "gc3Y9rucZPLJ");

            service.setDataset(RelationshipExtractionDataset.ENGLISH_NEWS);
            String response = service.extract("Kieran Fraser is the most talented person in Dublin");
            Log.d("Watson: ", response);

            PersonalityInsights service2 = new PersonalityInsights();
            service2.setUsernameAndPassword("fcf16a06-c953-4f8e-97c9-e1fbe1bb9e9a", "LHgwS2ojPgMZ");

// Demo content from Moby Dick by Hermann Melville (Chapter 1)
            String text = "Call me Ishmael. Some years ago-never mind how long precisely-having "
                    + "little or no money in my purse, and nothing particular to interest me on shore, "
                    + "I thought I would sail about a little and see the watery part of the world. "
                    + "It is a way I have of driving off the spleen and regulating the circulation. "
                    + "Whenever I find myself growing grim about the mouth; whenever it is a damp, "
                    + "drizzly November in my soul; whenever I find myself involuntarily pausing before "
                    + "coffin warehouses, and bringing up the rear of every funeral I meet; and especially "
                    + "whenever my hypos get such an upper hand of me, that it requires a strong moral "
                    + "principle to prevent me from deliberately stepping into the street, and methodically "
                    + "knocking people's hats off-then, I account it high time to get to sea as soon as I can. "
                    + "This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself "
                    + "upon his sword; I quietly take to the ship. There is nothing surprising in this. "
                    + "If they but knew it, almost all men in their degree, some time or other, cherish "
                    + "very nearly the same feelings towards the ocean with me. There now is your insular "
                    + "city of the Manhattoes, belted round by wharves as Indian isles by coral reefs-commerce surrounds "
                    + "it with her surf. Right and left, the streets take you waterward.";

            Profile profile = service2.getProfile(text);
            Log.d("PI: ", profile.toString());

            return null;
        }

    }

}
