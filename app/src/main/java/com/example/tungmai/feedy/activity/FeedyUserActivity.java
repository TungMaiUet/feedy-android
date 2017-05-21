package com.example.tungmai.feedy.activity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.ItemMakingAdapter;
import com.example.tungmai.feedy.adapter.ItemPrepareAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.GetDataAsyncTask;
import com.example.tungmai.feedy.asynctask.PostDataAsyncTask;
import com.example.tungmai.feedy.custom.ExpandableHeightListView;
import com.example.tungmai.feedy.dialog.ListPreparDialog;
import com.example.tungmai.feedy.fragment.FeedyFragment;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.fragment.ItemMaking;
import com.example.tungmai.feedy.models.ItemFeedyList;
import com.example.tungmai.feedy.models.ItemPrepare;
import com.example.tungmai.feedy.models.User;
import com.example.tungmai.feedy.splite.Database;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TungMai on 4/10/2017.
 */

public class FeedyUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int WHAT_GET_FEEDY_USER = 683;
    private static final int WHAT_VALUATION = 9872;
    private ImageView ivFeedy;
    private Toolbar toolbar;
    private TextView tvNameFeedy;
    private TextView tvStar;
    private TextView tvCountValuation;
    private TextView tvTimePrepare;
    private TextView tvTimeMaking;
    private TextView tvLevel;
    private ImageView ivProfileUser;
    private TextView tvNameUser;
    private TextView tvIntroFeedy;
    private Spinner spinner;
    private Button btnAddListMarket;
    private Button btnAddFeedy;

    private ExpandableHeightListView lvPrepare;
    private ArrayList<ItemPrepare> arrPrepare;
    private ItemPrepareAdapter itemPrepareAdapter;


    private ExpandableHeightListView lvMaking;
    private ArrayList<ItemMaking> arrItemMakings;
    private ItemMakingAdapter itemMakingAdapter;


    private Integer arrChoose[] = {2, 3, 4, 5, 6};
    private int currentSpinner;
    private boolean valuation;
    private int valuationUser;
    private int valuationVote;
    private double valuationFeedy;

    private String imageFeedy;
    private String userIdFeedy;
    private String imageUserFeedy;

    private boolean isSaveFeedy;

    private User user;
    private String idFeedyUser;
    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String resultRes = jsonObject.getString("result");
                String message = jsonObject.getString("message");
                if (resultRes.equals("done")) {
                    JSONArray jsonArray = new JSONArray(message);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String idFeedy = jsonObject1.getString("_id");
                        String nameFeedy = jsonObject1.getString("name_feedy");
                        imageFeedy = jsonObject1.getString("image_feedy");
                        String timePrepare = jsonObject1.getString("time_prepare");
                        String timeMaking = jsonObject1.getString("time_making");
                        String level = jsonObject1.getString("level");
                        String time = jsonObject1.getString("time");
                        String introFeedy = jsonObject1.getString("intro_feedy");

                        JSONArray jsonArrayValuation = jsonObject1.getJSONArray("valuation_feedy");
                        valuationVote = jsonArrayValuation.length();
                        valuation = false;
                        for (int j = 0; j < valuationVote; j++) {
                            JSONObject jsonObjectPrepare = jsonArrayValuation.getJSONObject(j);
                            String valuationFeedyUser = jsonObjectPrepare.getString("user_id");
                            int vote = jsonObjectPrepare.getInt("valuation_vote");
                            if (valuationFeedyUser.equals(user.getIdToken())) {
                                valuation = true;
                                valuationUser = vote;
                            }
                            valuationFeedy = (valuationFeedy + vote);
                        }

                        valuationFeedy = valuationFeedy / valuationVote;
//                        if (valuationFeedy % 1 > 5) valuationFeedy = (int) valuationFeedy / 1 + 1;
//                        else{
//
//                        }

//                        int valuationFeedyTemp = (int) Math.round(valuationFeedy * 10);
//                        Double temp = Double.valueOf(valuationFeedyTemp/10);
//                        Log.e("aaaaaaaa", temp + "");
                        initStar(valuationFeedy);


                        JSONObject jsonObjectUser = jsonObject1.getJSONObject("user");
                        userIdFeedy = jsonObjectUser.getString("user_id");
                        String nameUser = jsonObjectUser.getString("user_name");
                        imageUserFeedy = jsonObjectUser.getString("user_image");

                        JSONArray jArrayPrepare = (JSONArray) jsonObject1.get("prepare");

                        if (jArrayPrepare != null) {
                            arrPrepare.clear();
                            for (int j = 0; j < jArrayPrepare.length(); j++) {
                                Log.e("xxxx", j + "," + jArrayPrepare.length());
                                JSONObject jsonObjectPrepare = jArrayPrepare.getJSONObject(j);
                                String prepareId = jsonObjectPrepare.getString("_id");
                                String prepareContent = jsonObjectPrepare.getString("prepare_content");
                                String prepare_quantity = jsonObjectPrepare.getString("prepare_quantity");
                                String prepare_unit = jsonObjectPrepare.getString("prepare_unit");
//                                arrPrepare.add(jArrayPrepare.getString(j));
                                ItemPrepare itemPrepare = new ItemPrepare(prepareId, prepareContent, prepare_quantity, prepare_unit, false);
                                arrPrepare.add(itemPrepare);
                            }
//                            Log.e("aaa",arrPrepare.get(1).getContent()+"");
                            itemPrepareAdapter.notifyDataSetChanged();
                        }
//                        Log.e("xxxx", "fsdfa");
//
                        JSONArray jArrayMaking = (JSONArray) jsonObject1.get("making");
                        if (jArrayMaking != null) {
                            arrItemMakings.clear();
                            for (int j = 0; j < jArrayMaking.length(); j++) {
                                JSONObject object = jArrayMaking.getJSONObject(j);
                                String content = object.getString("content");
                                String image = object.getString("images");
                                ItemMaking itemMaking = new ItemMaking(content, image);
                                arrItemMakings.add(itemMaking);
                            }
                            itemMakingAdapter.notifyDataSetChanged();
                        }

                        Picasso.with(getBaseContext()).load(ConnectSever.IP_SEVER + imageFeedy).into(ivFeedy);
                        tvNameFeedy.setText(nameFeedy);
                        collapsingToolbarLayout.setTitle(nameFeedy);
//                        tvStar.setText(valuationFeedy + "");
                        tvCountValuation.setText("Có " + valuationVote + " lượt đánh giá");
                        tvTimePrepare.setText(timePrepare);
                        tvTimeMaking.setText(timeMaking);
                        tvLevel.setText(level);
                        Picasso.with(getBaseContext()).load(ConnectSever.IP_SEVER + imageUserFeedy).into(ivProfileUser);
                        tvNameUser.setText(nameUser);
                        tvIntroFeedy.setText(introFeedy);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Err", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Handler handleValuation = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedy_user_activity);
        user = (User) getIntent().getSerializableExtra(FragmentLogin.INTENT_USER);
        idFeedyUser = getIntent().getStringExtra(FeedyFragment.ID_FEEDY_USER);

        arrPrepare = new ArrayList<>();
        itemPrepareAdapter = new ItemPrepareAdapter(this, arrPrepare);

        arrItemMakings = new ArrayList<>();
        itemMakingAdapter = new ItemMakingAdapter(this, arrItemMakings);

        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask(handle, this, WHAT_GET_FEEDY_USER);
        getDataAsyncTask.execute(ConnectSever.LINK_SERVER_GET_FEEDY_USER + idFeedyUser);

        if (checkSaveFeedy()) isSaveFeedy = true;
        else isSaveFeedy = false;


        initViews();
    }

    private boolean checkSaveFeedy() {
        Database database = new Database(this);
        ArrayList<ItemFeedyList> arrItemFeedyListsDatabase = database.getDataListFeedy();
        int arrItemFeedyListsDatabaseLength = arrItemFeedyListsDatabase.size();
        for (int i = 0; i < arrItemFeedyListsDatabaseLength; i++) {
            if (idFeedyUser.equals(arrItemFeedyListsDatabase.get(i).getIdFeedy()))
                return true;
        }
        return false;
    }

    private void initViews() {
        valuationFeedy = 0;
        valuationVote = 0;

        ivFeedy = (ImageView) findViewById(R.id.iv_image_fisrt);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

        tvNameFeedy = (TextView) findViewById(R.id.tv_name_feedy);
//        tvStar = (TextView) findViewById(R.id.tv_cout_star);
        tvCountValuation = (TextView) findViewById(R.id.tv_count_valuation);
        tvTimePrepare = (TextView) findViewById(R.id.tv_time_prepare);
        tvTimeMaking = (TextView) findViewById(R.id.tv_time_making);
        tvLevel = (TextView) findViewById(R.id.tv_level);
        ivProfileUser = (ImageView) findViewById(R.id.iv_profile_user);
        tvNameUser = (TextView) findViewById(R.id.tv_name_user);
        tvIntroFeedy = (TextView) findViewById(R.id.tv_intro_feedy);

        spinner = (Spinner) findViewById(R.id.spinner_choose);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, arrChoose);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        currentSpinner = 4;
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int mselection = Integer.parseInt(spinner.getSelectedItem().toString());
                for (int i = 0; i < arrPrepare.size(); i++) {
                    double quantity = Double.parseDouble(arrPrepare.get(i).getQuantity().trim());
                    double quantityTemp = quantity / currentSpinner;
                    double quantityResult = (quantityTemp * mselection);
                    arrPrepare.get(i).setQuantity(quantityResult + "");
                }
                currentSpinner = mselection;
                itemPrepareAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        lvPrepare = (ExpandableHeightListView) findViewById(R.id.lv_prepare);
        lvPrepare.setExpanded(true);
        lvPrepare.setAdapter(itemPrepareAdapter);

        lvMaking = (ExpandableHeightListView) findViewById(R.id.lv_making);
        lvMaking.setExpanded(true);
        lvMaking.setAdapter(itemMakingAdapter);

        btnAddListMarket = (Button) findViewById(R.id.btn_add_list);
        btnAddListMarket.setOnClickListener(this);

        btnAddFeedy = (Button) findViewById(R.id.btn_add_feedy);
        if (isSaveFeedy) btnAddFeedy.setText("Món ăn này đã được thêm");
        else
            btnAddFeedy.setOnClickListener(this);


    }

    private void initStar(double star) {
        double starLate = star % 1;
        if (starLate < 0.5) {
            star--;
        } else if (starLate > 0.5) {
            star++;
        }

        ImageView ivStar1 = (ImageView) findViewById(R.id.iv_star_1);
        ImageView ivStar2 = (ImageView) findViewById(R.id.iv_star_2);
        ImageView ivStar3 = (ImageView) findViewById(R.id.iv_star_3);
        ImageView ivStar4 = (ImageView) findViewById(R.id.iv_star_4);
        ImageView ivStar5 = (ImageView) findViewById(R.id.iv_star_5);

        ivStar1.setImageDrawable(getResources().getDrawable(R.drawable.star));
        ivStar2.setImageDrawable(getResources().getDrawable(R.drawable.star));
        ivStar3.setImageDrawable(getResources().getDrawable(R.drawable.star));
        ivStar4.setImageDrawable(getResources().getDrawable(R.drawable.star));
        ivStar5.setImageDrawable(getResources().getDrawable(R.drawable.star));
        switch ((int) (star / 1)) {
            case 1:
                ivStar1.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                break;
            case 2:
                ivStar1.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar2.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                break;
            case 3:
                ivStar1.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar2.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar3.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                break;
            case 4:
                ivStar1.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar2.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar3.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar4.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                break;
            case 5:
                ivStar1.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar2.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar3.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar4.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                ivStar5.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_star));
                break;
        }

        if (star % 1 == 0.5) {
            switch ((int) (star / 1)) {
                case 0:
                    ivStar1.setImageDrawable(getResources().getDrawable(R.drawable.star_half_empty));
                    break;
                case 1:
                    ivStar2.setImageDrawable(getResources().getDrawable(R.drawable.star_half_empty));
                    break;
                case 2:
                    ivStar3.setImageDrawable(getResources().getDrawable(R.drawable.star_half_empty));
                    break;
                case 3:
                    ivStar4.setImageDrawable(getResources().getDrawable(R.drawable.star_half_empty));
                    break;
                case 4:
                    ivStar5.setImageDrawable(getResources().getDrawable(R.drawable.star_half_empty));
                    break;
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_list:
                ArrayList<ItemPrepare> arrItemPrepares = new ArrayList<>();
                for (int i = 0; i < arrPrepare.size(); i++) {
                    if (arrPrepare.get(i).isChecked()) {
                        String id = arrPrepare.get(i).getId();
                        String content = arrPrepare.get(i).getContent();
                        String quantity = arrPrepare.get(i).getQuantity();
                        String unit = arrPrepare.get(i).getUnit();
                        arrItemPrepares.add(new ItemPrepare(id, content, quantity, unit, false));
                    }
                }
                Database database = new Database(this);
                database.insertDataPrepare(arrItemPrepares);
                Toast.makeText(this, "Đã thêm vào danh sách đi chợ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_add_feedy:
                if (!isSaveFeedy) {
                    Database databaseFeedy = new Database(this);
                    databaseFeedy.insertDataFeedy(idFeedyUser, tvNameFeedy.getText().toString(), imageFeedy, userIdFeedy, tvNameUser.getText().toString(), imageUserFeedy);
                    Toast.makeText(this, "Đã thêm vào danh sách nấu ăṇ", Toast.LENGTH_SHORT).show();
                    btnAddFeedy.setText("Món ăn này đã được thêm");
                    isSaveFeedy = true;
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dialog_prepare, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_prepare:
                ListPreparDialog listPreparDialog = new ListPreparDialog();
//                listPreparDialog.setStyle(android.R.style.Theme_Holo_Dialog_NoActionBar,R.);
                listPreparDialog.show(getFragmentManager(), "PrepareDialog");
                break;
            case R.id.menu_star:
                String[] s = {"1 điểm", "2 điểm", "3 điểm", "4 điểm", "5 điểm",};
                ArrayAdapter<String> adp = new ArrayAdapter<String>(FeedyUserActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, s);

                final Spinner sp = new Spinner(FeedyUserActivity.this);
                sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                sp.setAdapter(adp);

                final AlertDialog.Builder builder = new AlertDialog.Builder(FeedyUserActivity.this);
                builder.setTitle("Đánh giá");
                if (!valuation) {
                    builder.setMessage("Bạn chưa đánh giá");
                    builder.setView(sp);
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id_feedy", idFeedyUser);
                            map.put("id_user", user.getIdToken());
                            map.put("star", (sp.getSelectedItemPosition() + 1) + "");
                            PostDataAsyncTask postDataAsyncTask = new PostDataAsyncTask(WHAT_VALUATION, handleValuation, FeedyUserActivity.this, ConnectSever.LINK_ADD_VALUATION);
                            postDataAsyncTask.execute(map, null);
                            valuation = true;
                            valuationUser = sp.getSelectedItemPosition() + 1;
                            if (valuationVote == 0) {
                                initStar(valuationUser);
                                valuationVote = 1;
                            } else {
                                initStar((valuationFeedy + valuationUser) / 2);
                                valuationVote++;
                            }
                            tvCountValuation.setText("Có " + valuationVote + " lượt đánh giá");
                        }
                    });
                    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                } else {
                    builder.setMessage("Bạn đã đánh giá:" + valuationUser + " điểm");
                }
                builder.create().show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
