package com.walladog.walladog.adapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.walladog.walladog.controllers.fragments.NotificationListFragment;
import com.walladog.walladog.models.WDNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadock on 12/12/15.
 *
 */

public class NotificationsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = NotificationsPagerAdapter.class.getName();

    List<WDNotification> mToRead,mReaded;

    private class NotificationOptions{
        private String name;

        public NotificationOptions(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private List<NotificationOptions> mNotificationOptions = null;

    public NotificationsPagerAdapter(android.support.v4.app.FragmentManager fm,List<WDNotification> mToRead,List<WDNotification> mReaded) {
        super(fm);
        Log.v(TAG, "Etramos");
        mNotificationOptions = new ArrayList<NotificationOptions>();
        mNotificationOptions.add(new NotificationOptions("No Leidas"));
        mNotificationOptions.add(new NotificationOptions("Leidas"));
        this.mToRead=mToRead;
        this.mReaded=mReaded;
    }

    @Override
    public int getCount() {
        return mNotificationOptions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNotificationOptions.get(position).getName();
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = NotificationListFragment.newInstance(0,mToRead);
                break;
            case 1:
                fragment = NotificationListFragment.newInstance(1,mReaded);
                break;
        }
        return fragment;
    }

    public void setToRead(List<WDNotification> toRead) {
        mToRead = toRead;
    }

    public void setReaded(List<WDNotification> readed) {
        mReaded = readed;
    }

}