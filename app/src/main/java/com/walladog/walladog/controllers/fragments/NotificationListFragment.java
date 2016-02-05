package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.NotificationsAdapter;
import com.walladog.walladog.models.WDNotification;
import com.walladog.walladog.models.dao.NotificationDAO;
import com.walladog.walladog.utils.NotificationDataEvent;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;


public class NotificationListFragment extends Fragment {
    public static final String ARG_NOTIFICATION_TYPE = "toread";
    public static final String ARG_NOTIFICATIONS = "notifications";
    public static final String ARG_LISTENER = "listener";

    private static final String TAG = NotificationListFragment.class.getName();

    private int mNotificationType;
    private NotificationsAdapter adapter;
    private RecyclerView recyclerView;
    private List<WDNotification> notificationsList;



    public NotificationListFragment() {
    }


    public static NotificationListFragment newInstance(int type,List<WDNotification> notifications) {
        NotificationListFragment fragment = new NotificationListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NOTIFICATION_TYPE, type);
        args.putSerializable(ARG_NOTIFICATIONS, (Serializable) notifications);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNotificationType = getArguments().getInt(ARG_NOTIFICATION_TYPE);
            notificationsList = (List<WDNotification>) getArguments().getSerializable(ARG_NOTIFICATIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification_list, container, false);

        adapter = new NotificationsAdapter(notificationsList, getContext(),mNotificationType);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.notificationList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);



        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when a NotificationDataEvent is posted
    public void onEvent(NotificationDataEvent dataItem){
        NotificationDAO ndao = new NotificationDAO(getContext());
        adapter.setItems(ndao.getNotificationsFromType(String.valueOf(mNotificationType)));
        adapter.notifyDataSetChanged();
    }
}
