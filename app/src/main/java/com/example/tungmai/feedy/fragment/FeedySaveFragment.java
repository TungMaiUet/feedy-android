package com.example.tungmai.feedy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.FeedyUserActivity;
import com.example.tungmai.feedy.activity.HomeActivity;
import com.example.tungmai.feedy.adapter.ItemFeedySaveAdapter;
import com.example.tungmai.feedy.custominterface.OnClickItemRecyclerView;
import com.example.tungmai.feedy.models.ItemFeedyList;
import com.example.tungmai.feedy.models.User;
import com.example.tungmai.feedy.splite.Database;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/20/2017.
 */

public class FeedySaveFragment extends Fragment {

    private View view;
    private User user;
    private ArrayList<ItemFeedyList> arrItemFeedyLists;
    private ItemFeedySaveAdapter itemFeedySaveAdapter;
    private Database database;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_save_feedy, container, false);
        HomeActivity homeActivity = (HomeActivity) getActivity();
        user = homeActivity.getUser();
        database = new Database(getContext());
        arrItemFeedyLists = database.getDataListFeedy();
        itemFeedySaveAdapter = new ItemFeedySaveAdapter(arrItemFeedyLists, getContext());

        initViews();
        return view;
    }

    private void initViews() {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemFeedySaveAdapter);
        itemFeedySaveAdapter.setOnClickItemRecyclerView(new OnClickItemRecyclerView() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), FeedyUserActivity.class);
                intent.putExtra(FragmentLogin.INTENT_USER, user);
                intent.putExtra(FeedyFragment.ID_FEEDY_USER, arrItemFeedyLists.get(position).getIdFeedy());
                startActivity(intent);
            }
        });


    }

}
