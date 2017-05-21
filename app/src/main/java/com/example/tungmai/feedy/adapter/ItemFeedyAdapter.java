package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.custominterface.OnClickItemRecyclerView;
import com.example.tungmai.feedy.models.ItemFeedyList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TungMai on 4/7/2017.
 */

public class ItemFeedyAdapter extends RecyclerView.Adapter<ItemFeedyAdapter.ViewHolder> {
    private ArrayList<ItemFeedyList> arrItemFeedyLists;
    private Context context;
    private LayoutInflater inflater;
    private OnClickItemRecyclerView onClickItemRecyclerView;

    public void setOnClickItemRecyclerView(OnClickItemRecyclerView onClickItemRecyclerView) {
        this.onClickItemRecyclerView = onClickItemRecyclerView;
    }

    public ItemFeedyAdapter(ArrayList<ItemFeedyList> arrItemFeedyLists, Context context) {
        this.arrItemFeedyLists = arrItemFeedyLists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemFeedyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_feedy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ItemFeedyList itemFeedyList = arrItemFeedyLists.get(position);
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

        public ViewHolder(View itemView) {
            super(itemView);
            ivFeedy = (ImageView) itemView.findViewById(R.id.image);
            tvFeedy = (TextView) itemView.findViewById(R.id.tvFeedy);
            ivUser = (ImageView) itemView.findViewById(R.id.image_profile);
            tvUser = (TextView) itemView.findViewById(R.id.tvUser);
        }
    }
}
