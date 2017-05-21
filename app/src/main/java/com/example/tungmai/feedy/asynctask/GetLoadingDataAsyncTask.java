package com.example.tungmai.feedy.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by TungMai on 5/12/2017.
 */

public class GetLoadingDataAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetDataBlogAsyncTask";

    private int what;
    private Handler handler;
    private Context context;
    private ProgressDialog progressDialog;

    public GetLoadingDataAsyncTask(Handler handler, Context context, int what) {
        this.handler = handler;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        this.what = what;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {

//        String string = ConnectSever.getConnect(params[0]);
        String link = params[0];
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(link);
//            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String temp="";
            while ((temp = in.readLine()) != null) {
                builder.append(temp);
            }
//            InputStream stream = connection.getInputStream();
//            int c = stream.read();
//            while (c != -1) {
//                builder.append((char) c);
//                c = stream.read();
//            }
//            Log.e(TAG,builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String string="aaa";
        return builder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Message message = new Message();
        message.what = what;
        message.obj = s;
        handler.sendMessage(message);
        progressDialog.dismiss();
    }
}
