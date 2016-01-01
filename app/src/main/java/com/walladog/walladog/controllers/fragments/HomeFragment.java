package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.ServicesPagerAdapter;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.apiservices.ServiceGenerator;
import com.walladog.walladog.models.apiservices.WDServicesService;
import com.walladog.walladog.models.responses.ServicesResponse;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by hadock on 28/12/15.
 * 
 */

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    public static final String ARG_WDSERVICES = "ARG_WDSERVICES";
    public static final String ARG_WDPRODUCTS = "ARG_WDPRODUCTS";

    private List<WDServices> services = null;
    private ViewPager pager = null;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View root = inflater.inflate(R.layout.fragment_home,container,false);

        //Pager
        pager = (ViewPager) root.findViewById(R.id.view_pager);

        Bundle b = getArguments();
        List<WDServices> services = (List<WDServices>) b.getSerializable(this.ARG_WDSERVICES);
        pager.setAdapter(new ServicesPagerAdapter(getChildFragmentManager(), services));

        return root;
    }

}
