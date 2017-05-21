package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.models.ItemPrepare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungMai on 4/10/2017.
 */

public class ItemPrepareAdapter extends ArrayAdapter {
    private ArrayList<ItemPrepare> arrPrepare;
    private Context context;
    private LayoutInflater inflater;

    public ItemPrepareAdapter(Context context, ArrayList<ItemPrepare> arrPrepare) {
        super(context, R.layout.item_list_prepare, arrPrepare);
        this.arrPrepare = arrPrepare;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return arrPrepare.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_prepare, parent, false);
            viewHolder.ivAdd = (ImageView) convertView.findViewById(R.id.iv_add);
            viewHolder.tvPrepare = (TextView) convertView.findViewById(R.id.tv_prepare);
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!arrPrepare.get(position).isChecked()) {
                        finalViewHolder.ivAdd.setImageResource(R.drawable.checked);
                        arrPrepare.get(position).setChecked(true);
                    } else {
                        finalViewHolder.ivAdd.setImageResource(R.drawable.add);
                        arrPrepare.get(position).setChecked(false);
                    }
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvPrepare.setText(arrPrepare.get(position).getContent() + ": " + arrPrepare.get(position).getQuantity() + " " + arrPrepare.get(position).getUnit());
        return convertView;
    }

    class ViewHolder {
        ImageView ivAdd;
        TextView tvPrepare;
    }
}
