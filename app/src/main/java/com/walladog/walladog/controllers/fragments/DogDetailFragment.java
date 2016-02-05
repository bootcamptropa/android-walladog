package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.LinePageIndicator;
import com.walladog.walladog.R;
import com.walladog.walladog.adapters.CarouselAdapter;
import com.walladog.walladog.models.Photo;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.utils.CustomMarker;
import com.walladog.walladog.utils.WorkaroundMapFragment;

import java.util.ArrayList;
import java.util.List;


public class DogDetailFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = DogDetailFragment.class.getName();
    private static final String ARG_PRODUCT = "PRODUCT";

    TextView txtDogName,txtDogRace,txtDogSterile,txtDogLocation = null;

    //Array of Products (dogs)
    private List<Photo> photoList=null;
    private Product mProduct=null;

    //Map
    private ScrollView mScrollView;
    private GoogleMap mMap=null;
    private CustomMarker customDogMarker=null;

    public static DogDetailFragment newInstance(Product product) {
        DogDetailFragment fragment = new DogDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    public DogDetailFragment() {
        photoList = new ArrayList<>();
        for(int i=0; i<5; i++){
            photoList.add(new Photo("Photo"+String.valueOf(i),"http://loremflickr.com/30"+String.valueOf(i)+"/300/dog"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
            customDogMarker = new CustomMarker(mProduct.getName(), 41.390205, 2.154007);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dog_detail, container, false);

        //Generic fields
        txtDogName = (TextView) v.findViewById(R.id.txt_dog_name);
        txtDogRace = (TextView) v.findViewById(R.id.txt_dog_race);
        txtDogSterile = (TextView) v.findViewById(R.id.txt_dog_sterile);
        //txtDogLocation = (TextView) v.findViewById(R.id.txt_dog_location);



        //Map - intercepting touch events in custom class
        if (mMap == null) {

            mMap = ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            mScrollView = (ScrollView) v.findViewById(R.id.detailScroll); //parent scrollview in xml, give your scrollview id value

            ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment))
                    .setListener(new WorkaroundMapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {
                            mScrollView.requestDisallowInterceptTouchEvent(true);
                        }
                    });

        }

        if(mProduct!=null){
            CarouselAdapter adapter = new CarouselAdapter(getActivity().getSupportFragmentManager(),photoList);
            ViewPager pager = (ViewPager) v.findViewById(R.id.viewpager);

            pager.addOnPageChangeListener(this);

            LinePageIndicator ti = (LinePageIndicator) v.findViewById(R.id.titles);
            pager.setAdapter(adapter);
            ti.setViewPager(pager);
            ti.setOnPageChangeListener(this);
            syncViewAndModel();
        }



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

    private void syncViewAndModel(){
        String genero = "";
        switch (mProduct.getGender()){
            case "MAL":
                genero = "Macho";
                break;
            case "FEM":
                genero = "Hembra";
                break;
            default:
                genero = "Desconocido";
                break;
        }

        txtDogName.setText(mProduct.getName());
        String isSterile = mProduct.isSterile()?"Si":"No";
        txtDogSterile.setText(isSterile);
        //txtDogLocation.setText("Barcelona");
        txtDogRace.setText(genero);

        double latitude = Double.parseDouble(mProduct.getLatitude());
        double longitude  = Double.parseDouble(mProduct.getLongitude());
            //TODO reactivate this for real-devices
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Walladog");
            // Set icon
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_petfeet));
            // adding marker
        try {
            mMap.addMarker(marker);
        }catch (Exception e){

        }

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().setZoomControlsEnabled(true);


    }
}
