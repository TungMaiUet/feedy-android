package com.example.tungmai.feedy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.FeedyUserActivity;
import com.example.tungmai.feedy.activity.HomeActivity;
import com.example.tungmai.feedy.activity.PostFeedUserActivity;
import com.example.tungmai.feedy.adapter.ItemFeedyAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.GetDataAsyncTask;
import com.example.tungmai.feedy.custominterface.OnClickItemRecyclerView;
import com.example.tungmai.feedy.models.ItemFeedyList;
import com.example.tungmai.feedy.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TungMai on 3/23/2017.
 */

public class FeedyFragment extends Fragment implements View.OnClickListener {

    private static final int WHAT_LIST_FEEDY = 879;
    public static final String ID_FEEDY_USER = "id_feedy_user";
    private static final int WHAT_LIST_FEEDY_ADD = 5432;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private RelativeLayout bottomLayout;

    private int countAddBlog;
    private boolean isLoading;
    private GetDataAsyncTask getDataAsyncTask;
    private GridLayoutManager linearLayoutManager;

    private FloatingActionButton floatingActionButton;
    private ArrayList<ItemFeedyList> arrItemFeedyLists;
    private ItemFeedyAdapter itemFeedyAdapter;
    private User user;
    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            if (msg.what == WHAT_LIST_FEEDY) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultRes = jsonObject.getString("result");

                    String message = jsonObject.getString("message");
//                Log.e("aaaa", message);
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
//                    arrItemFeedyLists.
//                    Collections.reverse(arrItemFeedyLists);
                        itemFeedyAdapter.notifyDataSetChanged();
                        swipeContainer.setRefreshing(false);
                        isLoading = false;
                    } else {
                        Toast.makeText(getContext(), "Err", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == WHAT_LIST_FEEDY_ADD) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultRes = jsonObject.getString("result");

                    String message = jsonObject.getString("message");
//                Log.e("aaaa", message);
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
//                    arrItemFeedyLists.
//                    Collections.reverse(arrItemFeedyLists);
                        itemFeedyAdapter.notifyDataSetChanged();
                        bottomLayout.setVisibility(View.GONE);
                        isLoading = false;
                    } else {
                        Toast.makeText(getContext(), "Err", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

//            Log.e("xxx",result);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feedy, container, false);
        arrItemFeedyLists = new ArrayList<>();
        itemFeedyAdapter = new ItemFeedyAdapter(arrItemFeedyLists, getContext());
        HomeActivity homeActivity = (HomeActivity) getActivity();
        user = homeActivity.getUser();

        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask(handle, getContext(), WHAT_LIST_FEEDY);
        getDataAsyncTask.execute(ConnectSever.LINK_SEVER_GET_FEEDY_USER + "/0");
        initViews();
        return view;
    }

    private void initViews() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemFeedyAdapter);
        itemFeedyAdapter.setOnClickItemRecyclerView(new OnClickItemRecyclerView() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), FeedyUserActivity.class);
                intent.putExtra(FragmentLogin.INTENT_USER, user);
                intent.putExtra(ID_FEEDY_USER, arrItemFeedyLists.get(position).getIdFeedy());
                startActivity(intent);
            }
        });

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addFeedy);
        floatingActionButton.setOnClickListener(this);

        bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrItemFeedyLists.clear();
                bottomLayout.setVisibility(View.GONE);
                countAddBlog = 0;
                isLoading = false;
                String url = ConnectSever.LINK_SEVER_GET_FEEDY_USER + "/" + countAddBlog;
                getDataAsyncTask = new GetDataAsyncTask(handle, getContext(), WHAT_LIST_FEEDY);
                getDataAsyncTask.execute(url);
                isLoading = true;
            }
        });

        implementScrollListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFeedy:
                Intent intent = new Intent(getActivity(), PostFeedUserActivity.class);
                intent.putExtra(FragmentLogin.INTENT_USER, user);
//                intent.putExtra(INTENT_FEEDY_ID, user);
                startActivity(intent);
                break;
        }
    }

    private boolean userScrolled = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private void implementScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
//
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,
                                   int dy) {

                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager
                        .findFirstVisibleItemPosition();

                if (userScrolled
                        && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;
//                    Log.e(TAG,"fdsa");
                    if (!isLoading) {
                        addBlogs();
                        bottomLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

        });

    }

    private void addBlogs() {
        countAddBlog++;
        isLoading = false;
        String url = ConnectSever.LINK_SEVER_GET_FEEDY_USER + "/" + countAddBlog;
//        Log.e(TAG, countAddBlog + "aa");
        getDataAsyncTask = new GetDataAsyncTask(handle, getContext(), WHAT_LIST_FEEDY_ADD);
        getDataAsyncTask.execute(url);
        isLoading = true;

    }
}
