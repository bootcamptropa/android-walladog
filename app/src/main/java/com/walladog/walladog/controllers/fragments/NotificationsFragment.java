package com.walladog.walladog.controllers.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.NotificationsPagerAdapter;
import com.walladog.walladog.models.dao.NotificationDAO;

public class NotificationsFragment extends Fragment
        implements ViewPager.OnPageChangeListener{

    private static final String TAG = NotificationsFragment.class.getName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private NotificationsPagerAdapter adapter=null;
    private ViewPager pager = null;
    private Button b1,b2;
    private LinearLayout mainLinear;


    private String mParam1;
    private String mParam2;

    public NotificationsFragment() {

    }

    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        mainLinear = (LinearLayout) v.findViewById(R.id.main_linear_notifications);

        b1 = (Button) v.findViewById(R.id.btn_toread);
        b2 = (Button) v.findViewById(R.id.btn_readed);

        pager = (ViewPager) v.findViewById(R.id.view_pager_notifications);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#d3d3d3"));
                pager.setCurrentItem(0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setTextColor(Color.parseColor("#d3d3d3"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                pager.setCurrentItem(1);
            }
        });
        return v;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position==0){
            b1.setTextColor(Color.parseColor("#ffffff"));
            b2.setTextColor(Color.parseColor("#d3d3d3"));
        }else{
            b1.setTextColor(Color.parseColor("#d3d3d3"));
            b2.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NotificationDAO nDao = new NotificationDAO(getContext());

        adapter = new NotificationsPagerAdapter(getChildFragmentManager(),
                nDao.getNotificationsFromType("0"),
                nDao.getNotificationsFromType("1"));

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);

        mainLinear.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
