package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.PostFeedUserActivity;
import com.example.tungmai.feedy.custom.CustomImage;
import com.example.tungmai.feedy.custominterface.ChooseImageFromPostAdapter;
import com.example.tungmai.feedy.custominterface.ChooseImageFromPostFeedy;
import com.example.tungmai.feedy.custominterface.GetPrepareFeedy;
import com.example.tungmai.feedy.models.ItemPostFeedy;

import java.util.ArrayList;

/**
 * Created by TungMai on 4/10/2017.
 */

public class ItemPostFeedyFeedyAdapter extends BaseAdapter {
    public static final int RESULT_LOAD_IMG_POST_FEEDY = 980;
    private ArrayList<ItemPostFeedy> countPrepare;
    private LayoutInflater inflater;
    private Context context;
    private ChooseImageFromPostAdapter chooseImageFromPostAdapter;

    public void setChooseImageFromPostAdapter(ChooseImageFromPostAdapter chooseImageFromPostAdapter) {
        this.chooseImageFromPostAdapter = chooseImageFromPostAdapter;
    }

    public ItemPostFeedyFeedyAdapter(Context context, ArrayList<ItemPostFeedy> arrayList) {
        this.countPrepare = arrayList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        PostFeedUserActivity postFeedUserActivity = (PostFeedUserActivity) context;
        postFeedUserActivity.setChooseImageFromPostFeedy(new ChooseImageFromPostFeedy() {

            @Override
            public void chooseImage(int position, String pathImage) {
                countPrepare.get(position).setImageMaking(pathImage);
                notifyDataSetChanged();
            }
        });

        postFeedUserActivity.setGetPrepareFeedy(new GetPrepareFeedy() {
            @Override
            public void eventPost() {
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getCount() {
//        Log.e("bbb", countPrepare.size() + "a");
        return countPrepare.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return countPrepare.get(position);
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
            convertView = inflater.inflate(R.layout.item_making_feedyuser, parent, false);
            viewHolder.tvPrepare = (TextView) convertView.findViewById(R.id.tv_count);
            viewHolder.edtPrepare = (EditText) convertView.findViewById(R.id.edt_content);
            viewHolder.btnImage = (Button) convertView.findViewById(R.id.btn_choose_image);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.image);

            viewHolder.btnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    chooseImageFromPostAdapter.chooseImage(position);
                    ((PostFeedUserActivity) context).startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG_POST_FEEDY);
                }
            });

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
                    countPrepare.get(position).setContentMaking(s.toString());
//                    notifyDataSetChanged();
//                    countPrepare.add(position, s.toString());
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvPrepare.setText(position + 1 + "");
        viewHolder.edtPrepare.setText(countPrepare.get(position).getContentMaking());
        if (countPrepare.get(position).getImageMaking() != null) {
            viewHolder.ivImage.setVisibility(View.VISIBLE);
            viewHolder.ivImage.setImageURI(Uri.parse(countPrepare.get(position).getImageMaking()));
//            CustomImage.decodeFile(countPrepare.get(position).getImageMaking(), viewHolder.ivImage);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvPrepare;
        EditText edtPrepare;
        Button btnImage;
        ImageView ivImage;
    }
}