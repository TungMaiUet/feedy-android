package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.fragment.ItemMaking;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungMai on 4/10/2017.
 */

public class ItemMakingAdapter extends ArrayAdapter {
    private ArrayList<ItemMaking> arrItemMakings;
    private Context context;
    private LayoutInflater inflater;

    public ItemMakingAdapter(Context context, ArrayList<ItemMaking> arrItemMakings) {
        super(context, R.layout.item_list_making, arrItemMakings);
        this.arrItemMakings = arrItemMakings;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrItemMakings.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_making, parent, false);
            viewHolder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_making);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemMaking itemMaking = arrItemMakings.get(position);
        viewHolder.tvCount.setText("Bước " + (position + 1) + ": ");
        viewHolder.tvContent.setText(itemMaking.getContent());
        Log.e("making",ConnectSever.IP_SEVER + itemMaking.getUrlImage());
        Picasso.with(context).load(ConnectSever.IP_SEVER + itemMaking.getUrlImage()).into(viewHolder.ivImage);

        return convertView;
    }

    class ViewHolder {
        TextView tvCount;
        TextView tvContent;
        ImageView ivImage;
    }
}
