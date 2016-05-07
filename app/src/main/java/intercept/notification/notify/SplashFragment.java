package intercept.notification.notify;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class SplashFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Thread splashScreenTimer = new Thread()
        {
            public void run()
            {
                int timer = 0;
                while (timer < 3000)
                {
                    try
                    {
                        sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    timer = timer + 100;
                }

                transition(new MainFragment());
            }
        };

        splashScreenTimer.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        return view;
    }

    /**
     * This method transitions between fragments, without adding the current fragment to the back stack
     *
     * @param fragment - the fragment to be transitioned to
     */
    public void transition(Fragment fragment)
    {
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(getId(), fragment);
        fragTran.commit();
    }

}
