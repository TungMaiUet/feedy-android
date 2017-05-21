package com.example.tungmai.feedy.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.ItemTipsAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.GetDataAsyncTask;
import com.example.tungmai.feedy.models.ItemTipsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/18/2017.
 */

public class TipsFragment extends Fragment {

    private static final int WHAT_LIST_TIPS = 4312;
    private static final int WHAT_TIPS_ADD = 9082;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private RelativeLayout bottomLayout;


    private ArrayList<ItemTipsList> arrItemTipsLists;
    private ItemTipsAdapter itemTipsAdapter;


    private int countAddBlog;
    private boolean isLoading;
    private GetDataAsyncTask getDataAsyncTask;
    private LinearLayoutManager linearLayoutManager;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            if (msg.what == WHAT_LIST_TIPS) {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        String id = jObject.getString("tips_id");
                        String title = jObject.getString("title");
                        String image = jObject.getString("image");
                        String description = jObject.getString("description");
                        ItemTipsList itemTipsList = new ItemTipsList(id, title, image, description);
                        arrItemTipsLists.add(itemTipsList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                itemTipsAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
                isLoading = false;
            } else if (msg.what == WHAT_TIPS_ADD) {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        String id = jObject.getString("tips_id");
                        String title = jObject.getString("title");
                        String image = jObject.getString("image");
                        String description = jObject.getString("description");
                        ItemTipsList itemTipsList = new ItemTipsList(id, title, image, description);
                        arrItemTipsLists.add(itemTipsList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                itemTipsAdapter.notifyDataSetChanged();
                bottomLayout.setVisibility(View.GONE);
                isLoading = false;
            }
        }


    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tips, container, false);
        arrItemTipsLists = new ArrayList<>();
        itemTipsAdapter = new ItemTipsAdapter(arrItemTipsLists, getContext());

        getDataAsyncTask = new GetDataAsyncTask(handle, getContext(), WHAT_LIST_TIPS);
        getDataAsyncTask.execute(ConnectSever.LINK_SEVER_GET_TIPS_LIST + "/0");
        initViews();
        return view;
    }

    private void initViews() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemTipsAdapter);


        bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrItemTipsLists.clear();
                bottomLayout.setVisibility(View.GONE);
                countAddBlog = 0;
                isLoading = false;
                String url = ConnectSever.LINK_SEVER_GET_TIPS_LIST + "/" + countAddBlog;
                getDataAsyncTask = new GetDataAsyncTask(handle, getContext(), WHAT_LIST_TIPS);
                getDataAsyncTask.execute(url);
                isLoading = true;
            }
        });

        implementScrollListener();

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
        String url = ConnectSever.LINK_SEVER_GET_TIPS_LIST + "/" + countAddBlog;
//        Log.e(TAG, countAddBlog + "aa");
        getDataAsyncTask = new GetDataAsyncTask(handle, getContext(), WHAT_TIPS_ADD);
        getDataAsyncTask.execute(url);
        isLoading = true;

    }

}
