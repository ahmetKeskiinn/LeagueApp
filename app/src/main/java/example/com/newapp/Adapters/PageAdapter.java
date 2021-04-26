package example.com.newapp.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import example.com.newapp.Views.Fragment.FixtureViewPager;

/*
    Bu class bir Adaptördür. Takımların görüntülenmesi için fragment'e ihtiyacımız vardır. Bu fragment nesnesi bu adaptörde oluşturulup
    ilgili yere geri yollanmaktadır.
 */
/*

    This class is an Adapter. We need fragment to view the teams. This fragment object is created in this adapter and
    sent back to the relevant location.
 */
public class PageAdapter extends FragmentPagerAdapter {
    private static final String TAG = "Page Adapter";
    private Context myContext;
    int totalTabs;
    private int weekNo;

    public PageAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        this.weekNo = weekNo;
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        FixtureViewPager teamFragment = new FixtureViewPager(myContext, position);
        return teamFragment;

    }


    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}