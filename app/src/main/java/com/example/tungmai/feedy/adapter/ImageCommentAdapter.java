package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/12/2017.
 */

public class ImageCommentAdapter extends RecyclerView.Adapter<ImageCommentAdapter.ViewHolder> {
    private ArrayList<String> arrImage;
    private LayoutInflater inflater;
    private Context context;

    public ImageCommentAdapter(Context context, ArrayList<String> arrImage) {
        this.arrImage = arrImage;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_image_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(ConnectSever.IP_SEVER + arrImage.get(position)).placeholder(R.drawable.backgroup_loading_image).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
