
package eu.basicairdata.graziano.gpslogger;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentJobProgress extends Fragment {

    ProgressBar progressBar;


    public FragmentJobProgress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_progress, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.id_jobProgressBar);
        progressBar.setProgress(GPSApplication.getInstance().getJobProgress());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Workaround for Nokia Devices, Android 9
        // https://github.com/BasicAirData/GPSLogger/issues/77
        if (EventBus.getDefault().isRegistered(this)) {
            //Log.w("myApp", "[#] FragmentJobProgress.java - EventBus: FragmentJobProgress already registered");
            EventBus.getDefault().unregister(this);
        }

        EventBus.getDefault().register(this);
        Update();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onEvent(Short msg) {
        if (msg == EventBusMSG.UPDATE_JOB_PROGRESS) {
            Update();
        }
    }

    public void Update() {
        if (isAdded()) {
            progressBar.setProgress((GPSApplication.getInstance().getJobProgress() == 1000) || (GPSApplication.getInstance().getJobsPending() == 0 ) ? 0 : GPSApplication.getInstance().getJobProgress());
        }
    }
}
