package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.ServicesPagerAdapter;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.responses.ProductResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hadock on 28/12/15.
 * 
 */

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    public static final String ARG_WDSERVICES = "ARG_WDSERVICES";
    public static final String ARG_CATEGORIAS = "ARG_CATEGORIAS";
    public static final String ARG_WDPRODUCTS = "ARG_WDPRODUCTS";

    private List<WDServices> services = null;
    private ViewPager pager = null;

    public static HomeFragment newInstance(List<WDServices> services, List<Product> products,List<Category> categorias) {
        HomeFragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(HomeFragment.ARG_WDSERVICES, (Serializable) services);
        arguments.putSerializable(HomeFragment.ARG_WDPRODUCTS, (Serializable) products);
        arguments.putSerializable(HomeFragment.ARG_CATEGORIAS, (Serializable) categorias);
        fragment.setArguments(arguments);
        return fragment;
    }

    public static HomeFragment newInstance(List<WDServices> services, ProductResponse products) {
        HomeFragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(HomeFragment.ARG_WDSERVICES, (Serializable) services);
        arguments.putSerializable(HomeFragment.ARG_WDPRODUCTS, (Serializable) products);
        fragment.setArguments(arguments);
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

        if (getArguments() != null) {
            List<WDServices> services = (List<WDServices>) getArguments().getSerializable(this.ARG_WDSERVICES);
            List<Category> categorias = (List<Category>) getArguments().getSerializable(ARG_CATEGORIAS);
            //pager.setAdapter(new ServicesPagerAdapter(getChildFragmentManager(), services));
            pager.setAdapter(new ServicesPagerAdapter(getChildFragmentManager(), categorias));
        }

        return root;
    }

}
