package example.com.newapp.Views;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import example.com.newapp.Adapters.PageAdapter;
import example.com.newapp.R;
import example.com.newapp.Utils.SharedPref;

/*
    Bu aktivitide fikstür görüntülemesi yapılması için hazırlık yapılmaktadır. Bu aktivitinin içerisinde;
    Tablayout, viewpager sharedpreferences,spinner vardır. Takım sayısına göre Tablayout nasıl kullanılacağı,
    spinner dropdown menüsünde kaç hafta olacağı ayarlanmaktadır.
 */
/*
    Preparations are made for fixture imaging in this activity. In this activity;
    There are tablayout, viewpager sharedpreferences, spinner. How to use Tablayout according to the number of teams,
    The number of weeks is set in the spinner dropdown menu.
 */
public class ShowFixture extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "Show Fixture Activity";
    private ViewPager viewPager;
    private Spinner spinner;
    private TabLayout tabLayout;
    private TextView topTw;
    private SharedPref sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);
        initialUI();
        initialViewPager();

    }

    private void initialUI() {
        spinner = (Spinner)findViewById(R.id.spinner1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        sharedPref = SharedPref.getInstance(this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        if(Integer.parseInt(sharedPref.getTeamsSize())%2 == 1){
            String[] paths = new String[Integer.parseInt(sharedPref.getTeamsSize()) * 2];
            int teamSize= Integer.parseInt(sharedPref.getTeamsSize());
            for (int i = 0; i < (teamSize * 2); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(""));
                paths[i] = (i+1)+getString(R.string.weekNo);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowFixture.this,
                    R.layout.spinner_line,paths);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown);

            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
        else if(Integer.parseInt(sharedPref.getTeamsSize())%2 == 0){
            String[] paths = new String[(Integer.parseInt(sharedPref.getTeamsSize())-1)*2];
            for (int i = 0; i < ((Integer.parseInt(sharedPref.getTeamsSize())-1)*2); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(""));
                paths[i] = (i+1)+getString(R.string.weekNo);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowFixture.this,
                    R.layout.spinner_line,paths);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        topTw = findViewById(R.id.weekText);
    }


    private void initialViewPager() {
        final PageAdapter adapter = new PageAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        topTw.setText("1" + getString(R.string.weekNo));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tabLayout.getTabAt(position).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                topTw.setText(String.valueOf(tab.getPosition() + 1) + getText(R.string.weekNo));
                spinner.setSelection(tab.getPosition());
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}