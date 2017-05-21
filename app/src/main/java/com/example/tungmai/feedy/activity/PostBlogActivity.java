package com.example.tungmai.feedy.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.ImagePostBlogAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.PostLoadingDataAsyncTask;
import com.example.tungmai.feedy.custom.CustomImage;
import com.example.tungmai.feedy.custom.Emoji;
import com.example.tungmai.feedy.custom.ExpandableHeightGridView;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.models.User;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by TungMai on 3/15/2017.
 */

public class PostBlogActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMG_POST_BLOG = 341;
    private static final String TAG = "PostBlogActivity";
    private static final int WHAT_POST_BLOG = 4123;
    private ImageView ivProfile;
    private TextView tvName;
    private EditText edtInput;
    private Button btnPhoto;
    private ExpandableHeightGridView expandableHeightGridView;
    private ImagePostBlogAdapter imagePostBlogAdapter;
    private ArrayList<Uri> imageUriUser;
    private User user;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_blog);
        user = (User) getIntent().getSerializableExtra(FragmentLogin.INTENT_USER);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Đăng lên diễn đàn");

        idUser = user.getIdToken();

        imageUriUser = new ArrayList<>();
        imagePostBlogAdapter = new ImagePostBlogAdapter(this, imageUriUser);
        initViews();
    }

    private void initViews() {
        ivProfile = (ImageView) findViewById(R.id.iv_profile);
        Picasso.with(this).load(ConnectSever.IP_SEVER + user.getImageUser()).into(ivProfile);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(user.getName());

        expandableHeightGridView = (ExpandableHeightGridView) findViewById(R.id.grid_view);
        expandableHeightGridView.setExpanded(true);
        expandableHeightGridView.setAdapter(imagePostBlogAdapter);

        btnPhoto = (Button) findViewById(R.id.btn_add_image);
        btnPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imageUriUser.add(uri);
                }

                imagePostBlogAdapter.notifyDataSetChanged();

            } else {
                Toast.makeText(getBaseContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_blog, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post:
//                Log.e(TAG,"fsdfa");
                String content = edtInput.getText().toString().trim();
                Calendar calendar = Calendar.getInstance();
                String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ", " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);

                HashMap<String, String> map = new HashMap<>();
//                String image = "";
//                for (int i = 0; i < imageUriUser.size(); i++) {
//                    image += CustomImage.imageToBase64(imageUriUser.get(i).getPath()) + ";";
//                }
                map.put("user_id", idUser);
                map.put("user_name", user.getName());
                map.put("user_image", user.getImageUser());
                map.put("content", content);
                map.put("time", time);

                PostLoadingDataAsyncTask loginAsyncTask = new PostLoadingDataAsyncTask(WHAT_POST_BLOG, handler, this, ConnectSever.LINK_SERVER_POST_LOGIN);
                HashMap<String, String> mapFile = new HashMap<>();
                if (imageUriUser.size() == 0) {
                    loginAsyncTask.execute(map, null);
                } else {
                    for (int i = 0; i < imageUriUser.size(); i++) {
                        mapFile.put("images" + i, imageUriUser.get(i).getPath());
                    }
                    loginAsyncTask.execute(map, mapFile);
                }


                return true;
            case android.R.id.home:
//                setResult(RESULT_OK);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setResult(RESULT_OK);
            finish();
//            String result = msg.obj.toString();
//            if (msg.what == WHAT_POST_BLOG) {
//
//            }
        }
    };

    @Override
    public void onBackPressed() {
//        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }
}
