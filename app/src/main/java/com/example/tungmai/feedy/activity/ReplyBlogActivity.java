package com.example.tungmai.feedy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.models.User;

/**
 * Created by TungMai on 3/24/2017.
 */

public class ReplyBlogActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private EditText edtComment;
    private Button btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_blog);

//        user = (User) getIntent().getSerializableExtra(FragmentLogin.INTENT_USER);
//        idBlog = getIntent().getStringExtra(FragmentLogin.INTENT_ITEM_BLOG);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bình luận");

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(itemCommentAdapter);


        edtComment = (EditText) findViewById(R.id.edt_comment);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
