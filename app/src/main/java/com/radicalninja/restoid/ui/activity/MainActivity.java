package com.radicalninja.restoid.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.radicalninja.restoid.R;
import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.db.ConnectionManager;
import com.radicalninja.restoid.data.db.SqlResult;
import com.radicalninja.restoid.data.event.ConnectionDataEvent;
import com.radicalninja.restoid.data.model.Connection;
import com.radicalninja.restoid.data.rest.api.Api;
import com.radicalninja.restoid.ui.fragment.BodyFragment;
import com.radicalninja.restoid.ui.fragment.HeadersFragment;
import com.radicalninja.restoid.ui.fragment.OtherSettingsFragment;
import com.radicalninja.restoid.ui.fragment.ParamsFragment;
import com.radicalninja.restoid.ui.fragment.RequestFragment;
import com.radicalninja.restoid.util.Ln;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private Connection mConnection = new Connection();

    final ConnectionManager mConnectionManager = new ConnectionManager(this);
    Drawer mDrawer;
    FloatingActionButton mActionButton;
    List<Connection> mConnections;
    SectionsPagerAdapter mSectionsPagerAdapter;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab connections
        mConnections = mConnectionManager.getAllConnections();
//        for (int i = 0; i < 5; i++) {
//            Connection con = new Connection();
//            con.setName("Connection "+i);
//            con.setUrl("http://www.google.com/");
//            QueryList query = new QueryList();
//            QueryEntry entry = new QueryEntry();
//            entry.setKey("q");
//            entry.setValue("Con "+i);
//            query.add(entry);
//            con.setQuery(query);
//            mConnections.add(con);
//        }


        // Set up the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // Set up the floating action button
        mActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Unselect currently selected drawer item!
                mConnection = new Connection();
                connectionDataRequestReceived(null);
            }
        });

        // Set up the nav drawer with MaterialDrawer
        final int drawerItemOffset = 2;   // The number of items preceding the mConnections list.
        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .addDrawerItems(
                        new SectionDrawerItem().withName(R.string.drawer_header).setDivider(false),
                        new DividerDrawerItem()
                );
        for (Connection connection : mConnections) {
            drawerBuilder.addDrawerItems(getConnectionDrawerItem(connection));
        }
        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem iDrawerItem) {
                position -= drawerItemOffset;
                if (mConnections.size() > position) {
                    selectItem(position);
                }
                return false;
            }
        });
        mDrawer = drawerBuilder.build();

        // TabLayout
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interceptor, as the callback (listener) for when
            // this tab is selected.
            mTabLayout.addTab(
                    mTabLayout.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)));
        }
        mTabLayout.setupWithViewPager(mViewPager);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_save);
        if (item == null || !mConnection.isChanged()) {
            item.setEnabled(false);
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_send:
                sendRequest();
                return true;
            case R.id.action_save:
                saveRequest();
                invalidateOptionsMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveRequest() {
        mConnection.setIsChanged(false);
        SqlResult result = mConnectionManager.saveConnection(mConnection);
        if (result.equals(SqlResult.CREATED)) {
            mConnections.add(mConnection);
            mDrawer.addItem(getConnectionDrawerItem(mConnection));
            // TODO: Add connection to drawer here.
        }
    }

    private void selectItem(int position) {
        mConnection = mConnections.get(position);
        connectionDataRequestReceived(null);
    }

    private void sendRequest() {
        // Swap over to the first fragment for results display.
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0);
        }
        // If no URL, error toast.
        if (mConnection.getUrl().isEmpty()) {
            Toast.makeText(this, R.string.message_error_nourl, Toast.LENGTH_SHORT).show();
            return;
        }
        // Else, send to API router
        Api api = new Api();
        api.sendRequest(mConnection);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) { }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }

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
                    return ParamsFragment.newInstance();
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

    @Subscribe
    public void connectionChangedEventReceived(ConnectionDataEvent.ChangedAlert alert) {
        invalidateOptionsMenu();
    }

    private PrimaryDrawerItem getConnectionDrawerItem(Connection connection) {
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(connection.getName()).setDescription(connection.getUrl());
        return item;
    }
}
