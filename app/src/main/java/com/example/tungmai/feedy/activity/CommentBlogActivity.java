package com.example.tungmai.feedy.activity;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.ItemCommentAdapter;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.GetDataAsyncTask;
import com.example.tungmai.feedy.custominterface.AddReplyBlog;
import com.example.tungmai.feedy.custominterface.RemoveAddReplyCommentBlog;
import com.example.tungmai.feedy.fragment.BlogFragment;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.models.ItemComment;
import com.example.tungmai.feedy.models.ItemReply;
import com.example.tungmai.feedy.models.User;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.tungmai.feedy.R.id.rl_send;
import static com.example.tungmai.feedy.R.id.swipeContainer;


/**
 * Created by TungMai on 3/23/2017.
 */

public class CommentBlogActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int WHAT_MESSAGE_BLOG = 891;
    private static final String TAG = "CommentBlogActivity";
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayoutSend;
    private EditText edtComment;
    private Button btnSend;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private LinearLayoutManager linearLayoutManager;

    private ItemCommentAdapter itemCommentAdapter;
    private ArrayList<ItemComment> arrItemComments;
    private User user;
    private String idBlog;
    private String idUser;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
//            Log.e(TAG,result);
            if (msg.equals("Err")) {
//                Toast
            } else {
                convertJsonData(result);
                itemCommentAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);


            }
        }
    };

    private void convertJsonData(String result) {
//        Log.e(TAG,result);
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);

                int idComment = jObject.getInt("_id");

                boolean isLike = jObject.getBoolean("is_vote");
                String content = jObject.getString("content");
                String time = jObject.getString("time");
                int countLike = jObject.getInt("votes");

                JSONObject jsonObjectUser = jObject.getJSONObject("user");
                String nameId = jsonObjectUser.getString("user_id");
                String nameComment = jsonObjectUser.getString("user_name");
                String profileUserComment = jsonObjectUser.getString("user_image");

//                JSONArray arrReply = null;
                ArrayList<ItemReply> arrItemReplies = new ArrayList<>();

//                for (int j = 0; j < arrReply.length(); j++) {
//                    JSONObject jObjectReply = arrReply.getJSONObject(j);
//                    String idReply = jObjectReply.getString("id_reply");
//                    String contentReply = jObjectReply.getString("content");
//                    String timeReply = jObjectReply.getString("time");
//                    String nameReply = jObject.getString("name");
//                    String profileUserReply = jObjectReply.getString("profile_image");
//                    boolean isLikeReply = jObject.getBoolean("is_like");
//                    int countLikeReply = jObject.getInt("like_size");
//                    ItemReply itemReply = new ItemReply(idReply, nameReply, profileUserReply, contentReply, timeReply, countLikeReply, isLikeReply);
//                    arrItemReplies.add(itemReply);
//                }

                ItemComment itemComment = new ItemComment(idComment+"",nameId, nameComment, profileUserComment, content, time, countLike, isLike, 0, arrItemReplies);
                arrItemComments.add(itemComment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//                    Log.e(TAG, jArray.length()+"a");
    }

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(ConnectSever.IP_SEVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String message = args[0].toString();
//            Log.e("message", message);
            try {
                JSONObject jObject = new JSONObject(message);

                int idComment = jObject.getInt("id_comment");

                boolean isLike = jObject.getBoolean("is_like");
                String content = jObject.getString("content");
                String time = jObject.getString("time");
                int countLike = jObject.getInt("like_size");

                JSONObject jsonObjectUser = jObject.getJSONObject("user");
                String nameId = jsonObjectUser.getString("user_id");
                String nameComment = jsonObjectUser.getString("user_name");
                String profileUserComment = jsonObjectUser.getString("user_image");


                ArrayList<ItemReply> arrItemReplies = new ArrayList<>();
//                Log.e(TAG, "asfsd");
                ItemComment itemComment = new ItemComment(idComment+"",nameId, nameComment, profileUserComment, content, time, countLike, isLike, 0, arrItemReplies);
//                Log.e(TAG, itemComment.getComment());
                arrItemComments.add(itemComment);
//                appBar.syncOffset();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.scrollToPosition(itemCommentAdapter.getItemCount() - 1);
                    itemCommentAdapter.notifyDataSetChanged();
                }
            });

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_blog);

        user = (User) getIntent().getSerializableExtra(FragmentLogin.INTENT_USER);
        idBlog = getIntent().getStringExtra(FragmentLogin.INTENT_ITEM_BLOG);
        idUser = user.getIdToken();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bình luận");

        mSocket.connect();
        mSocket.on("server_send_comment_blog" + idBlog, onLogin);
        mSocket.emit("client_connect_comment_blog", idBlog);

        arrItemComments = new ArrayList<>();
        initComments();
        itemCommentAdapter = new ItemCommentAdapter(arrItemComments, this);
        itemCommentAdapter.setIdUser(user.getIdToken());
        itemCommentAdapter.setIdBlog(idBlog);

        initViews();
    }

    private void initComments() {
        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask(handler, this, WHAT_MESSAGE_BLOG);
        String url = ConnectSever.IP_SEVER + "/blog/comment/get?id=" + idBlog + "&iduser=" + idUser;
//        String url = ConnectSever.IP_SEVER + "/blog/comment/get?id=5914ac70238c832de44194fb&iduser=591499760fccab20fc270200";
//        Log.e(TAG,url);
        getDataAsyncTask.execute(url);
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemCommentAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_comment);
        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);

        relativeLayoutSend = (RelativeLayout) findViewById(R.id.rl_send);
//        itemCommentAdapter.setAddReplyBlog(new AddReplyBlog() {
//            @Override
//            public void addReply() {
//                relativeLayoutSend.setVisibility(View.INVISIBLE);
//            }
//        });
//
//        itemCommentAdapter.setRemoveAddReplyCommentBlog(new RemoveAddReplyCommentBlog() {
//            @Override
//            public void removeAddReplyComment() {
//                relativeLayoutSend.setVisibility(View.VISIBLE);
//            }
//        });

        edtComment = (EditText) findViewById(R.id.edt_comment);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String comment = edtComment.getText().toString();
                if (comment.trim().isEmpty()) {
                    Toast.makeText(this, "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ", " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
                HashMap<String, String> map = new HashMap<>();

                map.put("_id",arrItemComments.size()+"");
                map.put("blog_id", idBlog);
                map.put("user_id", idUser);
                map.put("user_name", user.getName());
                map.put("user_image", user.getImageUser());
                map.put("content", comment);
                map.put("time", time);
//                map.put("key_comment",)

                mSocket.emit("client_send_comment_blog" + idBlog, new JSONObject(map));
//                Log.e(TAG,idBlog);

                ArrayList<ItemReply> arrItemReplies = new ArrayList<>();
                ItemComment itemComment = new ItemComment(arrItemComments.size()+"",user.getIdToken(), user.getName(), user.getImageUser(), comment, time, 0, false, 0, arrItemReplies);
                arrItemComments.add(itemComment);
                recyclerView.scrollToPosition(itemCommentAdapter.getItemCount() - 1);
//                appBar.syncOffset();
                itemCommentAdapter.notifyDataSetChanged();
                edtComment.setText("");


                //close keyboard
//                View view = this.getCurrentFocus();
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }

                break;

        }
    }

    private void exitComment() {
        mSocket.close();
//        mSocket.emit("client_exit_connect_comment_blog", idBlog);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitComment();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitComment();
        super.onBackPressed();
    }

    public String getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(String idBlog) {
        this.idBlog = idBlog;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Socket getmSocket() {
        return mSocket;
    }
}
