package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.DogListAdapter;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.utils.SpacesItemDecoration;

import java.io.Serializable;
import java.util.List;

public class DogListFragment extends Fragment implements SearchView.OnQueryTextListener,DogListAdapter.OnPhotoClickListener {
    private static final String TAG = DogListFragment.class.getName();

    private static final String ARG_WDPRODUCTS = "ARG_WDPRODUCTS";

    RecyclerView mRecyclerView;
    private SearchView mSearchView = null;
    private FloatingActionButton mFab = null;

    private List<Product> mParam1=null;

    private OnListItemSelectedListener mListItemListener;
    private DogListAdapter mAdapter = null;

    public DogListFragment() {

    }

    public static DogListFragment newInstance(List<Product> products) {
        DogListFragment fragment = new DogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WDPRODUCTS, (Serializable) products);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (List<Product>) getArguments().getSerializable(ARG_WDPRODUCTS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_dog_list, container, false);

        mFab = (FloatingActionButton) root.findViewById(R.id.fab);

        mSearchView = (SearchView) root.findViewById(R.id.search_txt);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint("Que buscas?");

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.masonry_grid);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new DogListAdapter(getActivity().getApplicationContext(),this);
        mRecyclerView.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  OnListItemSelectedListener){
            mListItemListener= (OnListItemSelectedListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement OnListItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListItemListener=null;
    }

    //Search listeners
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.v(TAG, "Submited text : " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.v(TAG, "Query text change: "+newText);
        return false;
    }

    //Photo listener
    @Override
    public void onPhotoClick(int position) {
        Log.v(TAG,"Photo click listener at : "+String.valueOf(position));
        mListItemListener.onListItemSelected(position);
    }

    public interface OnListItemSelectedListener {
        void onListItemSelected(int position);
    }
}
