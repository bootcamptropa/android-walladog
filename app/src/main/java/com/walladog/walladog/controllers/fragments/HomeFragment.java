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
    private static final String ARG_SERVICES = "services";
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

        //Api call
        getServices();
        return root;
    }


    //Helper Functions
    public void getServices() {
        WDServicesService apiServices = ServiceGenerator.createService(WDServicesService.class);
        Call<ServicesResponse> call = apiServices.getMultiTask();

        call.enqueue(new Callback<ServicesResponse>() {
            @Override
            public void onResponse(Response<ServicesResponse> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    List<WDServices> services = response.body().getData();
                    syncViewWithModel(services);
                }else{
                    Log.v(TAG, "Error in response of " + ServicesResponse.class.getName());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void syncViewWithModel(List<WDServices> serviceList){
        //Set adapter of viewPager
        pager.setAdapter(new ServicesPagerAdapter(getChildFragmentManager(), serviceList));
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    
}
