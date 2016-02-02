package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.SellingAdapter;
import com.walladog.walladog.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProfileSellingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SellingAdapter adapter;
    private RecyclerView recyclerView;
    private static List<Product> productList;

    public ProfileSellingFragment() {
        // Required empty public constructor
    }

    public static ProfileSellingFragment newInstance(String param1, String param2) {
        ProfileSellingFragment fragment = new ProfileSellingFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile_selling, container, false);



        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.sellingList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        productList = new ArrayList<Product>();
        char c = 'A';
        for (byte i = 0; i < 20; i++) {
            Product model = new Product();
            model.setName("Product name"+String.valueOf(c++));
            model.setGender("Male");
            productList.add(model);
        }
        adapter = new SellingAdapter(productList,getContext());
        recyclerView.setAdapter(adapter);
    }
}
