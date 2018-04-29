package edu.uwf.scavenger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends FragmentActivity implements
        Home.OnFragmentInteractionListener,
        Search.OnFragmentInteractionListener,
        QrScanner.OnFragmentInteractionListener
{

    private static boolean isStartup = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchToHome();
                        return true;
                    case R.id.navigation_scanQr:
                        switchToQR(null);
                        return true;
                    case R.id.navigation_map:
                        switchToMap();
                        return true;
                    case R.id.navigation_search:
                        switchToSearch();
                        return true;
                }
            }
            catch(Exception ex)
            {
                String m = "Error";
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);

            if(isStartup)
            {
                switchToHome();
                isStartup = false;
            }

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
        catch(Exception ex)
        {
            String m = "Error";
        }
    }

    public void switchToHome() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_fragment_placeholder, new Home()).commit();
    }

    QrScanner scan;

    public void switchToQR(String scanRes) {
        try {
            FragmentManager manager = getSupportFragmentManager();

            scan = new QrScanner();
            if(scanRes != null)
            {
                scan.scanRes = scanRes;
            }

            manager.beginTransaction().replace(R.id.main_fragment_placeholder, scan).commit();
        }
        catch(Exception ex)
        {
            String m = "Error";
        }
    }

    public void switchToSearch() {
        try {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main_fragment_placeholder, new Search()).commit();
        }
        catch(Exception ex)
        {
            String m = "Error";
        }
    }

    public void switchToMap() {
        try {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main_fragment_placeholder, (new MapViewFragment())).commit();
        }
        catch(Exception ex)
        {
            String m = "Error";
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
       try {
           IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

           if(result != null)
           {
               switchToQR(result.getContents());
           }
            else{
               super.onActivityResult(requestCode, resultCode, intent);
           }
       }
       catch (Exception ex)
       {
           String m = "Error";
       }

    }
}
