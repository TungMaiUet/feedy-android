package com.example.tungmai.feedy.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.custom.CustomImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungMai on 3/16/2017.
 */

public class ImagePostBlogAdapter extends ArrayAdapter {
    private ArrayList<Uri> arrUri;
    private LayoutInflater inflater;


    public ImagePostBlogAdapter(Context context, ArrayList<Uri> objects) {
        super(context, R.layout.item_image_post_blog, objects);
        this.arrUri = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrUri.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_image_post_blog,parent,false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.ivCancel = (ImageView) convertView.findViewById(R.id.iv_cancel);
            viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrUri.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Uri uri = arrUri.get(position);
        CustomImage.decodeFile(uri.getPath(),viewHolder.ivImage);
//        viewHolder.ivImage.setImageBitmap(CustomImage.base64ToImage(CustomImage.imageToBase64(uri.getPath())));
        return convertView;
    }

    class ViewHolder {
        ImageView ivImage;
        ImageView ivCancel;
    }
}
