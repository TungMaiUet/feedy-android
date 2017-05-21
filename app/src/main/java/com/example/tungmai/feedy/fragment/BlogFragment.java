package com.example.tungmai.feedy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.HomeActivity;
import com.example.tungmai.feedy.activity.PostBlogActivity;
import com.example.tungmai.feedy.adapter.ItemBlogAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.GetDataAsyncTask;
import com.example.tungmai.feedy.custominterface.LoadRefressBlog;
import com.example.tungmai.feedy.models.ItemBlog;
import com.example.tungmai.feedy.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TungMai on 3/15/2017.
 */

public class BlogFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BlogFragment";
    public static final int REQUEST_CODE_LOGIN = 76;
    private static final int WHAT_BLOG = 98;
    private static final int WHAT_BLOG_ADD = 451;
    private User user;
    private View view;
    //    private ProgressBar progressBar;
//    private LinearLayout llBlog;
//    private CardView postStatus;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayoutManager linearLayoutManager;
    private static RelativeLayout bottomLayout;

    private ArrayList<ItemBlog> arrItemBlogs;
    private ItemBlogAdapter itemBlogAdapter;

    private GetDataAsyncTask getDataAsyncTask;
    private int countAddBlog;
    private boolean isLoading;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
//            if (result.substring(1, result.length() - 1).equals("Err")) {
//                return;
//            }
//            Log.e(TAG, result);
            if (msg.what == WHAT_BLOG) {
                try {
                    JSONArray jArray = new JSONArray(result);
//                    Log.e(TAG, jArray.length()+"a");
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        String idBlog = jObject.getString("_id");

                        boolean isSave = jObject.getBoolean("is_save");
                        String content = jObject.getString("content");
                        String time = jObject.getString("time");
//                        String imagesObj = jObject.get("images");
//                        String[] images = CustomImage.getUrlImageFromUrl(imagesObj);
//                        Log.e(TAG, "kdfjsl");
                        JSONObject jsonUser = jObject.getJSONObject("user");

                        String imageProfile = jsonUser.getString("user_image");
                        String name = jsonUser.getString("user_name");
                        String userId = jsonUser.getString("user_id");

                        JSONArray arrJson = jObject.getJSONArray("images");
//                        Log.e(TAG,"aa");

                        String[] images = new String[arrJson.length()];
//                        Log.e(TAG,arrJson.length()+"");
                        for (int j = 0; j < arrJson.length(); j++)
                            images[j] = arrJson.getString(j);
                        String countLike = jObject.getString("like");
                        String countComment = jObject.getString("comment");
                        boolean isLike = jObject.getBoolean("is_like");
                        ItemBlog itemBlog = new ItemBlog(idBlog, userId, imageProfile, name, time, isSave, content, images, Integer.parseInt(countLike), Integer.parseInt(countComment), isLike);
                        arrItemBlogs.add(itemBlog);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
//                Log.e(TAG, arrItemBlogs.size() + "");
                itemBlogAdapter.notifyDataSetChanged();
//                llBlog.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.INVISIBLE);
                swipeContainer.setRefreshing(false);
                isLoading = false;
            } else if (msg.what == WHAT_BLOG_ADD) {
                try {
                    JSONArray jArray = new JSONArray(result);
//                    Log.e(TAG, jArray.length()+"a");
                    for (int i = 0; i < jArray.length(); i++) {
//
                        JSONObject jObject = jArray.getJSONObject(i);
//
                        String idBlog = jObject.getString("_id");
//                        String name = jObject.getString("name");
//                        String imageProfile = jObject.getString("profile_image");
                        boolean isSave = jObject.getBoolean("is_save");
                        String content = jObject.getString("content");
                        String time = jObject.getString("time");
//                        String imagesObj = jObject.get("images");
//                        String[] images = CustomImage.getUrlImageFromUrl(imagesObj);
//                        Log.e(TAG, "kdfjsl");
                        JSONObject jsonUser = jObject.getJSONObject("user");
                        String imageProfile = jsonUser.getString("user_image");
                        String name = jsonUser.getString("user_name");
                        String userId = jsonUser.getString("user_id");

                        JSONArray arrJson = jObject.getJSONArray("images");
                        String[] images = new String[arrJson.length()];
//                        Log.e(TAG,arrJson.length()+"");
                        for (int j = 0; j < arrJson.length(); j++)
                            images[j] = arrJson.getString(j);

                        String countLike = jObject.getString("like");
                        String countComment = jObject.getString("comment");
                        boolean isLike = jObject.getBoolean("is_like");

                        ItemBlog itemBlog = new ItemBlog(idBlog, userId, imageProfile, name, time, isSave, content, images, Integer.parseInt(countLike), Integer.parseInt(countComment), isLike);
                        arrItemBlogs.add(itemBlog);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
//                Collections.reverse(arrItemBlogs);
                itemBlogAdapter.notifyDataSetChanged();
                bottomLayout.setVisibility(View.GONE);
                isLoading = false;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blog, container, false);
        HomeActivity homeActivity = (HomeActivity) getActivity();
        isLoading = false;
        user = homeActivity.getUser();
        countAddBlog = 0;
        String url = ConnectSever.LINK_SERVER_GET_BLOG + "/" + user.getIdToken() + "/" + countAddBlog;

        arrItemBlogs = new ArrayList<>();
        itemBlogAdapter = new ItemBlogAdapter(arrItemBlogs, getContext(), user);

        getDataAsyncTask = new GetDataAsyncTask(handler, getContext(), WHAT_BLOG);
        getDataAsyncTask.execute(url);
        isLoading = true;

        homeActivity.setLoadRefressBlog(new LoadRefressBlog() {
            @Override
            public void loadRefress() {
//                Log.e(TAG,"Load");
                arrItemBlogs.clear();
                swipeContainer.setRefreshing(true);
                String url = ConnectSever.LINK_SERVER_GET_BLOG + "/" + user.getIdToken() + "/0"  ;
                getDataAsyncTask = new GetDataAsyncTask(handler, getContext(), WHAT_BLOG);
                getDataAsyncTask.execute(url);
            }
        });
        initViews();
        return view;
    }

    private void initViews() {
//        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
//        progressBar.setVisibility(View.VISIBLE);
//        llBlog = (LinearLayout) view.findViewById(R.id.ll_blog);
//        llBlog.setVisibility(View.INVISIBLE);


//        postStatus = (CardView) view.findViewById(R.id.card_view);
//        postStatus.setOnClickListener(this);
        bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);
        bottomLayout.setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemBlogAdapter);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrItemBlogs.clear();
                bottomLayout.setVisibility(View.GONE);
                countAddBlog = 0;
                isLoading = false;
                String url = ConnectSever.LINK_SERVER_GET_BLOG + "/" + user.getIdToken() + "/" + countAddBlog;
                getDataAsyncTask = new GetDataAsyncTask(handler, getContext(), WHAT_BLOG);
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
        String url = ConnectSever.LINK_SERVER_GET_BLOG + "/" + user.getIdToken() + "/" + countAddBlog;
//        Log.e(TAG, countAddBlog + "aa");
        getDataAsyncTask = new GetDataAsyncTask(handler, getContext(), WHAT_BLOG_ADD);
        getDataAsyncTask.execute(url);
        isLoading = true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view:
                Intent intent = new Intent(getActivity(), PostBlogActivity.class);
                intent.putExtra(FragmentLogin.INTENT_USER, user);
                startActivityForResult(intent, REQUEST_CODE_LOGIN);

                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==getActivity().RESULT_OK){
////            if(requestCode==REQUEST_CODE_LOGIN){
//                Log.e(TAG,"Load");
//                swipeContainer.setRefreshing(true);
//                String url = ConnectSever.LINK_SERVER_GET_BLOG + "?id=" + user.getIdToken();
//                getDataAsyncTask = new GetDataAsyncTask(handler, getContext(), WHAT_BLOG);
//                getDataAsyncTask.execute(url);
////            }
//        }
//    }
}
