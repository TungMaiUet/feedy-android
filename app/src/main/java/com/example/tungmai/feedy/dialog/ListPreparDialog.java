package com.example.tungmai.feedy.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.ItemDialogPrepareAdapter;
import com.example.tungmai.feedy.adapter.ItemPrepareAdapter;
import com.example.tungmai.feedy.models.ItemPrepare;
import com.example.tungmai.feedy.splite.Database;

import java.util.ArrayList;

/**
 * Created by TungMai on 5/14/2017.
 */

public class ListPreparDialog extends DialogFragment {

    private View view;
    private Toolbar toolbar;
    private ListView lvPrepare;
    private ArrayList<ItemPrepare> arrItemPrepares;
    private ItemDialogPrepareAdapter itemPrepareAdapter;

    private Database database;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_prepare, container, false);
        arrItemPrepares = new ArrayList<>();
        database = new Database(getActivity().getBaseContext());
        getData();
        itemPrepareAdapter = new ItemDialogPrepareAdapter(getActivity().getBaseContext(), arrItemPrepares);
        initViews();
        return view;
    }

    private void initViews() {
        toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_remove_prepare:
                        database.deleteDataPrepare();
                        arrItemPrepares.clear();
                        itemPrepareAdapter.notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.menu_dialog_remove);
        toolbar.setTitle("Danh sách đi chợ");
        lvPrepare = (ListView) view.findViewById(R.id.lv_prepare);
        lvPrepare.setAdapter(itemPrepareAdapter);
    }

    public void getData() {
        arrItemPrepares = database.getDataPrepare();
    }

}
