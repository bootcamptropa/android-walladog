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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProfileSoldFragment extends Fragment {
    private static final String ARG_PSOLD = "ARG_PSOLD";

    private List<Product> mProductsSold;


    private SellingAdapter adapter;
    private RecyclerView recyclerView;

    public ProfileSoldFragment() {

    }

    public static ProfileSoldFragment newInstance(List<Product> products) {
        ProfileSoldFragment fragment = new ProfileSoldFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PSOLD, (Serializable) products);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductsSold = (List<Product>) getArguments().getSerializable(ARG_PSOLD);
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

        adapter = new SellingAdapter(mProductsSold,getContext());
        recyclerView.setAdapter(adapter);
    }
}
