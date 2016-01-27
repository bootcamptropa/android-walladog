package com.walladog.walladog.controllers.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walladog.walladog.R;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.utils.WDEventNotification;

import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;

/**
 * Created by hadock on 28/12/15.
 *
 */

public class ServiceFragment extends Fragment {

    private static final String TAG = ServiceFragment.class.getName();

    public static final String ARG_WDSERVICE = null;
    public static final String ARG_CATEGORIA = null;

    private WDServices wdservice;
    private Category mCategory;

    private static TextView serviceTitle = null;
    private static TextView serviceDescription = null;
    private static ImageView serviceImage = null;

    public static ServiceFragment newInstance(WDServices service){
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WDSERVICE, service);
        fragment.setArguments(args);
        return fragment;
    }

    public ServiceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //wdservice = (WDServices) getArguments().getSerializable(ARG_WDSERVICE);
            mCategory = (Category) getArguments().getSerializable(ARG_CATEGORIA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_service, container, false);

        // Linking with view
        serviceTitle = (TextView) root.findViewById(R.id.txt_service_title);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Lobster-Regular.ttf");
        serviceTitle.setTypeface(face);
        serviceTitle.setTextSize(20);

        serviceDescription = (TextView) root.findViewById(R.id.txt_service_description);
        serviceImage = (ImageView) root.findViewById(R.id.img_service3);

        serviceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((int) mCategory.getId()){
                    case 1:
                        EventBus.getDefault().post(new WDEventNotification<Category>(1,"Perros Seleccionados",mCategory));
                        Log.v(TAG,"Son perros");
                        break;
                    case 2:
                        Log.v(TAG,"Son comida");
                        break;
                }
            }
        });

        serviceDescription.setMovementMethod(new ScrollingMovementMethod());

        // Sync view & model
        //serviceTitle.setText(wdservice.getName());
        serviceTitle.setText(mCategory.getName());
        //serviceDescription.setText(wdservice.getDescription());
        serviceDescription.setText("Descripcion de la categoria/servicio");

        Picasso.with(getActivity().getApplicationContext())
                .load(getDogCategoryPicture(mCategory.getName()))
                .placeholder(R.drawable.walladogsmall)
                .into(serviceImage);

        return root;
    }


    private String getDogCategoryPicture(String category){
        String pic = "";
        if(category.equals("Perros")){
            pic = "http://comoeducaraunperro.es/wp-content/uploads/2015/05/pa1-300x300.jpg";
        }else if(category.equals("Comida")){
            pic = "http://t2.uccdn.com/images/0/4/4/img_frutas_que_no_deben_comer_los_perros_27440_300.jpg";
        }else if(category.equals("Ropa")){
            pic = "http://www.ocio.net/wp-content/uploads/mascotas/29-228-thickbox-300x300.jpg";
        }else if(category.equals("Accesorios")){
            pic = "http://www.ocio.net/wp-content/uploads/mascotas/los-mejores-accesorios-para-perros-300x300.jpg";
        }else if(category.equals("Otros")){
            pic = "http://static.notinerd.com/wp-content/uploads/2015/07/2305-300x300.jpg";
        }
        return pic;
    }

}
