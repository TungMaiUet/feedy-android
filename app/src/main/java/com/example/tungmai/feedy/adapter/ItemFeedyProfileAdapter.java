package com.example.tungmai.feedy.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.DeleteDataAsyncTask;
import com.example.tungmai.feedy.custominterface.OnClickItemRecyclerView;
import com.example.tungmai.feedy.models.ItemFeedyList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/20/2017.
 */

public class ItemFeedyProfileAdapter extends RecyclerView.Adapter<ItemFeedyProfileAdapter.ViewHolder> {
    private static final int WHAT_DELETE_FEEDY_PROFILE = 9816;
    private ArrayList<ItemFeedyList> arrItemFeedyLists;
    private Context context;
    private LayoutInflater inflater;

    private OnClickItemRecyclerView onClickItemRecyclerView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void setOnClickItemRecyclerView(OnClickItemRecyclerView onClickItemRecyclerView) {
        this.onClickItemRecyclerView = onClickItemRecyclerView;
    }

    public ItemFeedyProfileAdapter(ArrayList<ItemFeedyList> arrItemFeedyLists, Context context) {
        this.arrItemFeedyLists = arrItemFeedyLists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemFeedyProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_feedy_profile, parent, false);
        return new ItemFeedyProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemFeedyProfileAdapter.ViewHolder holder, final int position) {
        final ItemFeedyList itemFeedyList = arrItemFeedyLists.get(position);
        Picasso.with(context).load(ConnectSever.IP_SEVER + itemFeedyList.getImageFeedy()).resize(300, 300).centerCrop().into(holder.ivFeedy);
        holder.tvFeedy.setText(itemFeedyList.getNameFeedy());
        Picasso.with(context).load(ConnectSever.IP_SEVER + itemFeedyList.getImageUser()).resize(300, 300).centerCrop().into(holder.ivUser);
        holder.tvUser.setText(itemFeedyList.getNameUser());

        holder.ivFeedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemRecyclerView.onClick(position);

            }
        });

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(context, holder.ivMore);

                popup.inflate(R.menu.menu_popup_feedy_profile);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:

                                DeleteDataAsyncTask deleteDataAsyncTask = new DeleteDataAsyncTask(WHAT_DELETE_FEEDY_PROFILE, handler, (Activity) context);
                                deleteDataAsyncTask.execute(ConnectSever.LINK_REMOVE_FEEDY_PROFILE + "/" + itemFeedyList.getIdFeedy());
                                arrItemFeedyLists.remove(position);
                                notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return arrItemFeedyLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFeedy;
        private TextView tvFeedy;
        private ImageView ivUser;
        private TextView tvUser;
        private ImageView ivMore;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFeedy = (ImageView) itemView.findViewById(R.id.image);
            tvFeedy = (TextView) itemView.findViewById(R.id.tvFeedy);
            ivUser = (ImageView) itemView.findViewById(R.id.image_profile);
            tvUser = (TextView) itemView.findViewById(R.id.tvUser);
            ivMore = (ImageView) itemView.findViewById(R.id.iv_popup);

        }

    }
}
