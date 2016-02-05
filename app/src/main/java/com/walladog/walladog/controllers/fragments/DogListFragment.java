package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.DogListAdapter;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDProductService;
import com.walladog.walladog.models.responses.ProductResponse;
import com.walladog.walladog.utils.CustomSearchDialog;
import com.walladog.walladog.utils.EndRecycleViewAdapter;
import com.walladog.walladog.utils.SearchObject;
import com.walladog.walladog.utils.SpacesItemDecoration;
import com.walladog.walladog.utils.WDEventNotification;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DogListFragment extends Fragment
        implements DogListAdapter.OnPhotoClickListener,
        EndRecycleViewAdapter.ReqToLoadMoreListener {

    private static final String TAG = DogListFragment.class.getName();

    private static final String ARG_WDPRODUCTS = "ARG_WDPRODUCTS";
    private static final String ARG_SO = "ARG_SO";

    RecyclerView mRecyclerView;
    private FloatingActionButton mFab = null;

    private ProductResponse mProductResponse =null;

    private OnListItemSelectedListener mListItemListener;
    private DogListAdapter mAdapter = null;

    //Pagination
    private EndRecycleViewAdapter endRecyclerViewAdapter;
    private boolean loading = true;
    private int mOffset = 0;
    private int mLimit = 10;
    private Boolean mIsLastPage = false;
    private Boolean mIsUniquePage = false;
    StaggeredGridLayoutManager mLayoutManager;
    LinearLayout mEmptyResults;

    //Search Object
    SearchObject mSO = null;

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

    public static DogListFragment newInstance(ProductResponse products,SearchObject so) {
        DogListFragment fragment = new DogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WDPRODUCTS, (Serializable) products);
        args.putSerializable(ARG_SO, (Serializable) so);
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
            mSO = (SearchObject) getArguments().getSerializable(ARG_SO);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_dog_list, container, false);

        mEmptyResults = (LinearLayout) root.findViewById(R.id.doglist_noresults);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.masonry_grid);

        mFab = (FloatingActionButton) root.findViewById(R.id.fab);
        Button btn = (Button) root.findViewById(R.id.doglist_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCsd = new CustomSearchDialog(getActivity(), new CustomSearchDialog.SearchDialogListener() {
                    @Override
                    public void OnDialogData(SearchObject so) {
                        try {
                            reloadGridFromSearch(so);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void OnDialogCanceled() {

                    }

                });
                mCsd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mCsd.show();
            }
        });

        if(mProductResponse.getNext()!=null){
            this.mOffset=this.mOffset+this.mLimit;
        }

        if(mProductResponse.getNext()==null && mProductResponse.getPrevious()==null){
            mIsUniquePage=true;
        }

        if(mProductResponse.getResults().size()>0){
            mAdapter = new DogListAdapter(getActivity().getApplicationContext(),this,mProductResponse.getResults());
            endRecyclerViewAdapter = new EndRecycleViewAdapter(getContext(),mAdapter,this); //Adapter endless

            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.setAdapter(endRecyclerViewAdapter);
            SpacesItemDecoration decoration = new SpacesItemDecoration(16);
            mRecyclerView.addItemDecoration(decoration);
        }else{
            mEmptyResults.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }



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

    /**
     * Listener for picture clicks
     * @param position
     */
    @Override
    public void onPhotoClick(int position) {
        mListItemListener.onListItemSelected(position);
    }

    /**
     * LoadMore interface from endlessAdapter
     */
    @Override
    public void OnNeedMorePages() {
        if(mIsUniquePage){
            //TODO resolve this stupid hack
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    endRecyclerViewAdapter.onDataReady(false);
                }
            }, 2000);
        }else {
            if (mIsLastPage == false) {
                try {
                    ServiceGeneratorOAuth.createService(WDProductService.class).getSearchProductsPaginated(String.valueOf(mOffset), String.valueOf(mLimit),mSO.getLatitude(), mSO.getLongitude(),mSO.getRace(),mSO.getCategory(),mSO.getDistance())
                            .enqueue(new Callback<ProductResponse>() {
                                @Override
                                public void onResponse(Response<ProductResponse> response, Retrofit retrofit) {
                                    if (response.body().getNext() == null) {
                                        mIsLastPage = true;
                                    }
                                    mAdapter.appendItems(response.body().getResults());
                                    endRecyclerViewAdapter.onDataReady(true);
                                    mOffset = mOffset + mLimit;
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    endRecyclerViewAdapter.onDataReady(false);
                                }
                            });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                //TODO resolve this stupid hack
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        endRecyclerViewAdapter.onDataReady(false);
                    }
                }, 2000);
            }
        }
    }

    private void reloadGridFromSearch(SearchObject so) throws UnsupportedEncodingException {
        EventBus.getDefault().post(new WDEventNotification<SearchObject>(1, "Perros Seleccionados", so));
    }


    public interface OnListItemSelectedListener {
        void onListItemSelected(int position);
    }
}
