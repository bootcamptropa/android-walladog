package com.walladog.walladog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.walladog.walladog.R;
import com.walladog.walladog.models.WDNotification;
import com.walladog.walladog.models.dao.NotificationDAO;
import com.walladog.walladog.utils.NotificationDataEvent;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * Created by hadock on 5/01/16.
 *
 */

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = NotificationsAdapter.class.getName();

    private List<WDNotification> items;
    private SparseBooleanArray selectedItems;
    private int mNotificationType;

    private static final int FOOTER_VIEW = 1;

    private Context context;

    public NotificationsAdapter(List<WDNotification> modelData, Context context,int notificationType) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.mNotificationType=notificationType;
        this.context = context;
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;

        if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcy_sell_itemfooter, viewGroup, false);
            FooterViewHolder vh = new FooterViewHolder(v);
            return vh;
        }

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcy_notification_item, viewGroup, false);
        ListItemViewHolder vh = new ListItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        try {
            if (viewHolder instanceof ListItemViewHolder) {
                ListItemViewHolder vh = (ListItemViewHolder) viewHolder;
                WDNotification model = items.get(position);
                vh.title.setText(String.valueOf(model.getTitle()));
                String fdate = new SimpleDateFormat("dd-MM-yyyy").format(model.getCreationDate());
                vh.send_date.setText(fdate);

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.parseColor("#5e7974"))
                        .borderWidthDp(5)
                        .cornerRadiusDp(10)
                        .oval(false)
                        .build();

                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(20);
                int height = 300 + randomInt;
                String url = "http://loremflickr.com/300/"+String.valueOf(height)+"/dog";
                Picasso.with(context)
                        .load(url)
                        .transform(transformation)
                        .placeholder(R.drawable.progress_animation2)
                        .into(vh.img_product);
                viewHolder.itemView.setActivated(selectedItems.get(position, false));
                vh.bindView(position);
            } else  {
                FooterViewHolder vh = (FooterViewHolder) viewHolder;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        if (items.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }
        // Add extra view to show the footer view
        return items.size() + 1;
    }

    public class ListItemViewHolder extends ViewHolder {
        TextView title;
        TextView send_date;
        ImageView img_product;

        public ListItemViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_noti_title);
            send_date = (TextView) itemView.findViewById(R.id.txt_noti_date);
            img_product = (ImageView) itemView.findViewById(R.id.img_grid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_down_fast);
                    itemView.startAnimation(anim);*/
                    WDNotification noti = items.get(getMyPosition());
                    if (mNotificationType == 0)
                        noti.setRead(true);
                    else
                        noti.setRead(false);
                    NotificationDAO nDAO = new NotificationDAO(context);
                    nDAO.update((long) noti.getId(), noti);
                    items.remove(getMyPosition());
                    EventBus.getDefault().post(new NotificationDataEvent("Notifications", mNotificationType));
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==items.size()){
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    public class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the item
                    Log.v(TAG, "Item clicked");
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define elements of a row here
        private int myPosition=0;
        public ViewHolder(View itemView) {
            super(itemView);
            // Find view by ID and initialize here
        }

        public void bindView(int position) {
            // bindView() method to implement actions
            myPosition=position;
        }

        public int getMyPosition() {
            return myPosition;
        }
        public void setMyPosition(int myPosition) {
            this.myPosition = myPosition;
        }
    }

    public void setItems(List<WDNotification> items) {
        this.items = items;
    }

}