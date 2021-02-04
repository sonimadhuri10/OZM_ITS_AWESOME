package com.example.ozmapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ozmapp.R;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.fragment.Aboutus_fragment;
import com.example.ozmapp.fragment.ContactUs_fragement;
import com.example.ozmapp.fragment.Dashboard_fragment;
import com.example.ozmapp.fragment.EditProfile_fragment;
import com.example.ozmapp.fragment.Feedback_fragment;
import com.example.ozmapp.fragment.cashback_fragemnt;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    static int coutn1 = 0;
    SessionManagment sd;
    ConnectionDetector cd;
   // APIInterface apiInterface;
    TextView tvName, tvEmail, tvMobile;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // apiInterface = APIClient.getClient().create(APIInterface.class);
        sd = new SessionManagment(this);
        cd = new ConnectionDetector(this);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toolbar.setNavigationIcon(R.drawable.menuicontoolbar);
        TextView header = findViewById(R.id.pg2_header_footer);
        header.setText("OZM");

        // HEADER SECTION
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_newly__updated_);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        tvEmail = (TextView) headerView.findViewById(R.id.tvEmail);
        tvMobile = (TextView) headerView.findViewById(R.id.tvMobile);
        circleImageView = (CircleImageView) headerView.findViewById(R.id.imgUser);

        tvName.setText(sd.getNAME());
        tvEmail.setText(sd.getEMAIL());
        tvMobile.setText("+91-"+sd.getMobile());

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new Dashboard_fragment()).commit();


        circleImageView.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        if (coutn1 == 1) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction.replace(R.id.container, new Dashboard_fragment());
            fragmentTransaction.commit();
            coutn1++;
        } else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackCount == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DrawerActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(DrawerActivity.this).inflate(R.layout.custom_dialog_layout, viewGroup, false);

                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.show();

                TextView tvYes , tvNo ,tvheader ;
                tvYes=(TextView)dialogView.findViewById(R.id.tvYes);
                tvNo=(TextView)dialogView.findViewById(R.id.tvNo);
                tvheader=(TextView)dialogView.findViewById(R.id.tvHeader);

                tvheader.setText("Are you sure you want to exit this app ?");
                tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent start = new Intent(Intent.ACTION_MAIN);
                        start.addCategory(Intent.CATEGORY_HOME);
                        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(start);
                        finish();
                    }
                });

                tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            } else {
                super.onBackPressed();
            }

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_home) {
            drawer.closeDrawer(Gravity.LEFT);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction1.replace(R.id.container, new Dashboard_fragment());
            fragmentTransaction1.commit();
        }
       else if (id == R.id.nav_edit) {
            drawer.closeDrawer(Gravity.LEFT);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction.replace(R.id.container, new EditProfile_fragment());
            fragmentTransaction.commit();
        }else if(id == R.id.nav_feedback){
            drawer.closeDrawer(Gravity.LEFT);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction1.replace(R.id.container, new Feedback_fragment());
            fragmentTransaction1.commit();
        } else if(id == R.id.nav_cashback){
            drawer.closeDrawer(Gravity.LEFT);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction1.replace(R.id.container, new cashback_fragemnt());
            fragmentTransaction1.commit();
        } else if (id == R.id.nav_share) {
            drawer.closeDrawer(Gravity.LEFT);
             try {
                 Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                 shareIntent.setType("text/plain");
                 shareIntent.putExtra(Intent.EXTRA_SUBJECT,"OZM ITS AWESOME");
                 String app_url = "\n\nDownload the OZM ITS AWESOME app by given link - \n\n"+"https://play.google.com/store/apps/details?id=com.snapchat.android";
                 shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                 startActivity(Intent.createChooser(shareIntent, "Share via"));
             } catch(Exception e) {
                 //e.toString();
             }
         } else if (id == R.id.nav_about) {
            drawer.closeDrawer(Gravity.LEFT);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction1.replace(R.id.container, new Aboutus_fragment());
            fragmentTransaction1.commit();
        }else if (id == R.id.nav_contact) {
            drawer.closeDrawer(Gravity.LEFT);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.setCustomAnimations(R.anim.fadein, R.anim.fade_out);
            fragmentTransaction1.replace(R.id.container, new ContactUs_fragement());
            fragmentTransaction1.commit();
        }else if (id == R.id.nav_logout) {
            drawer.closeDrawer(Gravity.LEFT);
             logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            default:

                break;

        }
    }

    public  void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DrawerActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(DrawerActivity.this).inflate(R.layout.custom_dialog_layout, viewGroup, false);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        TextView tvYes , tvNo ,tvheader ;
        tvYes=(TextView)dialogView.findViewById(R.id.tvYes);
        tvNo=(TextView)dialogView.findViewById(R.id.tvNo);
        tvheader=(TextView)dialogView.findViewById(R.id.tvHeader);

        tvheader.setText("Are you sure you want to logout this app ?");
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DrawerActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                sd.setLOGIN_STATUS("false");
                sd.setKEY_APITOKEN("");
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                startActivity(i);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                System.exit(0);
            }
        });

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

}

