package com.luisgonzalez.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.dispersiondigital.demo.FRAGMENTS.mRegisterFragment;
import com.dispersiondigital.demo.FRAGMENTS.mAboutFragmentView;
import com.dispersiondigital.demo.FRAGMENTS.mHomeFragmentView;
import com.dispersiondigital.smartclaritydemo.R;

public class MainActivity extends AppCompatActivity {
    private static String FLAG_CURRENT_VIEW = MainActivity.class.getName();
    private TextView mTextMessage;
    private FragmentManager fragmentManager;
    private Toolbar mActionBarToolbar;
    private String titulo ="";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    switchFragment(mHomeFragmentView.newInstance("",""));
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    switchFragment(mRegisterFragment.newInstance());
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(mAboutFragmentView.newInstance());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        /*try {
            JSONObject object = new JSONObject(Myapp.getInstance().getJsonStringUser());
            titulo =object.getJSONArray("auth").getJSONObject(0).getString("NAME_PROFILE");
            Log.d(FLAG_CURRENT_VIEW,"Response is: "+ object);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        this.getSupportActionBar().setTitle("BIENVENIDO");

    }


    private void switchFragment(Fragment fragment){
        fragmentManager = this.getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment).commit();
    }

    public static void show(Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }



}
