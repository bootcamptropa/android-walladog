package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.Button;

import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;
import com.walladog.walladog.R;
import com.walladog.walladog.adapters.DogListAdapter;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDProductService;
import com.walladog.walladog.models.responses.ProductResponse;
import com.walladog.walladog.utils.CustomSearchDialog;
import com.walladog.walladog.utils.SearchObject;
import com.walladog.walladog.utils.SpacesItemDecoration;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DogListFragment extends Fragment
        implements SearchView.OnQueryTextListener,
        DogListAdapter.OnPhotoClickListener, EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    private static final String TAG = DogListFragment.class.getName();

    private static final String ARG_WDPRODUCTS = "ARG_WDPRODUCTS";
    private static final String ARG_RACE = "ARG_RACE";
    private static final String ARG_CATEGORY = "ARG_CATEGORY";
    private static final String ARG_LATITUDE = "ARG_LATITUDES";
    private static final String ARG_LONGITUDE = "ARG_LONGITUDE";
    private static final String ARG_DISTANCE = "ARG_DISTANCE";

    RecyclerView mRecyclerView;
    private SearchView mSearchView = null;
    private FloatingActionButton mFab = null;

    private List<Product> mProducts =null;
    private int mDistance;
    private int mCategory;
    private int mRace;
    private double mLatitude;
    private double mLogitude;

    private ProductResponse mProductResponse =null;

    private OnListItemSelectedListener mListItemListener;
    private DogListAdapter mAdapter = null;

    //Pagination
    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private boolean loading = true;
    int grid_column_count = 2;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int mOffset = 0;
    private int mLimit = 10;
    private Boolean mIsLastPage = false;
    StaggeredGridLayoutManager mLayoutManager;

    //Dialog search
    CustomSearchDialog mCsd = null;

    public DogListFragment() {

    }

    public static DogListFragment newInstance(List<Product> products) {
        DogListFragment fragment = new DogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WDPRODUCTS, (Serializable) products);
        fragment.setArguments(args);
        return fragment;
    }

    public static DogListFragment newInstance(ProductResponse products) {
        DogListFragment fragment = new DogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WDPRODUCTS, (Serializable) products);
        fragment.setArguments(args);
        return fragment;
    }

    public static DogListFragment newInstance(ProductResponse products,int category,int race) {
        DogListFragment fragment = new DogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WDPRODUCTS, (Serializable) products);
        fragment.setArguments(args);
        return fragment;
    }

    public static DogListFragment newInstance(ProductResponse products,int category,int race, double latitude,double logitude,int distance) {
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
            //mProducts = (List<Product>) getArguments().getSerializable(ARG_WDPRODUCTS);
            mProductResponse = (ProductResponse) getArguments().getSerializable(ARG_WDPRODUCTS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_dog_list, container, false);

        mFab = (FloatingActionButton) root.findViewById(R.id.fab);

        Button btn = (Button) root.findViewById(R.id.doglist_search);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCsd = new CustomSearchDialog(getActivity(), new CustomSearchDialog.SearchDialogListener() {
                    @Override
                    public void OnDialogData(SearchObject so) {
                        Log.v(TAG,"Search Object");
                        Log.v(TAG, String.valueOf(so.getRace()));
                        Log.v(TAG, String.valueOf(so.getDistance()));
                        Log.v(TAG, String.valueOf(so.getCategory()));
                    }

                    @Override
                    public void OnDialogCanceled() {

                    }

                });
                mCsd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mCsd.show();
            }
        });


        //mSearchView = (SearchView) root.findViewById(R.id.search_txt);
        //mSearchView.setOnQueryTextListener(this);
        //mSearchView.setQueryHint("Que buscas?");

        if(mProductResponse.getNext()!=null){
            this.mOffset=this.mOffset+this.mLimit;
        }

        /*mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });*/

        mRecyclerView = (RecyclerView) root.findViewById(R.id.masonry_grid);

        //mAdapter = new DogListAdapter(getActivity().getApplicationContext(),this,mProducts);
        mAdapter = new DogListAdapter(getActivity().getApplicationContext(),this,mProductResponse.getResults());
        endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(getContext(),mAdapter,this); //Adapter endless

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(endlessRecyclerViewAdapter);
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

    @Override
    public void onLoadMoreRequested() {
        if(mIsLastPage==false){
            try {
                ServiceGeneratorOAuth.createService(WDProductService.class).getProductsPaginated(mOffset,mLimit)
                        .enqueue(new Callback<ProductResponse>() {
                            @Override
                            public void onResponse(Response<ProductResponse> response, Retrofit retrofit) {
                                if(response.body().getNext()==null){
                                    mIsLastPage=true;
                                }
                                mAdapter.appendItems(response.body().getResults());
                                endlessRecyclerViewAdapter.onDataReady(true);
                                mOffset=mOffset+mLimit;
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.v(TAG,"Fallo en la conexion a la api");
                                endlessRecyclerViewAdapter.onDataReady(false);
                            }
                        });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            mOffset=0;
            mIsLastPage=true;
            try {
                ServiceGeneratorOAuth.createService(WDProductService.class).getProductsPaginated(mOffset,mLimit)
                        .enqueue(new Callback<ProductResponse>() {
                            @Override
                            public void onResponse(Response<ProductResponse> response, Retrofit retrofit) {
                                if(response.body().getNext()==null){
                                    mIsLastPage=true;
                                }
                                mAdapter.appendItems(response.body().getResults());
                                endlessRecyclerViewAdapter.onDataReady(true);
                                mOffset=mOffset+mLimit;
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.v(TAG,"Fallo en la conexion a la api");
                                endlessRecyclerViewAdapter.onDataReady(false);
                            }
                        });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    //New search
    public interface OnNewSearchListener {
        void onNewSearchRecived(SearchObject so);
    }

    public interface OnListItemSelectedListener {
        void onListItemSelected(int position);
    }
}
