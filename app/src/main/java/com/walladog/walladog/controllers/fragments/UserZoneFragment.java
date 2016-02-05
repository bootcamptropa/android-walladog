package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
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


    private Button btnNotifications,btnProductos,btnUserData;
    private CircularImageView mAvatar;
    private FrameLayout mFrameProgress;
    private TextView mUsername;
    private int mTasks;

    private UserData mUserData;
    private List<Product> mSellingProducts=new ArrayList<>(),mSoldProducts = new ArrayList<>();

    public UserZoneFragment() {

    }

    public static UserZoneFragment newInstance(String param1, String param2) {
        UserZoneFragment fragment = new UserZoneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        mAvatar = (CircularImageView) v.findViewById(R.id.userzone_avatar);
        mUsername = (TextView) v.findViewById(R.id.userzone_username);

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
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, UserProductsFragment.newInstance(mSellingProducts, mSoldProducts),UserProductsFragment.class.getName())
                        .addToBackStack(UserProductsFragment.class.getName())
                        .commit();
                break;
            case R.id.btn_uz_profile:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, EditUserFragment.newInstance(mUserData),UserProductsFragment.class.getName())
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
                Picasso.with(getContext()).load(mUserData.getAvatar_url()).into(mAvatar);
                mUsername.setText(mUserData.getUsername());
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
