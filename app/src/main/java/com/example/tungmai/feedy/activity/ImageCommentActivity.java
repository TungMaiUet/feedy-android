package com.example.tungmai.feedy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.ImageCommentAdapter;
import com.example.tungmai.feedy.adapter.ItemBlogAdapter;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/12/2017.
 */

public class ImageCommentActivity extends AppCompatActivity {

    private ArrayList<String> arrImage;

    private RecyclerView recyclerView;
    private ImageCommentAdapter imageCommentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_comment);

        arrImage = getIntent().getStringArrayListExtra(ItemBlogAdapter.ARRAY_IMAGE_BLOG);
        imageCommentAdapter = new ImageCommentAdapter(this,arrImage);
        initViews();
    }

    private void initViews() {


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(imageCommentAdapter);

    }
}
