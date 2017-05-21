package com.example.tungmai.feedy.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.tungmai.feedy.api.ConnectSever;

import java.util.HashMap;

/**
 * Created by TungMai on 5/12/2017.
 */

public class PostLoadingDataAsyncTask extends AsyncTask<HashMap, Void, String> {
    private static final String TAG = "PostDataAsyncTask";

    private int what;
    private Handler handler;
    private ProgressDialog progressDialog;
    private Activity context;
    private String url;
    private boolean isDialog;

    public PostLoadingDataAsyncTask(int what, Handler handler, Activity context, String url) {
        this.what = what;
        this.handler = handler;
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(HashMap... params) {
        String string = null;
        if(params[1]==null) {
            string = ConnectSever.getPostConnect(params[0], url);
        }else{
            string = ConnectSever.getPostFileConnect(params[0],params[1],url);
        }
//        Log.e(TAG,params[0].values().toString());
//        Log.e(TAG,params[1].values().toString());
        return string;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            Message message = new Message();
            message.what = what;
            message.obj = s;
            handler.sendMessage(message);
        }
        progressDialog.dismiss();
    }
}

