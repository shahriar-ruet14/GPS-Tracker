

package eu.basicairdata.graziano.gpslogger;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentAboutDialog extends DialogFragment {

    TextView TVVersion;
    TextView TVDescription;

    //@SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder createAboutAlert = new AlertDialog.Builder(getActivity(), R.style.MyMaterialThemeAbout);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_about_dialog, null);

        final GPSApplication app = GPSApplication.getInstance();

        TVVersion = (TextView) view.findViewById(R.id.id_about_textView_Version);
        String versionName = BuildConfig.VERSION_NAME;
        TVVersion.setText(getString(R.string.about_version) + " " + versionName);

        TVDescription = (TextView) view.findViewById(R.id.id_about_textView_description);
        switch (app.getAppOrigin()) {
            case GPSApplication.APP_ORIGIN_NOT_SPECIFIED:
                TVDescription.setText(getString(R.string.about_description));
                break;
            case GPSApplication.APP_ORIGIN_GOOGLE_PLAY_STORE:
                TVDescription.setText(getString(R.string.about_description_googleplaystore));
                break;
        }

        createAboutAlert.setView(view).setPositiveButton(R.string.about_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {}
            });

        if (app.getAppOrigin() != GPSApplication.APP_ORIGIN_NOT_SPECIFIED) {
            createAboutAlert.setView(view).setNegativeButton(R.string.about_rate_this_app, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    if (app.getAppOrigin() == GPSApplication.APP_ORIGIN_GOOGLE_PLAY_STORE) {
                        boolean marketfailed = false;
                        try {
                            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
                        } catch (Throwable e) {
                            // Unable to start the Google Play app for rating
                            marketfailed = true;
                        }
                        if (marketfailed) {
                            try {               // Try with the web browser
                                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                            } catch (Throwable e) {
                                // Unable to start also the browser for rating
                                Toast.makeText(getContext(), getString(R.string.about_unable_to_rate), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }

        return createAboutAlert.create();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}