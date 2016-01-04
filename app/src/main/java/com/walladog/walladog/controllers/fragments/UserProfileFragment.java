package com.walladog.walladog.controllers.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.ProfilePagerAdapter;


public class UserProfileFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ViewPager pager = null;

    private Button b1,b2,b3;

    public UserProfileFragment() {

    }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        b1 = (Button) v.findViewById(R.id.btn_enventa);
        b2 = (Button) v.findViewById(R.id.btn_vendido);
        b3 = (Button) v.findViewById(R.id.btn_busquedas);
        pager = (ViewPager) v.findViewById(R.id.view_pager);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#000000"));
                b3.setTextColor(Color.parseColor("#000000"));
                pager.setCurrentItem(0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setTextColor(Color.parseColor("#000000"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#000000"));
                pager.setCurrentItem(1);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setTextColor(Color.parseColor("#000000"));
                b2.setTextColor(Color.parseColor("#000000"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                pager.setCurrentItem(2);
            }
        });


        pager.setAdapter(new ProfilePagerAdapter(getChildFragmentManager()));
        pager.addOnPageChangeListener(this);


        return v;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void toogleButton(int position){
        switch (position){
            case 1:
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#fdfdfd"));
                b3.setTextColor(Color.parseColor("#fdfdfd"));
                break;
            case 2:
                b1.setTextColor(Color.parseColor("#fdfdfd"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#fdfdfd"));
                break;
            case 3:
                b1.setTextColor(Color.parseColor("#fdfdfd"));
                b2.setTextColor(Color.parseColor("#fdfdfd"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
    }
}
