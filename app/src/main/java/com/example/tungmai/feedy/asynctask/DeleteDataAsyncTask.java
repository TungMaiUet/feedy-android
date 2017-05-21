package com.example.tungmai.feedy.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.tungmai.feedy.api.ConnectSever;

import java.util.HashMap;

/**
 * Created by TungMai on 5/20/2017.
 */

public class DeleteDataAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "PostDataAsyncTask";

    private int what;
    private Handler handler;
    private Activity context;
    private boolean isDialog;

    public DeleteDataAsyncTask(int what, Handler handler, Activity context) {
        this.what = what;
        this.handler = handler;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String string = null;
        String url = params[0];
        string = ConnectSever.delete(url);
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
    }
}
