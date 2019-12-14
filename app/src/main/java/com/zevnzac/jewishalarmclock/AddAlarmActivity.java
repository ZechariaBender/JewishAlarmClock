package com.zevnzac.jewishalarmclock;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.zevnzac.jewishalarmclock.zmanim.NoZman;
import com.zevnzac.jewishalarmclock.zmanim.ZmanHolder;

public class AddAlarmActivity extends AppCompatActivity implements
        RegularFragment.OnFragmentInteractionListener,
        ZmanBasedFragment.OnFragmentInteractionListener {

    private AddAlarmActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private AlarmPreferenceFragment alarmPreferenceFragment;
    private ViewPager mViewPager;
    Button cancelButton;
    Button addAlarmButton;
    AlarmObject alarmObject;
    Toolbar toolbar;
    ScrollView scrollView;
    private boolean addScreen;
    Context context;
    private Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                addAlarm();
            }
        });
        toolbar = findViewById(R.id.add_alarm_toolbar_inner);
        scrollView = findViewById(R.id.scrollView);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.add_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.add_tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        alarmPreferenceFragment = new AlarmPreferenceFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                alarmPreferenceFragment).commit();
        View divider = findViewById(R.id.divider);
        if (MainTabsActivity.nightMode)
            divider.setBackgroundColor(getResources().getColor(R.color.divider_dark));
        else
            divider.setBackgroundColor(getResources().getColor(R.color.divider_light));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setScreen() {

        intent = getIntent();
        String screenType = intent.getStringExtra("screenType");
        if (screenType.contains("addAlarm")) {
            if (toolbar != null)
                toolbar.setTitle("Add Alarm");
            addScreen = true;
            mSectionsPagerAdapter.getItem(0).setHour(6);
            mSectionsPagerAdapter.getItem(0).setMinute(0);
            mSectionsPagerAdapter.getItem(0).setZman(new NoZman());
            mSectionsPagerAdapter.getItem(1).setHour(0);
            mSectionsPagerAdapter.getItem(1).setMinute(0);
            mSectionsPagerAdapter.getItem(1).setZman(new NoZman());
            alarmPreferenceFragment.setRepeat(new AlarmObject.DaysOfWeek(0));
            alarmPreferenceFragment.setRingtone("Silent");
            alarmPreferenceFragment.setLabel("");

        } else if (screenType.contains("edit")) {
            if (toolbar != null)
                toolbar.setTitle("Edit Alarm");
            try {
                alarmObject = (AlarmObject) intent.getExtras().getSerializable("AlarmEdit");
            } catch (Exception e) {
                Log.e("setScreen exception", e.getMessage() + " cause: " + e.getCause());
            }
            if (alarmObject != null) {

                if (alarmObject.getZman().getCode() == 0)
                    mViewPager.setCurrentItem(0);
                else mViewPager.setCurrentItem(1);
                mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()).setHour(alarmObject.getHour());
                mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()).setMinute(alarmObject.getMinute());
                mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()).setZman(alarmObject.getZman());
                alarmPreferenceFragment.setRepeat(alarmObject.getDaysOfWeek());
                alarmPreferenceFragment.setLabel(alarmObject.getLabel());
                alarmPreferenceFragment.setRingtone(alarmObject.getRingtone());
            }

            addScreen = false;
        }
        scrollView.smoothScrollTo(0,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addAlarm() {

        int position = intent.getExtras().getInt("position");

        String name = alarmPreferenceFragment.getLabel();
        int hour = mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()).getHour();
        int minute = mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()).getMinute();
        ZmanHolder zman = new NoZman();
        if (mViewPager.getCurrentItem() == 1)
            zman = mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()).getZman();
        String ringtone = alarmPreferenceFragment.getRingtone();
        AlarmObject.DaysOfWeek daysOfWeek = alarmPreferenceFragment.getRepeat();

        if (addScreen) {


            alarmObject = new AlarmObject(name, hour, minute, 1, zman, ringtone, daysOfWeek);

            intent.putExtra("Alarm", alarmObject);
            setResult(RESULT_OK, intent);

            mSectionsPagerAdapter.getItem(0).setHour(6);
            mSectionsPagerAdapter.getItem(0).setMinute(0);
            mSectionsPagerAdapter.getItem(0).setZman(new NoZman());
            mSectionsPagerAdapter.getItem(1).setHour(0);
            mSectionsPagerAdapter.getItem(1).setMinute(0);
            mSectionsPagerAdapter.getItem(1).setZman(new NoZman());

            finish();

        } else {
            alarmObject.setLabel(name);
            alarmObject.setHour(hour);
            alarmObject.setMinute(minute);
            alarmObject.setEnabled(1);
            alarmObject.setZman(zman);
            alarmObject.setRingtone(ringtone);
            alarmObject.setDaysOfWeek(daysOfWeek);

            Bundle bundle = new Bundle();
            bundle.putSerializable("Alarm", alarmObject);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static AddAlarmActivity.PlaceholderFragment newInstance(int sectionNumber) {
            AddAlarmActivity.PlaceholderFragment fragment = new AddAlarmActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_tabs, container, false);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public TimeSetterFragment getItem(int position) {
            TimeSetterFragment fragment = null;
            switch (position) {
                case 0:
                    fragment = RegularFragment.newInstance(null, null);
                    break;
                case 1:
                    fragment = ZmanBasedFragment.newInstance(null, null);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)


    public static class AlarmPreferenceFragment extends PreferenceFragment {

        private EditTextPreference label;
        private AlarmRingtonePreference ringtone;
        private CheckBoxPreference vibrate;
        private RepeatPreference repeat;


        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            for (int i = 0; i < 10; i++) {
                android.util.Log.d("AlarmPreferenceFragment", "onCreate" + i);
            }
            addPreferencesFromResource(R.xml.alarm_prefs);

            label = (EditTextPreference) findPreference("label");
            label.setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() {
                        public boolean onPreferenceChange(Preference p,
                                                          Object newValue) {
                            // Set the summary based on the new label.
                            p.setSummary((String) newValue);
                            return true;
                        }
                    });
            ringtone = (AlarmRingtonePreference) findPreference("ringtone");
            vibrate = (CheckBoxPreference) findPreference("vibrate");
            repeat = (RepeatPreference) findPreference("repeat");

            AddAlarmActivity activity = (AddAlarmActivity) this.getActivity();
            activity.setScreen();
        }

        public AlarmObject.DaysOfWeek getRepeat() {
            return repeat.getDaysOfWeek();
        }

        public String getLabel() {
            return label.getText();
        }

        public String getRingtone() {
            return ringtone.onRestoreRingtone().toString();
        }

        public void setRepeat(AlarmObject.DaysOfWeek daysOfWeek) {
            repeat.setDaysOfWeek(daysOfWeek);
        }

        public void setLabel(String label) {
            this.label.setText(label);
            this.label.setSummary(label);
        }

        public void setRingtone(String ringtone) {
            this.ringtone.setAlert(Uri.parse(ringtone));
            this.ringtone.setSummary(ringtone);
        }
    }
}