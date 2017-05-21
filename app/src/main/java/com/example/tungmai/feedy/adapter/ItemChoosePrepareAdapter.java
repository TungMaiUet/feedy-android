package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.PostFeedUserActivity;
import com.example.tungmai.feedy.custominterface.GetPrepareFeedy;
import com.example.tungmai.feedy.custominterface.GetPrepareFeedyFromAdapter;
import com.example.tungmai.feedy.models.ItemPrepare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungMai on 4/9/2017.
 */

public class ItemChoosePrepareAdapter extends BaseAdapter {
    private ArrayList<ItemPrepare> arrItemPrepares;
    private LayoutInflater inflater;
    private Context context;

    private String[] arrUnit = {
            "gam", "kilogam", "ml", "lit", "gói", "cái", "củ", "trái"
    };

    public ItemChoosePrepareAdapter(Context context, ArrayList<ItemPrepare> arrItemPrepares) {
        this.arrItemPrepares = arrItemPrepares;
        inflater = LayoutInflater.from(context);
        this.context = context;
        PostFeedUserActivity postFeedUserActivity = (PostFeedUserActivity) context;
        postFeedUserActivity.setGetPrepareFeedy(new GetPrepareFeedy() {
            @Override
            public void eventPost() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return arrItemPrepares.size();
    }

    @Override
    public Object getItem(int position) {
        return arrItemPrepares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_choose_prepare, parent, false);
            viewHolder.tvPrepare = (TextView) convertView.findViewById(R.id.tv_count);
            viewHolder.edtPrepare = (EditText) convertView.findViewById(R.id.edt_content);
            viewHolder.edtQuantity = (EditText) convertView.findViewById(R.id.edt_so);
            viewHolder.spinner = (Spinner) convertView.findViewById(R.id.spinner);
            viewHolder.edtPrepare.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
//                    countPrepare.remove(position);
                    arrItemPrepares.get(position).setContent(s.toString());
                }
            });

            viewHolder.edtQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    arrItemPrepares.get(position).setQuantity(s.toString());
                }
            });

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrUnit);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            viewHolder.spinner.setAdapter(adapter);

            convertView.setTag(viewHolder);
            arrItemPrepares.get(position).setUnit(viewHolder.spinner.getSelectedItem().toString());
//            viewHolder.spinner.add
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvPrepare.setText(position + 1 + "");
//        }
        return convertView;
    }

    class ViewHolder {
        TextView tvPrepare;
        EditText edtPrepare;
        EditText edtQuantity;
        Spinner spinner;
    }
}
