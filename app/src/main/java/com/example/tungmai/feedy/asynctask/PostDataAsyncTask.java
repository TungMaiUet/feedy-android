package com.example.tungmai.feedy.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tungmai.feedy.activity.LoginActivity;
import com.example.tungmai.feedy.api.ConnectSever;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by TungMai on 3/12/2017.
 */

public class PostDataAsyncTask extends AsyncTask<HashMap, Void, String> {
    private static final String TAG = "PostDataAsyncTask";

    private int what;
    private Handler handler;
    private Activity context;
    private String url;
    private boolean isDialog;

    public PostDataAsyncTask(int what, Handler handler, Activity context, String url) {
        this.what = what;
        this.handler = handler;
        this.context = context;
        this.url = url;
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
    }
}
