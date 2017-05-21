package com.example.tungmai.feedy.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tungmai.feedy.R;

/**
 * Created by TungMai on 5/18/2017.
 */

public class ValuationDialog extends DialogFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setTitle("Đánh giá");
//        view= inflater.inflate(R.layout.dialog_valuation,container,false);
        return view;
    }
}
