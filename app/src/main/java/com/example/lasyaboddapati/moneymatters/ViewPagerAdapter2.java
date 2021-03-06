package com.example.lasyaboddapati.moneymatters;

/**
 * Created by kaustav1992 on 2/21/15.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter2 extends FragmentPagerAdapter {
    private Context _context;

    String username;

    public ViewPagerAdapter2(Context context, FragmentManager fm,String user) {
        super(fm);
        _context=context;
        username=user;

    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch(position){
            case 0:
                f=UserNotificationFragment.newInstance(_context,username);
                break;
            case 1:
                f=SystemNotificationFragment.newInstance(_context);
                break;
        }
        return f;
    }
    @Override
    public int getCount() {
        return 2;
    }

}