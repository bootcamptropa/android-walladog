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
import com.walladog.walladog.utils.DBAsyncTasksGet;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hadock on 28/12/15.
 * 
 */

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    public static final String ARG_CATEGORIAS = "ARG_CATEGORIAS";

    private List<WDServices> services = null;
    private ViewPager pager = null;

    public static HomeFragment newInstance(List<Product> products,List<Category> categorias) {
        HomeFragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(HomeFragment.ARG_CATEGORIAS, (Serializable) categorias);
        fragment.setArguments(arguments);
        return fragment;
    }

    public static HomeFragment newInstance(ProductResponse products) {
        HomeFragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        fragment.setArguments(arguments);
        return fragment;
    }

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
                DBAsyncTasksGet<Category> taskGetCategory = new DBAsyncTasksGet<Category>(DBAsyncTasksGet.TASK_GET_LIST,
                        new Category(), getContext(),
                        new DBAsyncTasksGet.OnItemsRecoveredFromDBListener<Category>() {
                            @Override
                            public void onItemsRecovered(List<Category> items) {
                                pager.setAdapter(new ServicesPagerAdapter(getChildFragmentManager(), items));
                            }
                        });
                taskGetCategory.execute();
        return root;
    }

}
