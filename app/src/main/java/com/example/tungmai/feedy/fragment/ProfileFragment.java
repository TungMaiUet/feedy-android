package com.example.tungmai.feedy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.FeedyUserActivity;
import com.example.tungmai.feedy.activity.HomeActivity;
import com.example.tungmai.feedy.adapter.ItemFeedyProfileAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.GetDataAsyncTask;
import com.example.tungmai.feedy.custominterface.OnClickItemRecyclerView;
import com.example.tungmai.feedy.models.ItemFeedyList;
import com.example.tungmai.feedy.models.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/17/2017.
 */

public class ProfileFragment extends Fragment {
    private static final int WHAT_FEEDY_PROFILE = 765;
    private View view;

    private Toolbar toolbar;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvGender;
    private TextView tvDate;
    private ImageView ivProfile;
    private RecyclerView recyclerView;
    private TextView addFeedy;

    private ArrayList<ItemFeedyList> arrItemFeedyLists;
    private ItemFeedyProfileAdapter itemFeedyAdapter;
    private int countAdd;
    private RelativeLayout bottomLayout;
    private NestedScrollView nestedScroll;

    private User user;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String resultRes = jsonObject.getString("result");
//                Log.e("aaaa", resultRes);
                String message = jsonObject.getString("message");
                if (resultRes.equals("done")) {
                    JSONArray jsonArray = new JSONArray(message);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String idFeedy = jsonObject1.getString("feedy_id");
                        String nameId = jsonObject1.getString("user_id");
                        String nameFeedy = jsonObject1.getString("name_feedy");
                        String imageFeedy = jsonObject1.getString("image_feedy");
                        String nameUser = jsonObject1.getString("name_user");
                        String imageUser = jsonObject1.getString("image_user");
                        ItemFeedyList itemFeedyList = new ItemFeedyList(idFeedy, imageFeedy, nameFeedy, nameId, imageUser, nameUser);
                        arrItemFeedyLists.add(itemFeedyList);
                    }
                    itemFeedyAdapter.notifyDataSetChanged();
                    bottomLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Err", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        HomeActivity homeActivity = (HomeActivity) getActivity();
        user = homeActivity.getUser();
        countAdd = 0;
        arrItemFeedyLists = new ArrayList<>();
        itemFeedyAdapter = new ItemFeedyProfileAdapter(arrItemFeedyLists, getContext());

        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask(handler, getContext(), WHAT_FEEDY_PROFILE);
        getDataAsyncTask.execute(ConnectSever.LINK_SEVER_GET_FEEDY_USER_PROFILE + user.getIdToken() + "/0");

        initViews();
        return view;
    }

    private void initViews() {
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setTitle("Thông tin cá nhân");
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvEmail = (TextView) view.findViewById(R.id.tv_email);
        tvGender = (TextView) view.findViewById(R.id.tv_sex);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        ivProfile = (ImageView) view.findViewById(R.id.iv_profile);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        addFeedy = (TextView) view.findViewById(R.id.addFeedy);
        bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);
        nestedScroll = (NestedScrollView) view.findViewById(R.id.nestedScroll);

        bottomLayout.setVisibility(View.VISIBLE);

        addFeedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomLayout.setVisibility(View.VISIBLE);
                arrItemFeedyLists.clear();
                countAdd++;
                GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask(handler, getContext(), WHAT_FEEDY_PROFILE);
                getDataAsyncTask.execute(ConnectSever.LINK_SEVER_GET_FEEDY_USER_PROFILE + user.getIdToken() + "/" + countAdd);
            }
        });


        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemFeedyAdapter);
        itemFeedyAdapter.setOnClickItemRecyclerView(new OnClickItemRecyclerView() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), FeedyUserActivity.class);
                intent.putExtra(FragmentLogin.INTENT_USER, user);
                intent.putExtra(FeedyFragment.ID_FEEDY_USER, arrItemFeedyLists.get(position).getIdFeedy());
                startActivity(intent);
            }
        });

        initData();

    }

    private void initData() {

        tvName.setText("Họ tên: " + user.getName());
        tvEmail.setText("Email: " + user.getEmail());
        String gender = "";
        if (user.isGender()) gender = "Nam";
        else gender = "Nữ";
        tvGender.setText("Giới tính: " + gender);
        tvDate.setText("Ngày sinh: " + user.getBirth());
        Picasso.with(getContext()).load(ConnectSever.IP_SEVER + user.getImageUser()).into(ivProfile);
    }
}
