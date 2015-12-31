package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
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
import com.walladog.walladog.adapters.MasonryAdapter;
import com.walladog.walladog.utils.SpacesItemDecoration;

public class DogListFragment extends Fragment implements SearchView.OnQueryTextListener,MasonryAdapter.OnPhotoClickListener {
    private static final String TAG = DogListFragment.class.getName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView mRecyclerView;
    private SearchView mSearchView = null;
    private FloatingActionButton mFab = null;

    private String mParam1;
    private String mParam2;

    private OnListItemSelectedListener mListItemListener;
    private MasonryAdapter mAdapter = null;
    private MasonryAdapter.OnPhotoClickListener mPhotoListener;

    public DogListFragment() {

    }

    public static DogListFragment newInstance(String param1, String param2) {
        DogListFragment fragment = new DogListFragment();
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
        View root =  inflater.inflate(R.layout.fragment_dog_list, container, false);

        mFab = (FloatingActionButton) root.findViewById(R.id.fab);
        int mColorWalladog = Color.parseColor("#4e91df");
        mFab.setBackgroundTintList(ColorStateList.valueOf(mColorWalladog));

        //TODO: only in Lolipop
        //int mColorWhite = Color.parseColor("#4e91df");
        /*mFab.setImageTintList(ColorStateList.valueOf(mColorWhite));*/

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

        mAdapter = new MasonryAdapter(getActivity().getApplicationContext(),this);
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
