package com.example.tungmai.feedy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.TipsActivity;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.models.ItemBlog;
import com.example.tungmai.feedy.models.ItemTipsList;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/19/2017.
 */

public class ItemTipsAdapter extends RecyclerView.Adapter<ItemTipsAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<ItemTipsList> arrItemTipsLists;
    private Context context;

    public ItemTipsAdapter(ArrayList<ItemTipsList> arrItemTipsLists, Context context) {
        this.arrItemTipsLists = arrItemTipsLists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_tips, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemTipsList itemTipsList = arrItemTipsLists.get(position);

        Glide.with(context).load(ConnectSever.IP_SEVER + itemTipsList.getImage()).into(holder.ivImage);
        holder.tvTitle.setText(itemTipsList.getTitle());
        holder.tvDescription.setText(itemTipsList.getDescription());
        holder.ll_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((Activity) context), TipsActivity.class);
                intent.putExtra(TipsActivity.INTENT_TIPS, itemTipsList.getId());
                ((Activity) context).startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrItemTipsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvTitle;
        private TextView tvDescription;
        private LinearLayout ll_tips;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            ll_tips = (LinearLayout) itemView.findViewById(R.id.ll_tips);
        }
    }
}
