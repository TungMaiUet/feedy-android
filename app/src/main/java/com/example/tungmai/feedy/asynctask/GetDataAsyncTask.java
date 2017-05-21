package com.example.tungmai.feedy.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tungmai.feedy.api.ConnectSever;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by TungMai on 3/17/2017.
 */

public class GetDataAsyncTask extends AsyncTask<String ,Void,String>{
    private static final String TAG = "GetDataBlogAsyncTask";

    private int what;
    private Handler handler;
    private Context context;

    public GetDataAsyncTask(Handler handler, Context context,int what) {
        this.handler = handler;
        this.context = context;
        this.what = what;
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
//            URLConnection connection = url.openConnection();
//            InputStream stream = connection.getInputStream();
//            int c = stream.read();
//            while (c!=-1){
//                builder.append((char)c);
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
    }
}
