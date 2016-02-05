package com.walladog.walladog.controllers.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.BasicsSpinArrayAdapter;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Gender;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.State;
import com.walladog.walladog.models.Sterile;
import com.walladog.walladog.utils.DBAsyncTasksGet;

import java.util.ArrayList;
import java.util.List;


public class EditProductFragment extends Fragment {

    private static final String ARG_PRODUCT = "ARG_PRODUCT";
    private ArrayAdapter<Race> mRaceArrayAdapter;
    private ArrayAdapter<Category> mCategoryArrayAdapter;
    private TextView mTextName,mTextDescription,mTextPrice;
    private Spinner mSpinerCategory,mSpinnerRaces,mSpinnerSterile,mSpinnerStates,mSpinnerGender;
    private Button mButtonAdd;
    private AddProductForm mAddForm;

    private Product mProduct;

    public static EditProductFragment newInstance(Product product) {
        EditProductFragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    public EditProductFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_product, container, false);

        mTextName = (TextView) v.findViewById(R.id.edit_product_name);
        mTextDescription = (TextView) v.findViewById(R.id.edit_product_description);
        mTextPrice = (TextView) v.findViewById(R.id.edit_product_price);
        mSpinerCategory = (Spinner) v.findViewById(R.id.spin_edit_category);
        mSpinnerRaces = (Spinner) v.findViewById(R.id.spin_edit_race);
        mSpinnerStates = (Spinner) v.findViewById(R.id.spin_edit_state);
        mSpinnerSterile = (Spinner) v.findViewById(R.id.spin_edit_sterile);
        mSpinnerGender = (Spinner) v.findViewById(R.id.spin_edit_gender);
        mButtonAdd = (Button) v.findViewById(R.id.btn_edit_product);
        mAddForm=new AddProductForm();

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm())
                    sendForm();

            }
        });

        mTextName.setText(mProduct.getName());
        mTextDescription.setText(mProduct.getDescription());
        mTextPrice.setText(String.valueOf(mProduct.getPrice()));


        LoadSpinners();
        
        return v;
    }
    
    
    private Boolean validateForm(){
        return true;
    }

    private void sendForm(){

    }


    public void LoadSpinners(){
        //TODO cargar de api en splashScreen
        DBAsyncTasksGet<Race> taskGetRaces = new DBAsyncTasksGet<Race>(DBAsyncTasksGet.TASK_GET_LIST,new Race(), getActivity().getApplicationContext(),new DBAsyncTasksGet.OnItemsRecoveredFromDBListener<Race>() {
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
                        //Select item
                        BasicsSpinArrayAdapter<Race> adapter = (BasicsSpinArrayAdapter<Race>) mSpinnerRaces.getAdapter();
                        for (int position = 0; position < adapter.getCount(); position++) {
                            if(((Race)adapter.getItem(position)).getId_race() == mProduct.getRaceid()) {
                                mSpinnerRaces.setSelection(position);
                            }
                        }
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
                        BasicsSpinArrayAdapter<Category> adapter = (BasicsSpinArrayAdapter) mSpinerCategory.getAdapter();
                        for (int position = 0; position < adapter.getCount(); position++) {
                            if(((Category)adapter.getItem(position)).getId_category() == mProduct.getCategoryid()) {
                                mSpinerCategory.setSelection(position);
                            }
                        }
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
        switch (mProduct.getGender()){
            case "MAL":
                mSpinnerGender.setSelection(0);
                break;
            case "FEM":
                mSpinnerGender.setSelection(1);
                break;
            case "NON":
                mSpinnerGender.setSelection(2);
                break;
        }


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
        switch (mProduct.getStateid()){
            case 1:
                mSpinnerStates.setSelection(0);
                break;
            case 2:
                mSpinnerStates.setSelection(1);
                break;
            case 3:
                mSpinnerStates.setSelection(2);
                break;
            case 4:
                mSpinnerStates.setSelection(3);
                break;
        }

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
        if(mProduct.isSterile())
            mSpinnerSterile.setSelection(0);
        else
            mSpinnerSterile.setSelection(1);

    }


    private void snakeMsg(String s){
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
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
