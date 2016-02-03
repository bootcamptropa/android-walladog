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
                EventBus.getDefault()
                        .post(new WDEventNotification<Category>(WDEventNotification.EVENT_FROM_SERVICE, "Servicio", mCategory));
            }
        });

        serviceDescription.setMovementMethod(new ScrollingMovementMethod());

        // Sync view & model
        //serviceTitle.setText(wdservice.getName());
        serviceTitle.setText(mCategory.getName());
        //serviceDescription.setText(wdservice.getDescription());
        serviceDescription.setText(getDogCategoryDescription(mCategory.getName()));

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

    private String getDogCategoryDescription(String category){
        String description = "";
        if(category.equals("Perros")){
            description = "En Walladog queremos ofrecerte la oportunidad de adoptar a un amigo que te necesita, entra y elige a un amigo para toda la vida.";
        }else if(category.equals("Comida")){
            description = "Uhm! tu mascota necesita comida, seguro que aqui encuentras justo la que necesita. Y mira que precios!";
        }else if(category.equals("Ropa")){
            description = "Dios mio que Frio!, va siendo hora de comprarnos una chaqueta, no te pierdas nuestra seleccion de moda canina, porque Él lo vale!";
        }else if(category.equals("Accesorios")){
            description = "Guau! mira que abrebadero, y mira que collar, los super accesorios de Walladog! Me compras uno ?";
        }else if(category.equals("Otros")){
            description = "Porque siempre hay otras cosas que no te esperas, aqui las tienes , en la sección Otros!";
        }
        return description;
    }

}
