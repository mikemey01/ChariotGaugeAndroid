package com.chariotinstruments.chariotgauge;


import java.util.prefs.Preferences;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {

    View root;
    PreferenceScreen CGPreferenceScreen;
    Preference TraceBlueToothPreference;

    private final boolean ENABLE_TRACE_BLUE_TOOTH = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        CGPreferenceScreen = getPreferenceScreen();

        // Setup the click listener for the "Buy Hardware Controller" preference
        getPreferenceManager()
        .findPreference("go_to_site")
        .setOnPreferenceClickListener(
                new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.chariotgauge.com/product/controller/"));
                        startActivity(intent);
                        return true;
                    }
                });

        // Blue tooth trace preference. Setup the listener if ENABLE_TRACE_BLUE_TOOTH, else remove
        // it from the preference screen.
        TraceBlueToothPreference = getPreferenceManager().
                findPreference("blue_tooth_trace_selection");

        if ( ENABLE_TRACE_BLUE_TOOTH ){
            TraceBlueToothPreference
                    .setOnPreferenceClickListener(
                            new OnPreferenceClickListener() {
                                public boolean onPreferenceClick(Preference preference) {
                                    startActivity(new Intent(getApplicationContext(), BlueToothTrace.class));
                                    return true;
                                }
                            });
        }
        else {
            CGPreferenceScreen.removePreference(TraceBlueToothPreference);
        }
    }
}
