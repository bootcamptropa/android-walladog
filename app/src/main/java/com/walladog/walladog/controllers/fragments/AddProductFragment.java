package com.walladog.walladog.controllers.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.walladog.walladog.R;
import com.walladog.walladog.utils.Constants;
import com.walladog.walladog.utils.WDUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddProductFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final static String TAG = AddProductFragment.class.getName();

    private String mParam1;
    private String mParam2;


    //Photos
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1 = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2 = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3 = 3;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4 = 4;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5 = 5;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE101 = 101;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE102 = 102;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE103 = 103;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE104 = 104;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE105 = 105;
    private static final int SOURCE_CAMERA = 1;
    private static final int SOURCE_GALLERY = 2;

    private String originalImage,littleDir,littleFileName,littleFullRoute;

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

        Log.v(TAG,"Request code");
        Log.v(TAG, String.valueOf(requestCode));

        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE101 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE102 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE103 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE104 ||
                requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE105){


            if (resultCode == getActivity().RESULT_OK) {
                try {
                    if(requestCode>5){
                        Uri imageUri = data.getData();
                        decodeBitmap(requestCode,SOURCE_GALLERY,imageUri);
                    }else{
                        decodeBitmap(requestCode,SOURCE_CAMERA,null);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setPictureClickListeners(){
        //Im1 Listener
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1);*/
                pictureDialog(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1);
            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureDialog(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2);
            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureDialog(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3);
            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureDialog(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4);
            }
        });
        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureDialog(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Salvar foto para la orientación.

    }

    private void pictureDialog(final int picNumber){
        final CharSequence[] options = {"Camara","Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elegir fuente de la Fotografía");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which] == "Camara") {
                    openCamera(picNumber);
                } else if (options[which] == "Galeria") {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona fuente de imagen"), getConstantFromPicture(100+picNumber));
                } else {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openCamera(int picNumber){
        if(WDUtils.isExternalStorageAviable() && WDUtils.isExternalStorageWritable()){
            String path = Environment.getExternalStorageDirectory()
                    + File.separator
                    +Constants.APP_IMAGES
                    +"tmpPicture"+String.valueOf(picNumber)+".jpg";

            Log.v(TAG,"Guardando original en : "+path);

            File newFile = new File(path);

            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(i,getConstantFromPicture(picNumber));

        }else if(!WDUtils.isExternalStorageAviable()){
            Log.v(TAG,"No external storage aviable Writing in Internal Storage");
        }

    }

    private int getConstantFromPicture(int picNumber){
        switch (picNumber){
            case 1:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1;
            case 2:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2;
            case 3:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3;
            case 4:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4;
            case 5:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5;
            case 101:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE101;
            case 102:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE102;
            case 103:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE103;
            case 104:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE104;
            case 105:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE105;
            default:
                return CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1;
        }
    }

    private void decodeBitmap(int requestCode,int source,Uri galleryUri) throws IOException {

        if(source==SOURCE_CAMERA) {
            String dir = Environment.getExternalStorageDirectory() + File.separator + Constants.APP_IMAGES;

            originalImage = dir + "tmpPicture" + String.valueOf(requestCode) + ".jpg";
            littleDir = Environment.getExternalStorageDirectory() + File.separator + Constants.APP_IMAGES_REDUCED;
            littleFileName = "tmpReduced" + String.valueOf(requestCode) + ".jpg";
            littleFullRoute = littleDir + littleFileName;
            File littleFile = new File(littleDir, littleFileName);
            if (littleFile.exists()) littleFile.delete();

            //Save image to External Storage DIR
            Bitmap bmp = BitmapFactory.decodeFile(originalImage);

            FileOutputStream out = new FileOutputStream(littleFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }else{
            littleDir = Environment.getExternalStorageDirectory() + File.separator + Constants.APP_IMAGES_REDUCED;
            littleFileName = "tmpReduced" + String.valueOf(requestCode) + ".jpg";
            File littleFile = new File(littleDir, littleFileName);

            InputStream imageStream = getActivity().getContentResolver().openInputStream(galleryUri);
            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

            Log.v(TAG,"Rotate::");
            Log.v(TAG, String.valueOf(getRotation(getContext(), galleryUri)));

            FileOutputStream out = new FileOutputStream(littleFile);
            bmp = rotateImageIfRequired(getContext(),bmp,getImageUri(getContext(),bmp));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();
        }

        Picasso.with(getContext()).invalidate(new File(littleDir, littleFileName));
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im1);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im2);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im3);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im4);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im5);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE101:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im1);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE102:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im2);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE103:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im3);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE104:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im4);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE105:
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im5);
                break;
        }
    }

    private static Bitmap rotateImageIfRequired(Context context,Bitmap img, Uri selectedImage) {

        // Detect rotation
        int rotation=getRotation(context, selectedImage);
        if(rotation!=0){
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        }else{
            return img;
        }
    }

    private static int getRotation(Context context,Uri selectedImage) {
        int rotation =0;
        ContentResolver content = context.getContentResolver();
        Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { "orientation", "date_added" },null, null,"date_added desc");
        if (mediaCursor != null && mediaCursor.getCount() !=0 ) {
            while(mediaCursor.moveToNext()){
                rotation = mediaCursor.getInt(0);
                break;
            }
        }
        mediaCursor.close();
        return rotation;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}
