package com.zevnzac.jewishalarmclock;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.support.v7.preference.PreferenceManager;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private SharedPreferences sharedPreferences;
    public static boolean justReturned;
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            preference.setSummary(stringValue);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        justReturned = false;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null || true)
            actionBar.setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new GeneralPreferenceFragment()).commit();
    }

    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        SwitchPreference darkModeSwitch, hourViewSwitch;
        private SharedPreferences sharedPreferences;
        boolean nightMode, hourView;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            nightMode = sharedPreferences.getBoolean("dark_mode_switch", true);
            darkModeSwitch = (SwitchPreference) findPreference("dark_mode_switch");
            final boolean finalNightMode = nightMode;
            darkModeSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (!justReturned){
                        justReturned = true;
                        startActivity(new Intent(getActivity(), MainTabsActivity.class));
                    }
                    return true;
                }});


            hourView = sharedPreferences.getBoolean("hour_view_switch", true);
            hourViewSwitch = (SwitchPreference) findPreference("hour_view_switch");
            hourViewSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    MainTabsActivity.getHourView(getActivity());
                    return true;
                }});
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            return super.onOptionsItemSelected(item);
        }
    }
}