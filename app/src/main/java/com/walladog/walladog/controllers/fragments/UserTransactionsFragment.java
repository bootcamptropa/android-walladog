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
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.responses.ProductResponse;

import java.io.Serializable;
import java.util.List;


public class UserTransactionsFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public static final String ARG_SELLINGPRODUCTS = "ARG_SELLINGPRODUCTS";
    public static final String ARG_SOLDPRODUCTS = "ARG_SOLDPRODUCTS";

    private List<Product> mProductsSelling;
    private List<Product> mProductsSold;

    private ViewPager pager = null;

    private Button b1,b2,b3;

    public UserTransactionsFragment() {

    }

    public static UserTransactionsFragment newInstance(List<Product> productsSelling,List<Product> productsSold) {
        UserTransactionsFragment fragment = new UserTransactionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SELLINGPRODUCTS, (Serializable) productsSelling);
        args.putSerializable(ARG_SOLDPRODUCTS, (Serializable) productsSold);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductsSelling = (List<Product>) getArguments().getSerializable(ARG_SELLINGPRODUCTS);
            mProductsSold = (List<Product>) getArguments().getSerializable(ARG_SOLDPRODUCTS);
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
                b2.setTextColor(Color.parseColor("#d3d3d3"));
                b3.setTextColor(Color.parseColor("#d3d3d3"));
                pager.setCurrentItem(0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setTextColor(Color.parseColor("#d3d3d3"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#d3d3d3"));
                pager.setCurrentItem(1);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setTextColor(Color.parseColor("#d3d3d3"));
                b2.setTextColor(Color.parseColor("#d3d3d3"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                pager.setCurrentItem(2);
            }
        });

        pager.setAdapter(new ProfilePagerAdapter(getChildFragmentManager(),mProductsSelling,mProductsSold));
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
}
