package com.radicalninja.restoid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ConnectionDataEvent;
import com.radicalninja.restoid.data.model.Connection;
import com.radicalninja.restoid.data.model.HeaderEntry;
import com.radicalninja.restoid.data.model.HeaderList;
import com.radicalninja.restoid.data.model.UrlEntry;
import com.radicalninja.restoid.data.rest.api.Api;
import com.radicalninja.restoid.data.rest.interceptor.RestInterceptor;
import com.radicalninja.restoid.ui.fragment.BodyFragment;
import com.radicalninja.restoid.ui.fragment.HeadersFragment;
import com.radicalninja.restoid.ui.fragment.OtherSettingsFragment;
import com.radicalninja.restoid.ui.fragment.RequestFragment;
import com.radicalninja.restoid.util.Ln;
import com.squareup.otto.Subscribe;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    private Connection mConnection = new Connection();

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interceptor, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getOttoBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.getOttoBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_send:
                // Swap over to the first fragment...
                if (mViewPager.getCurrentItem() != 0) {
                    mViewPager.setCurrentItem(0);
                }
                UrlEntry entry = new UrlEntry(mConnection.getUrl());
                App.getEndpoint().setUrl(entry.getUrlBase());
                // Send the enabled headers into the Interceptor.
                RestInterceptor interceptor = RestInterceptor.getInstance();
                interceptor.removeAllHeaders();
                for (HeaderEntry header : mConnection.getHeaders()) {
                    interceptor.addHeader(header.getKey(), header.getValue());
                }
                Api api = new Api();
                switch (mConnection.getRequestType()) {
                    case GET:
                        api.submitGET(entry.getUrlPath());
                        break;
                    case POST:
                        api.submitPOST(entry.getUrlPath());
                        break;
                    case PATCH:
                        api.submitPATCH(entry.getUrlPath());
                        break;
                    case DELETE:
                        api.submitDELETE(entry.getUrlPath());
                        break;
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return HeadersFragment.newInstance();
                case 2:
                case 3:
                    return BodyFragment.newInstance();
                case 4:
                    return OtherSettingsFragment.newInstance();
                case 0:
                default:
                    return RequestFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5).toUpperCase(l);
            }
            return null;
        }
    }

    @Subscribe
    public void connectionDataRequestReceived(ConnectionDataEvent.ReadRequest request) {
        Ln.i("ReadRequest received.");
        App.getOttoBus().post(new ConnectionDataEvent.ReadResponse(mConnection));
    }

}
