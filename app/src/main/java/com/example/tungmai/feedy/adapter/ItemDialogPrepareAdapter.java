package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.models.ItemPrepare;
import com.example.tungmai.feedy.splite.Database;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/16/2017.
 */

public class ItemDialogPrepareAdapter extends ArrayAdapter {
    private ArrayList<ItemPrepare> arrPrepare;
    private Context context;
    private LayoutInflater inflater;
    private Database database;

    public ItemDialogPrepareAdapter(Context context, ArrayList<ItemPrepare> arrPrepare) {
        super(context, R.layout.item_dialog_list_prepare, arrPrepare);
        this.arrPrepare = arrPrepare;
        this.context = context;
        database = new Database(this.context);
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
            convertView = inflater.inflate(R.layout.item_dialog_list_prepare, parent, false);
            viewHolder.llList = (LinearLayout) convertView.findViewById(R.id.ll_list_dialog_prepare);
            viewHolder.ivAdd = (ImageView) convertView.findViewById(R.id.iv_add);
            viewHolder.tvPrepare = (TextView) convertView.findViewById(R.id.tv_prepare);
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.llList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!arrPrepare.get(position).isChecked()) {
                        finalViewHolder.ivAdd.setImageResource(R.drawable.checked);
                        arrPrepare.get(position).setChecked(true);
                        database.updateDatePrepare(arrPrepare.get(position).getId(),true);
                    } else {
                        finalViewHolder.ivAdd.setImageResource(R.drawable.add);
                        arrPrepare.get(position).setChecked(false);
                        database.updateDatePrepare(arrPrepare.get(position).getId(),false);
                    }
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (arrPrepare.get(position).isChecked()) {
            viewHolder.ivAdd.setImageResource(R.drawable.checked);
        } else {
            viewHolder.ivAdd.setImageResource(R.drawable.add);
        }

        viewHolder.tvPrepare.setText(arrPrepare.get(position).getContent() + ": " + arrPrepare.get(position).getQuantity() + " " + arrPrepare.get(position).getUnit());
        return convertView;
    }

    class ViewHolder {
        LinearLayout llList;
        ImageView ivAdd;
        TextView tvPrepare;
    }
}
