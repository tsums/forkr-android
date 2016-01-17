package com.tsums.forkr.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.squareup.picasso.Picasso;
import com.tsums.forkr.ForkrApp;
import com.tsums.forkr.R;
import com.tsums.forkr.data.GHUser;
import com.tsums.forkr.fragments.ProspectFragment;
import com.tsums.forkr.network.ForkrNetworkService;
import com.tsums.forkr.util.CircleTransform;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @InjectExtra GHUser user;

    @Inject ForkrNetworkService networkService;
    @Inject Picasso picasso;

    @Bind (R.id.drawer_layout) DrawerLayout drawer;
    @Bind (R.id.toolbar) Toolbar toolbar;
    @Bind (R.id.nav_view) NavigationView navigationView;

//    @Bind (R.id.nav_header_avatar) ImageView avatarView;
//    @Bind (R.id.nav_header_name) TextView nameView;
//    @Bind (R.id.nav_header_username) TextView usernameView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Dart.inject(this);
        ((ForkrApp) getApplication()).getComponent().inject(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Snackbar.make(toolbar, "Welcome " + user.login, Snackbar.LENGTH_LONG).show();

        networkService.getMyUser().enqueue(new Callback<GHUser>() {
            @Override
            public void onResponse (Call<GHUser> call, Response<GHUser> response) {

            }

            @Override
            public void onFailure (Call<GHUser> call, Throwable t) {

            }
        });

        View headerView = navigationView.getHeaderView(0);

        ImageView avatar = ((ImageView) headerView.findViewById(R.id.nav_header_avatar));
        picasso.load(user.avatar_url).transform(new CircleTransform()).into(avatar);

        TextView name = ((TextView) headerView.findViewById(R.id.nav_header_name));
        TextView username = ((TextView) headerView.findViewById(R.id.nav_header_username));

        name.setText(user.name);
        username.setText(user.login);

        ProspectFragment prospectFragment = new ProspectFragment();
        ((ForkrApp) getApplication()).getComponent().inject(prospectFragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame, prospectFragment).commit();

    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings ("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
