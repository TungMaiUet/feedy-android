package com.example.tungmai.feedy.api;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.tungmai.feedy.models.ItemPostFeedy;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TungMai on 3/12/2017.
 */

public class ConnectSever {
        public static final String IP_SEVER = "https://feedyandroid.herokuapp.com";
//    public static final String IP_SEVER = "http://192.168.0.102:3000";
    public static final String LINK_SERVER_LOGIN = IP_SEVER + "/users/login";
    public static final String LINK_SERVER_LOGIN_FORGET = IP_SEVER + "/users/login/forget";
    public static final String LINK_SERVER_REGISTER = IP_SEVER + "/users/register";
    public static final String LINK_SERVER_POST_LOGIN = IP_SEVER + "/blog/postblog";
    public static final String LINK_SERVER_GET_BLOG = IP_SEVER + "/blog/home";

    public static final String LINK_SERVER_POST_FEEDY_USER = IP_SEVER + "/feedy/feedyuser";
    public static final String LINK_SERVER_GET_FEEDY_USER = IP_SEVER + "/feedy/getfeedyuser/";

    private static final String TAG = "ConnectSever";
    public static final String LINK_ADD_LIKE_BLOG = IP_SEVER + "/blog/like/add";
    public static final String LINK_REMOVE_LIKE_BLOG = IP_SEVER + "/blog/like/remove";
    public static final String LINK_GET_COMMENT_BLOG = IP_SEVER + "/blog/comment/get";
    public static final String LINK_SEVER_GET_FEEDY_USER = IP_SEVER + "/feedy/feedyuser";
    public static final String LINK_ADD_SAVE_BLOG = IP_SEVER + "/blog/save/add";
    public static final String LINK_ADD_REMOVE_BLOG = IP_SEVER + "/blog/save/remove";
    public static final String LINK_ADD_LIKE_COMMENT = IP_SEVER + "/blog/comment/like/add";
    public static final String LINK_REMOVE_LIKE_COMMENT = IP_SEVER + "/blog/comment/like/remove";
    public static final String LINK_SEVER_GET_FEEDY_USER_PROFILE = IP_SEVER + "/feedy/getfeedyofuser/";
    public static final String LINK_ADD_VALUATION = IP_SEVER + "/feedy/addvaluation/";
    public static final String LINK_SEVER_GET_TIPS_LIST = IP_SEVER + "/tips/listtips";
    public static final String LINK_SERVER_GET_TIPS = IP_SEVER + "/tips/gettips/";
    public static final String LINK_REMOVE_FEEDY_PROFILE = IP_SEVER + "/feedy/feedy";


    public static String getPostFileConnect(HashMap<String, String> map, HashMap<String, String> mapFile, String url) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);

            MultipartEntity entity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            Charset chars = Charset.forName("UTF-8");
//            File file = new File(mapFile);
//            entity.addPart("profileimage", new FileBody(file));
//entity.isChunked()
            for (HashMap.Entry<String, String> e : map.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
//                Log.e(TAG,key+";")
                entity.addPart(key, new StringBody(value, chars));
            }
            for (HashMap.Entry<String, String> e : mapFile.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
                File file = new File(value);
                entity.addPart(key, new FileBody(file));
            }
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost,
                    localContext);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent(), "UTF-8"));

            String sResponse = reader.readLine();
            return sResponse;
        } catch (Exception e) {
            // something went wrong. connection with the server error
        }
        return null;
    }

    public static String postDataFeedy(HashMap<String, String> map, HashMap<String, String> mapImage, HashMap<String, String> mapContentMaking, HashMap<String, String> mapPrepare, String url) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);

            MultipartEntity entity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);


            for (HashMap.Entry<String, String> e : map.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
                entity.addPart(key, new StringBody(value));
            }

            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost,
                    localContext);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent(), "UTF-8"));

            String sResponse = reader.readLine();
            return sResponse;
        } catch (Exception e) {
            // something went wrong. connection with the server error
        }
        return null;
    }

    public static String getPostConnect(HashMap<String, String> map, String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        StringBuffer result = new StringBuffer();
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(map.size());
            for (HashMap.Entry<String, String> e : map.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
                nameValuePairs.add(new BasicNameValuePair(key, value));
//
            }
//
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
//            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
//            Log.e(TAG,result.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String delete(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(url);
        StringBuffer result = new StringBuffer();
        try {
            HttpResponse response = httpclient.execute(httpDelete);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
//            Log.e(TAG,result.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

//    public String getPath(Uri uri) {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

    public static String getConnect(String link) {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(link);


        BufferedReader rd = null;
        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            HttpResponse response = client.execute(httpGet);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
//        Log.e(TAG,"fads");
//        StringBuilder builder = new StringBuilder();
//        try {
//            URL url = new URL(link);
//            URLConnection connection = url.openConnection();
//            InputStream stream = connection.getInputStream();
//            int c = stream.read();
//            while (c!=-1){
//                builder.append((char)c);
//                c = stream.read();
//            }
//            Log.e(TAG,builder.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return builder;

    }

}
