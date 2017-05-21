package com.example.tungmai.feedy.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.ItemChoosePrepareAdapter;
import com.example.tungmai.feedy.adapter.ItemPostFeedyFeedyAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.PostDataAsyncTask;
import com.example.tungmai.feedy.asynctask.PostLoadingDataAsyncTask;
import com.example.tungmai.feedy.custom.CustomImage;
import com.example.tungmai.feedy.custom.ExpandableHeightGridView;
import com.example.tungmai.feedy.custom.ExpandableHeightListView;
import com.example.tungmai.feedy.custominterface.ChooseImageFromPostAdapter;
import com.example.tungmai.feedy.custominterface.ChooseImageFromPostFeedy;
import com.example.tungmai.feedy.custominterface.GetPrepareFeedy;
import com.example.tungmai.feedy.custominterface.GetPrepareFeedyFromAdapter;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.models.ItemFeedyList;
import com.example.tungmai.feedy.models.ItemPostFeedy;
import com.example.tungmai.feedy.models.ItemPrepare;
import com.example.tungmai.feedy.models.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by TungMai on 4/9/2017.
 */

public class PostFeedUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMG_FEEDY = 23425;
    private static final int WHAT_POST_FEEDY = 542;
    private TextInputEditText txtNameFeedy;
    private TextInputEditText txtIntroFeedy;
    private ImageView ivUser;
    private Button btnChosseImage;

    private ImageView ivImageFeedy;
    private TextView tvNameUser;

    private EditText edtTimePrepare;
    private EditText edtTimeMaking;
    private Spinner spinnerLevel;
    private String arrLevel[] = {
            "Dễ",
            "Trung bình",
            "Khó"};

    private TextInputEditText txtCountPrepare;
    private Button btnOkPrepare;
    private ExpandableHeightListView lvPrepare;
    private ItemChoosePrepareAdapter itemChoosePrepareAdapter;
//    private ArrayList<String> countPrepare;
    private ArrayList<ItemPrepare> countPrepare;


    private TextInputEditText txtCountMaking;
    private Button btnOkMaking;
    private ExpandableHeightListView lvMaking;
    private ItemPostFeedyFeedyAdapter itemPostFeedyFeedyAdapter;
    private ArrayList<ItemPostFeedy> countMaking;
    private int chooseImagePosition;
    private User user;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_feedtuser);
        user = (User) getIntent().getSerializableExtra(FragmentLogin.INTENT_USER);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Đăng lên");
        countPrepare = new ArrayList<>();
        countMaking = new ArrayList<>();
        itemChoosePrepareAdapter = new ItemChoosePrepareAdapter(this, countPrepare);
        itemPostFeedyFeedyAdapter = new ItemPostFeedyFeedyAdapter(this, countMaking);
        initViews();
    }

    private void initViews() {
        ivUser = (ImageView) findViewById(R.id.iv_profile);
        Picasso.with(this).load(ConnectSever.IP_SEVER+user.getImageUser());
        tvNameUser = (TextView) findViewById(R.id.tv_name);
        tvNameUser.setText(user.getName());

        txtNameFeedy = (TextInputEditText) findViewById(R.id.edt_name);
        txtIntroFeedy = (TextInputEditText) findViewById(R.id.edt_intro);
        btnChosseImage = (Button) findViewById(R.id.btn_image);
        btnChosseImage.setOnClickListener(this);

        edtTimePrepare = (EditText) findViewById(R.id.edt_time_prepare);
        edtTimeMaking = (EditText) findViewById(R.id.edt_time_making);
        spinnerLevel = (Spinner) findViewById(R.id.spinner_choose);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrLevel);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerLevel.setAdapter(adapter);

        ivImageFeedy = (ImageView) findViewById(R.id.iv_feedy);
        txtCountPrepare = (TextInputEditText) findViewById(R.id.edt_prepare);
        btnOkPrepare = (Button) findViewById(R.id.btn_prepare);

        btnOkPrepare.setOnClickListener(this);
        lvPrepare = (ExpandableHeightListView) findViewById(R.id.lv_prepare);
        lvPrepare.setExpanded(true);
        lvPrepare.setAdapter(itemChoosePrepareAdapter);

        txtCountMaking = (TextInputEditText) findViewById(R.id.edt_making);
        btnOkMaking = (Button) findViewById(R.id.btn_making);
        btnOkMaking.setOnClickListener(this);
        lvMaking = (ExpandableHeightListView) findViewById(R.id.lv_making);
        lvMaking.setExpanded(true);
        lvMaking.setAdapter(itemPostFeedyFeedyAdapter);

        itemPostFeedyFeedyAdapter.setChooseImageFromPostAdapter(new ChooseImageFromPostAdapter() {
            @Override
            public void chooseImage(int position) {
//                Log.e("aaaa",position+"");
                chooseImagePosition = position;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_blog, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prepare:
                String countTemp = txtCountPrepare.getText().toString();
                if (countTemp.trim().isEmpty()) {
                    Toast.makeText(this, "Bạn chưa điền số nguyên liệu cần chuẩn bị", Toast.LENGTH_SHORT).show();
                    return;
                }
                countPrepare.clear();
                for (int i = 0; i < Integer.parseInt(countTemp); i++) {
                    ItemPrepare string = new ItemPrepare();
                    countPrepare.add(string);
                }
//                Log.e("aaa", countPrepare.size() + "");
//                itemChoosePrepareAdapter = new ItemChoosePrepareAdapter(this, countPrepare);
                itemChoosePrepareAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_making:
                String countTempMaking = txtCountMaking.getText().toString();
                if (countTempMaking.trim().isEmpty()) {
                    Toast.makeText(this, "Bạn chưa điền số bước cần chuẩn bị", Toast.LENGTH_SHORT).show();
                    return;
                }
                countMaking.clear();
                for (int i = 0; i < Integer.parseInt(countTempMaking); i++) {
                    ItemPostFeedy itemPostFeedy = new ItemPostFeedy("", null);
                    countMaking.add(itemPostFeedy);
                }
//                Log.e("aaa", countMaking.size() + "");
                itemPostFeedyFeedyAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_image:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG_FEEDY);
                break;
        }
    }

    private GetPrepareFeedy getPrepareFeedy;

    public void setGetPrepareFeedy(GetPrepareFeedy getPrepareFeedy) {
        this.getPrepareFeedy = getPrepareFeedy;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post:
//                Log.e("aaaaa",countPrepare.get(0).getContent());
                getPrepareFeedy.eventPost();
//
                String nameFeedy = txtNameFeedy.getText().toString().trim();
                String introFeedy = txtIntroFeedy.getText().toString().trim();
                String timePrepare = edtTimePrepare.getText().toString().trim();
                String timeMaking = edtTimeMaking.getText().toString().trim();
                String level = spinnerLevel.getSelectedItem().toString();
                Calendar calendar = Calendar.getInstance();
                String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ", " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
//


                HashMap<String, String> map = new HashMap<>();

                map.put("user_id", user.getIdToken());
                map.put("user_name", user.getName());
                map.put("user_image", user.getImageUser());
                map.put("name_feedy", nameFeedy);
                map.put("intro_feedy", introFeedy);
                map.put("time_prepare",timePrepare);
                map.put("time_making",timeMaking);
                map.put("level",level);
                map.put("time", time);
                PostLoadingDataAsyncTask loginAsyncTask = new PostLoadingDataAsyncTask(WHAT_POST_FEEDY, handler, this, ConnectSever.LINK_SERVER_POST_FEEDY_USER);
                HashMap<String, String> mapFileFeedy = new HashMap<>();
                mapFileFeedy.put("image_feedy", uriImageFeedy.getPath());

                for (int i = 0; i < countMaking.size(); i++) {
                    mapFileFeedy.put("images_making[" + i + "]", countMaking.get(i).getImageMaking());
                    map.put("content_making[" + i + "]", countMaking.get(i).getContentMaking());
                }
//
                for (int i = 0; i < countPrepare.size(); i++) {
                    map.put("prepare_content[" + i + "]", countPrepare.get(i).getContent());
                    map.put("prepare_quantity[" + i + "]", countPrepare.get(i).getQuantity());
                    map.put("prepare_unit[" + i + "]", countPrepare.get(i).getUnit());
//                    Log.e("aaaaa",countPrepare.get(i).getContent());
//                    Log.e("aaaaa",countPrepare.get(i).getQuantity());
//                    Log.e("aaaaa",countPrepare.get(i).getUnit());
                }
                loginAsyncTask.execute(map, mapFileFeedy);

                return true;
            case android.R.id.home:
//                setResult(RESULT_OK);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ChooseImageFromPostFeedy chooseImageFromPostFeedy;

    public void setChooseImageFromPostFeedy(ChooseImageFromPostFeedy chooseImageFromPostFeedy) {
        this.chooseImageFromPostFeedy = chooseImageFromPostFeedy;
    }

    private Uri uriImageFeedy;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ItemPostFeedyFeedyAdapter.RESULT_LOAD_IMG_POST_FEEDY) {
                uriImageFeedy = data.getData();
                chooseImageFromPostFeedy.chooseImage(chooseImagePosition, uriImageFeedy.getPath());
            }

            if (requestCode == RESULT_LOAD_IMG_FEEDY) {
                uriImageFeedy = data.getData();
                ivImageFeedy.setVisibility(View.VISIBLE);
                CustomImage.decodeFile(uriImageFeedy.getPath(), ivImageFeedy);
            }
//
//            Log.e(TAG, CustomImage.base64Image(imageUriUser.getPath()));
//            ivImage.setImageBitmap(CustomImage.base64ToImage(CustomImage.imageToBase64(imageUriUser.getPath())));
//            CustomImage.decodeFile(imageUriUser.getPath(), ivImage);
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
