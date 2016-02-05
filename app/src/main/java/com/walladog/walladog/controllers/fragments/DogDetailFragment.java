package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.LinePageIndicator;
import com.walladog.walladog.R;
import com.walladog.walladog.adapters.CarouselAdapter;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.ProductImage;
import com.walladog.walladog.models.Transaction;
import com.walladog.walladog.models.WDTransaction;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDTransactionService;
import com.walladog.walladog.utils.CustomMarker;
import com.walladog.walladog.utils.WorkaroundMapFragment;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class DogDetailFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = DogDetailFragment.class.getName();
    private static final String ARG_PRODUCT = "PRODUCT";

    TextView txtDogName,txtDogRace,txtDogSterile,txtDogLocation,txtDescription,txtProductOwner,txtProductOwnerLocation = null;
    CircularImageView avatarOwner;
    CarouselAdapter adapter = null;

    //Array of Products (dogs)
    private List<ProductImage> photoList=null;
    private Product mProduct=null;
    private Button mButtonBuy;

    //Map
    private ScrollView mScrollView;
    private GoogleMap mMap=null;
    private CustomMarker customDogMarker=null;
    private ViewPager pager;

    private LinePageIndicator ti;

    public static DogDetailFragment newInstance(Product product) {
        DogDetailFragment fragment = new DogDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    public DogDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
            //customDogMarker = new CustomMarker(mProduct.getName(), 41.390205, 2.154007);
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
        txtDescription = (TextView) v.findViewById(R.id.description_product);
        txtProductOwner = (TextView) v.findViewById(R.id.txt_product_owner_name);
        txtProductOwnerLocation = (TextView) v.findViewById(R.id.txt_product_owner_location);
        mButtonBuy = (Button) v.findViewById(R.id.btn_comprar);
        avatarOwner = (CircularImageView) v.findViewById(R.id.product_seller_avatar);
        pager = (ViewPager) v.findViewById(R.id.viewpager);
        ti = (LinePageIndicator) v.findViewById(R.id.titles);


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

        mButtonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar transacción
                try {
                    ServiceGeneratorOAuth.createService(WDTransactionService.class).createTransaction(new WDTransaction((int)mProduct.getId()))
                    .enqueue(new Callback<Transaction>() {
                        @Override
                        public void onResponse(Response<Transaction> response, Retrofit retrofit) {
                            Transaction r = response.body();
                            snakeMsg("Transaccion correcta!");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            snakeMsg("Transaccion incorrecta!");
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        syncViewAndModel();

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

        photoList = mProduct.getImages();
        adapter = new CarouselAdapter(getActivity().getSupportFragmentManager(),photoList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(this);


        ti.setViewPager(pager);
        ti.setOnPageChangeListener(this);

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
        txtDescription.setText(mProduct.getDescription());
        //txtDogLocation.setText("Barcelona");
        txtProductOwner.setText(mProduct.getSeller().getFirst_name()+" "+mProduct.getSeller().getLast_name());
        txtProductOwnerLocation.setText(mProduct.getSeller().getUsername());
        txtDogRace.setText(genero);

        Picasso.with(getContext())
                .load(mProduct.getSeller().getAvatar_thumbnail_url())
                .placeholder(R.drawable.personsample3)
                .into(avatarOwner);

        double latitude = Double.parseDouble(mProduct.getLatitude());
        double longitude  = Double.parseDouble(mProduct.getLongitude());

        //TODO reactivate this for real-devices
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(mProduct.getName());
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

    @Override
    public void onDetach() {
        super.onDetach();
        adapter=null;
        System.gc();
    }

    private void snakeMsg(String s){
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
    }
}
