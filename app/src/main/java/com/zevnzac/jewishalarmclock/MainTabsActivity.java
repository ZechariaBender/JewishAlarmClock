package com.zevnzac.jewishalarmclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainTabsActivity extends AppCompatActivity implements
        AlarmsFragment.OnFragmentInteractionListener,
        ZmanimListFragment.OnFragmentInteractionListener,
        TimerFragment.OnFragmentInteractionListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private AlarmList list;
    private ViewPager mViewPager;
    private static SharedPreferences sharedPreferences;
    public static AppCompatDelegate delegate;
    public static boolean nightMode;
    public static boolean hourView;
    private boolean justReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        justReturned = false;
        if (SettingsActivity.justReturned)
            startActivity(new Intent(this, SettingsActivity.class));
        getDayNightTheme(this);
        super.onCreate(savedInstanceState);
        getHourView(this);
        setContentView(R.layout.activity_main_tabs);
        delegate = getDelegate();
        list = AlarmList.get();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    protected void onStart(){
//        getDayNightTheme(this);
        super.onStart();
        getHourView(this);
        if (list.getList() == null)
            loadAlarmList();
    }

    protected void onResume() {
        getDayNightTheme(this);
        super.onResume();
        if (justReturned)
            this.recreate();
    }

    public static void getDayNightTheme(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        nightMode = sharedPreferences.getBoolean("dark_mode_switch", true);
        if (nightMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    public static void getHourView(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        hourView = sharedPreferences.getBoolean("hour_view_switch", false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_tabs, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                justReturned = true;
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_exit:
                System.exit(0);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_tabs, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = AlarmsFragment.newInstance(null,null);
                    break;
                case 1:
                    fragment = ZmanimListFragment.newInstance(null,null);
                    break;
                case 2:
                    fragment = TimerFragment.newInstance(null,null);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    private void loadAlarmList() {
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("alarm list", null);
//        Type type = new TypeToken<ArrayList<AlarmObject>>() {}.getType();
//        ArrayList<String> stringList = gson.fromJson(json, type);
//        if (stringList == null) {
//            stringList = new ArrayList<>();
//        }
        ArrayList<AlarmObject> alarmList = new ArrayList<>();
//        for (String string : stringList)
//            alarmList.add(new AlarmObject(string));
        list.setList(alarmList);
    }

//    private void saveAlarmList() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(list.getStringList());
//        editor.putString("alarm list", json);
//    }
}
