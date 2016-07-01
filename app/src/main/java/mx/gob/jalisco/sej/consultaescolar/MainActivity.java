package mx.gob.jalisco.sej.consultaescolar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mx.gob.jalisco.sej.consultaescolar.fragments.NotificationsFragment;
import mx.gob.jalisco.sej.consultaescolar.fragments.SchoolFragment;
import mx.gob.jalisco.sej.consultaescolar.fragments.ServicesFragment;
import mx.gob.jalisco.sej.consultaescolar.services.NotifyService;
import mx.gob.jalisco.sej.consultaescolar.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_action_social_school_white,
            R.drawable.ic_action_icono_escuela_white,
            R.drawable.ic_action_social_notifications_white
    };
    PendingIntent pendingIntent;

    public static final String PREF_USER_FIRST_TIME = "user_first_time";

    boolean isUserFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titleApp = (TextView) findViewById(R.id.titleApp);
        titleApp.setText("Consulta Escolar");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        Intent alarmIntent = new Intent(MainActivity.this, NotifyService.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));
        if(isUserFirstTime){
            Utils.saveSharedSetting(MainActivity.this,NotifyService.PREF_USER_LAST_NOTIFICACTION,"0");
            Utils.saveSharedSetting(MainActivity.this,PREF_USER_FIRST_TIME,"false");
        }

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();



        enableNotifications();

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

        if(id == R.id.action_share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=mx.gob.jalisco.sej.consultaescolar");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabIcons() {
        View view1 = getLayoutInflater().inflate(R.layout.item_tab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(tabIcons[0]);
        tabLayout.getTabAt(0).setCustomView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.item_tab, null);
        view2.findViewById(R.id.icon).setBackgroundResource(tabIcons[1]);
        tabLayout.getTabAt(1).setCustomView(view2);

        View view3 = getLayoutInflater().inflate(R.layout.item_tab, null);
        view3.findViewById(R.id.icon).setBackgroundResource(tabIcons[2]);
        tabLayout.getTabAt(2).setCustomView(view3);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ServicesFragment(), "ALUMNOS");
        adapter.addFragment(new SchoolFragment(), "ESCUELA");
        adapter.addFragment(new NotificationsFragment(), "NOTIFICACIONES");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            return null;
        }
    }

    public void enableNotifications(){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        long updateInterval = 30000;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + updateInterval, updateInterval, pendingIntent);

    }

}
