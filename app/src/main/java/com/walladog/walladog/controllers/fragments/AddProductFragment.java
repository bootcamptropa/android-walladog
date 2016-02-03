package com.walladog.walladog.controllers.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;
import com.walladog.walladog.R;
import com.walladog.walladog.WalladogApp;
import com.walladog.walladog.adapters.BasicsSpinArrayAdapter;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Gender;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.State;
import com.walladog.walladog.models.Sterile;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDProductService;
import com.walladog.walladog.utils.Constants;
import com.walladog.walladog.utils.DBAsyncTasksGet;
import com.walladog.walladog.utils.WDUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AddProductFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final static String TAG = AddProductFragment.class.getName();

    private String mParam1;
    private String mParam2;

    private ArrayAdapter<Race> mRaceArrayAdapter;
    private ArrayAdapter<Category> mCategoryArrayAdapter;
    private Spinner mSpinerCategory,mSpinnerRaces,mSpinnerSterile,mSpinnerStates,mSpinnerGender;
    private Button mButtonAdd;
    private AddProductForm mAddForm;

    private File mTmpFile;
    private String mTmpRoute;
    private List<String> mListImages = new ArrayList<>();


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

    private TextView mTextName,mTextDescription,mTextPrice;
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

        mTextName = (TextView) v.findViewById(R.id.add_product_name);
        mTextDescription = (TextView) v.findViewById(R.id.add_product_description);
        mTextPrice = (TextView) v.findViewById(R.id.add_product_price);
        im1 = (ImageView) v.findViewById(R.id.product_image1);
        im2 = (ImageView) v.findViewById(R.id.product_image2);
        im3 = (ImageView) v.findViewById(R.id.product_image3);
        im4 = (ImageView) v.findViewById(R.id.product_image4);
        im5 = (ImageView) v.findViewById(R.id.product_image5);
        mSpinerCategory = (Spinner) v.findViewById(R.id.spin_add_category);
        mSpinnerRaces = (Spinner) v.findViewById(R.id.spin_add_race);
        mSpinnerStates = (Spinner) v.findViewById(R.id.spin_add_state);
        mSpinnerSterile = (Spinner) v.findViewById(R.id.spin_add_sterile);
        mSpinnerGender = (Spinner) v.findViewById(R.id.spin_add_gender);
        mButtonAdd = (Button) v.findViewById(R.id.btn_add_product);
        mAddForm=new AddProductForm();

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAddForm())
                    sendForm();

            }
        });

        LoadSpinners();

        setPictureClickListeners();

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v(TAG, "Request code");
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
        //TODO salvar imagen y form si queremos pasar a landscape

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

        mTmpRoute = littleDir+littleFileName;
        Log.v(TAG,"TMP route:: "+mTmpRoute);


        Picasso.with(getContext()).invalidate(new File(littleDir, littleFileName));
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im1);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im2);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im3);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im4);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE5:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im5);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE101:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im1);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE102:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im2);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE103:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im3);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE104:
                mListImages.add(littleDir+littleFileName);
                Picasso.with(getContext())
                        .load(new File(littleDir, littleFileName))
                        .fit()
                        .centerCrop()
                        .into(im4);
                break;
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE105:
                mListImages.add(littleDir+littleFileName);
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

    public void LoadSpinners(){
        //TODO cargar de api en splashScreen
        DBAsyncTasksGet<Race> taskGetRaces = new DBAsyncTasksGet<Race>(DBAsyncTasksGet.TASK_GET_LIST,
                new Race(), getContext(),
                new DBAsyncTasksGet.OnItemsRecoveredFromDBListener<Race>() {
                    @Override
                    public void onItemsRecovered(List<Race> items) {
                        mRaceArrayAdapter = new BasicsSpinArrayAdapter<Race>(getContext(),android.R.layout.simple_spinner_item,items);
                        mSpinnerRaces.setAdapter(mRaceArrayAdapter);
                        mSpinnerRaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
        taskGetRaces.execute();

        DBAsyncTasksGet<Category> taskGetCategories = new DBAsyncTasksGet<Category>(DBAsyncTasksGet.TASK_GET_LIST,
                new Category(), getContext(),
                new DBAsyncTasksGet.OnItemsRecoveredFromDBListener<Category>() {
                    @Override
                    public void onItemsRecovered(List<Category> items) {
                        mCategoryArrayAdapter = new BasicsSpinArrayAdapter<Category>(getContext(),android.R.layout.simple_spinner_item,items);
                        mSpinerCategory.setAdapter(mCategoryArrayAdapter);
                        mSpinerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
        taskGetCategories.execute();

        List<Gender> oGenderList = new ArrayList<Gender>(){{
            add(new Gender("MAL","Macho"));
            add(new Gender("FEM","Hembra"));
            add(new Gender("NON","Desconocido"));
        }};
        BasicsSpinArrayAdapter<Gender> adapterGenders = new BasicsSpinArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,oGenderList);
        mSpinnerGender.setAdapter(adapterGenders);
        mSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<State> oStateList = new ArrayList<State>(){{
            add(new State(1,"Publicado"));
            add(new State(2,"Vendido"));
            add(new State(3,"Cancelado"));
            add(new State(4,"Suspendido"));
        }};
        BasicsSpinArrayAdapter<State> adapterStates = new BasicsSpinArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,oStateList);
        mSpinnerStates.setAdapter(adapterStates);
        mSpinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<Sterile> oSterileList = new ArrayList<Sterile>(){{
            add(new Sterile(1,"Si",true));
            add(new Sterile(0,"No",false));
        }};
        BasicsSpinArrayAdapter<Sterile> adapterSterile = new BasicsSpinArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,oSterileList);
        mSpinnerSterile.setAdapter(adapterSterile);
        mSpinnerSterile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private Boolean checkAddForm(){
        if(mTextName.length()==0||mTextName.length()<3){
            snakeMsg("Tienes un error en el nombre");
            return false;
        }
        if(mTextDescription.length()==0){
            snakeMsg("Indica alguna descripcion");
            return false;
        }
        if(mTextPrice.length()==0){
            snakeMsg("Debes indicar un precio");
            return false;
        }
        if(mListImages.size()<1){
            snakeMsg("Debes seleccionar alguna imágen");
            return false;
        }


        /** Barcelona , porque yo lo valgo **/
        String deflat = "41.431141200000006";
        String deflon = "2.1548569";

        mAddForm.setName(mTextName.getText().toString());
        mAddForm.setDescription(mTextDescription.getText().toString());
        mAddForm.setPrice(mTextPrice.getText().toString());
        mAddForm.setCategory(String.valueOf((int)((Category) mSpinerCategory.getSelectedItem()).getId_category()));
        mAddForm.setRace(String.valueOf((int)((Race) mSpinnerRaces.getSelectedItem()).getId_race()));
        mAddForm.setSterile(String.valueOf(((Sterile) mSpinnerSterile.getSelectedItem()).getValue()));
        mAddForm.setGender(String.valueOf(((Gender) mSpinnerGender.getSelectedItem()).getId()));
        mAddForm.setState(String.valueOf(((State) mSpinnerStates.getSelectedItem()).getId()));



        List<String> geoCoords;
        geoCoords = WalladogApp.getGeopos();
        mAddForm.setLatitutde(geoCoords!=null && geoCoords.get(0)!=null?geoCoords.get(0):deflat);
        mAddForm.setLongitude(geoCoords!=null && geoCoords.get(1)!=null?geoCoords.get(1):deflon);



        return true;
    }

    private void snakeMsg(String s){
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
    }

    private void sendForm(){
        /*mAddForm.setName("Prueba ramon2");
        mAddForm.setDescription("Esto es una prueba desde Android");
        mAddForm.setPrice("10.50");
        mAddForm.setCategory("1");
        mAddForm.setRace("1");
        mAddForm.setSterile("true");
        mAddForm.setGender("MAL");
        mAddForm.setState("1");
        mAddForm.setLatitutde("41.431141200000006");
        mAddForm.setLongitude("2.1548569");*/


        //Log.v(TAG, "Path::: " + mTmpRoute);
        //File file = new File(mTmpRoute);

        HashMap<String,RequestBody> map = new HashMap<>(2);
        int i = 1;
        for(String img: mListImages){
            File file = new File(img);
            RequestBody upload_file =RequestBody.create(MediaType.parse("multipart/form-data"), file);
            map.put("upload_image\"; filename=\"pimage"+String.valueOf(i)+".jpg",upload_file);
        }

        /*RequestBody upload_file1 =RequestBody.create(MediaType.parse("multipart/form-data"), file);
        map.put("upload_image\"; filename=\"product1.jpg",upload_file1);

        RequestBody upload_file2 =RequestBody.create(MediaType.parse("multipart/form-data"), file);
        map.put("upload_image\"; filename=\"product2.jpg",upload_file2);
*/
        //RequestBody upload_file =RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getName());
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getDescription());
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getPrice());
        RequestBody category = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getCategory());
        RequestBody race = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getRace());
        RequestBody sterile = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getSterile());
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getGender());
        RequestBody state = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getState());
        RequestBody latitude = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getLatitutde());
        RequestBody longitude = RequestBody.create(MediaType.parse("multipart/form-data"),mAddForm.getLongitude());

        try {
            ServiceGeneratorOAuth.createService(WDProductService.class)
                    .addProduct(map,name, description, price, category, race, sterile, gender, state, latitude,longitude)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Response<String> response, Retrofit retrofit) {

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.v(TAG,t.getMessage());
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            Log.v(TAG,"Eeeeeeeeeeeeeroooooor");
            e.printStackTrace();
        }


    }


    /**
     * Internal class for form Validation
     */
    private class AddProductForm{
        private String name;
        private String description;
        private String price;
        private String category;
        private String race;
        private String gender;
        private String state;
        private String imageUri;
        private String latitutde;
        private String longitude;

        public String getSterile() {
            return sterile;
        }

        public void setSterile(String sterile) {
            this.sterile = sterile;
        }

        private String sterile;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getRace() {
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getImageUri() {
            return imageUri;
        }

        public void setImageUri(String imageUri) {
            this.imageUri = imageUri;
        }

        public String getLatitutde() {
            return latitutde;
        }

        public void setLatitutde(String latitutde) {
            this.latitutde = latitutde;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }


}
