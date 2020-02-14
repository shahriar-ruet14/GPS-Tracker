
package eu.basicairdata.graziano.gpslogger;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("prefColorTheme", "2")));

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.id_toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_settings);

        FragmentSettings wvf = new FragmentSettings();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.id_preferences, wvf);
        ft.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w("myApp", "[#] SettingsActivity.java - onResume()");
    }

    @Override
    public void onPause() {
        Log.w("myApp", "[#] SettingsActivity.java - onPause()");
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
