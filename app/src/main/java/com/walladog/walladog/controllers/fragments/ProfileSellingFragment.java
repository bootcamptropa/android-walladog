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

public class ProfileSellingFragment extends Fragment {
    public static final String ARG_PSELLING = "ARG_PSELLING";

    private List<Product> mProductsSelling;

    private SellingAdapter adapter;
    private RecyclerView recyclerView;

    public ProfileSellingFragment() {

    }

    public static ProfileSellingFragment newInstance(List<Product> pSelling) {
        ProfileSellingFragment fragment = new ProfileSellingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PSELLING, (Serializable) pSelling);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductsSelling = (List<Product>) getArguments().getSerializable(ARG_PSELLING);
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

        if(mProductsSelling==null){
            mProductsSelling = new ArrayList<>();
        }
        adapter = new SellingAdapter(mProductsSelling,getContext(),getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
    }
}
