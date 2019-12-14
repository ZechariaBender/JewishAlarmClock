package com.zevnzac.jewishalarmclock;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainTabsActivity extends AppCompatActivity implements
        AlarmsFragment.OnFragmentInteractionListener,
        ZmanimListFragment.OnFragmentInteractionListener  {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static SharedPreferences sharedPreferences;
    public static AppCompatDelegate delegate;
    public static boolean nightMode, _24hourView, refresh, isrunning, locationSet = false;
    private boolean justReturned;
    private static Location location;
    private PlaceDetectionClient mPlaceDetectionClient;
    private String mLastPlaceName;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        justReturned = false;
        if (SettingsActivity.justReturned)
            startActivity(new Intent(this, SettingsActivity.class));
        getDayNightTheme(this);
        super.onCreate(savedInstanceState);
        getHourView(this);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);

        } else {
            setLocation(this);
        }
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        setContentView(R.layout.activity_main_tabs);
        delegate = getDelegate();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 1)
                    mSectionsPagerAdapter.getItem(1).onResume();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
        });

        List<Address> addresses = null;
        Location l = getLocation();
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            addresses = gc.getFromLocation(
                    l.getLatitude(),
                    l.getLongitude(),
                    1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final TextView locationTextView = findViewById(R.id.location_text_view);
        Button locationButton = findViewById(R.id.location_button);
        locationButton.setVisibility(View.GONE);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation(getParent());
                locationTextView.setText(getString(R.string.address_text,
                        getString(R.string.loading),// Name
                        getString(R.string.loading),// Address
                        new Date())); // Timestamp
            }
        });
        if (nightMode) {
            locationTextView.setTextColor(getResources().getColor(R.color.colorPrimaryNightMode));
            locationTextView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_location_on_black_24dp_night_mode),null,null,null);
        }
        String address = "";
        if (addresses != null)
            address = addresses.get(0).getLocality();
        locationTextView.setText(address);
        if (!locationSet)
        {
            recreate();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setLocation(this);
        } else {
            setDefaultLocation();
        }

    }

    public static void setLocation(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);

        } else {
            FusedLocationProviderClient client =
                    LocationServices.getFusedLocationProviderClient(activity);
            client.getLastLocation()
                    .addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            setLocation(task.getResult());
                            locationSet = true;
                        }
                    });
        }
    }

    public static void setLocation(Location l) {
        location = l;
    }

    public static void setDefaultLocation() {
        Location l = new Location("empty provider");
        l.setLatitude(31.800257);
        l.setLongitude(35.221334);
        l.setAltitude(793.5999755859375);
        setLocation(l);
    }

    public static Location getLocation() {
        if (location == null)
            setDefaultLocation();
        return location;
    }

    @Override
    public void onStart() {
        super.onStart();
        getHourView(this);
        isrunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isrunning = false;
    }


    protected void onResume() {
        getDayNightTheme(this);
        super.onResume();
        if (justReturned)
            this.recreate();
        if (refresh) {
            MainTabsActivity.refresh = false;
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
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
        _24hourView = sharedPreferences.getBoolean("hour_view_switch", false);
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
            View rootView = inflater.inflate(R.layout.fragment_add_tabs, container, false);
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
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
