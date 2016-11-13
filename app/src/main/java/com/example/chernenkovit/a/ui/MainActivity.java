package com.example.chernenkovit.a.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.chernenkovit.a.Const;
import com.example.chernenkovit.a.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.chernenkovit.a.Const.IMAGE_LOADER;

/** Main activity class with tabs attachment and menu implementation. */
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText et_inputField;
    private Button btn_ok;
    private MenuItem sortByDateMenuItem;
    private MenuItem sortByStatusMenuItem;
    public static int sortOrder;
    private HistoryFragment historyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TestFragment(), "TEST");
        adapter.addFragment(new HistoryFragment(), "HISTORY ");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        sortByDateMenuItem = menu.findItem(R.id.action_menu_sort_history_by_date);
        sortByStatusMenuItem = menu.findItem(R.id.action_menu_sort_history_by_status);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_sort_history_by_date:
                sortByDateMenuItem.setChecked(true);
                sortByStatusMenuItem.setChecked(false);
                sortOrder = Const.SORT_BY_DATE;
                historyFragment.getLoaderManager().restartLoader(IMAGE_LOADER, null, historyFragment);
                return true;
            case R.id.action_menu_sort_history_by_status:
                sortByStatusMenuItem.setChecked(true);
                sortByDateMenuItem.setChecked(false);
                sortOrder = Const.SORT_BY_STATUS;
                historyFragment.getLoaderManager().restartLoader(IMAGE_LOADER, null, historyFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TestFragment();
            } else {
                historyFragment = new HistoryFragment();
                return historyFragment;
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
