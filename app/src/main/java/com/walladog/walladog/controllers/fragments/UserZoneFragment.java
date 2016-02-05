package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.walladog.walladog.R;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.UserData;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDUserDataService;
import com.walladog.walladog.models.apiservices.WDUserProductsService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserZoneFragment extends Fragment implements View.OnClickListener {

    private final static String TAG = UserZoneFragment.class.getName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btnNotifications,btnProductos,btnUserData;
    private FrameLayout mFrameProgress;
    private int mTasks;

    private UserData mUserData;
    private List<Product> mSellingProducts=new ArrayList<>(),mSoldProducts = new ArrayList<>();

    public UserZoneFragment() {

    }

    public static UserZoneFragment newInstance(String param1, String param2) {
        UserZoneFragment fragment = new UserZoneFragment();
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
        View v = inflater.inflate(R.layout.fragment_user_zone, container, false);

        //Controller view View
        btnNotifications = (Button) v.findViewById(R.id.btn_uz_notifications);
        btnProductos = (Button) v.findViewById(R.id.btn_uz_transactions);
        btnUserData = (Button) v.findViewById(R.id.btn_uz_profile);
        mFrameProgress = (FrameLayout) v.findViewById(R.id.frameProgress);

        //Listeners
        btnNotifications.setOnClickListener(this);
        btnProductos.setOnClickListener(this);
        btnUserData.setOnClickListener(this);

        try {
            LoadUserData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_uz_notifications:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, NotificationsFragment.newInstance("1", "1"),NotificationsFragment.class.getName())
                        .addToBackStack(NotificationsFragment.class.getName())
                        .commit();
                break;
            case R.id.btn_uz_transactions:
                Log.v(TAG,"Click2");
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, UserTransactionsFragment.newInstance(mSellingProducts, mSoldProducts),UserTransactionsFragment.class.getName())
                        .addToBackStack(UserTransactionsFragment.class.getName())
                        .commit();
                break;
            case R.id.btn_uz_profile:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, EditUserFragment.newInstance(mUserData),UserTransactionsFragment.class.getName())
                        .addToBackStack(EditUserFragment.class.getName())
                        .commit();
                break;
        }

    }

    public void LoadUserData() throws UnsupportedEncodingException {

        ServiceGeneratorOAuth.createService(WDUserDataService.class).getUserData().enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Response<UserData> response, Retrofit retrofit) {
                mUserData=response.body();
                mTasks++;
                removeOverlay();
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(getView(), "Error en datos de usuario", Snackbar.LENGTH_LONG).show();
                mTasks++;
                removeOverlay();
            }
        });

        ServiceGeneratorOAuth.createService(WDUserProductsService.class).getUserProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                List<Product> allProducts = response.body();
                for(Product p: allProducts){
                    if(p.getStateid()==1 || p.getStateid()==3 || p.getStateid()==4){
                        mSellingProducts.add(p);
                    }else{
                        mSoldProducts.add(p);
                    }
                }
                mTasks++;
                removeOverlay();
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(getView(), "Error al descargar tus productos", Snackbar.LENGTH_LONG).show();
                mTasks++;
                removeOverlay();
            }
        });


    }

    public void removeOverlay(){
        if(mTasks==2){
            mFrameProgress.setVisibility(View.GONE);
            mTasks=0;
        }
    }
}
