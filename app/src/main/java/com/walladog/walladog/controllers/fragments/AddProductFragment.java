package com.walladog.walladog.controllers.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.walladog.walladog.R;

import java.io.ByteArrayOutputStream;

public class AddProductFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    //Photos
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1 = 1001;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2 = 1002;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3 = 1003;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4 = 1004;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5 = 1005;
    Button button;
    ImageView im1,im2,im3,im4,im5;


    public AddProductFragment() {

    }

    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_product, container, false);

        im1 = (ImageView) v.findViewById(R.id.product_image1);
        im2 = (ImageView) v.findViewById(R.id.product_image2);
        im3 = (ImageView) v.findViewById(R.id.product_image3);
        im4 = (ImageView) v.findViewById(R.id.product_image4);
        im5 = (ImageView) v.findViewById(R.id.product_image5);

        setPictureClickListeners();

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5){

            if (resultCode == getActivity().RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,byteArray.length);

                switch (requestCode){
                    case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1:
                        im1.setImageBitmap(bitmap);
                        break;
                    case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2:
                        im2.setImageBitmap(bitmap);
                        break;
                    case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3:
                        im3.setImageBitmap(bitmap);
                        break;
                    case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4:
                        im4.setImageBitmap(bitmap);
                        break;
                    case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5:
                        im5.setImageBitmap(bitmap);
                        break;
                }
            }

        }
    }

    private void setPictureClickListeners(){
        //Im1 Listener
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1);
            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2);
            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3);
            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4);
            }
        });
        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Salvar foto para la orientación.

    }

}
