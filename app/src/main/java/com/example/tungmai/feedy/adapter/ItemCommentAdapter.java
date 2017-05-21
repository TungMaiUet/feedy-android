package com.example.tungmai.feedy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.CommentBlogActivity;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.PostDataAsyncTask;
import com.example.tungmai.feedy.custominterface.AddReplyBlog;
import com.example.tungmai.feedy.custominterface.RemoveAddReplyBlog;
import com.example.tungmai.feedy.custominterface.RemoveAddReplyCommentBlog;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.models.ItemComment;
import com.example.tungmai.feedy.models.ItemReply;
import com.github.nkzawa.emitter.Emitter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TungMai on 3/24/2017.
 */

public class ItemCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ItemComment> arrItemComments;
    private LayoutInflater inflater;
    private Context context;
    private String idUser;
    private String idBlog;
    private Handler hander =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
//    private AddReplyBlog addReplyBlog;
//    private RemoveAddReplyCommentBlog removeAddReplyCommentBlog;

//    public void setRemoveAddReplyCommentBlog(RemoveAddReplyCommentBlog removeAddReplyCommentBlog) {
//        this.removeAddReplyCommentBlog = removeAddReplyCommentBlog;
//    }

//    public void setAddReplyBlog(AddReplyBlog addReplyBlog) {
//        this.addReplyBlog = addReplyBlog;
//    }

    public ItemCommentAdapter(ArrayList<ItemComment> arrItemComments, Context context) {
        this.arrItemComments = arrItemComments;
        this.context = context;
        inflater = LayoutInflater.from(context);
//        CommentBlogActivity commentBlogActivity = (CommentBlogActivity) context;
//        commentBlogActivity.getmSocket().on("server_send_comment_reply_blog", onReply);

    }

//    private final Emitter.Listener onReply = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            String message = args[0].toString();
////            Log.e("message", message);
//
//            try {
//                JSONObject jsonObject = new JSONObject(message);
//
//                String idUserTemp = jsonObject.getString("id_user");
//                String idComment = jsonObject.getString("id_comment");
////                if (idUserTemp.equals(idUser)) {
////                    arrItemComments.get(arrItemComments.size()-1).setIdComment(idComment);
////                    return;
////                }
//
//
//                String nameComment = jsonObject.getString("name");
//                String profileUserComment = jsonObject.getString("profile_image");
//                boolean isLike = jsonObject.getBoolean("is_like");
//                String content = jsonObject.getString("content");
//                String time = jsonObject.getString("time");
//                int countLike = jsonObject.getInt("like_size");
//
//                ItemReply itemComment = new ItemReply(idComment, nameComment, profileUserComment, content, time, countLike, isLike);
////                arrItemComments.add(itemComment);
////                appBar.syncOffset();
//                for (int i = 0; i < arrItemComments.size(); i++) {
//                    if (arrItemComments.get(i).getIdComment().equals(idComment)) {
//                        arrItemComments.get(i).getArrItemReplies().add(itemComment);
//                    }
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
////            context.notifyDataSetChanged();
//            ((CommentBlogActivity) context).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    recyclerView.scrollToPosition(itemCommentAdapter.getItemCount() - 1);
//                    notifyDataSetChanged();
//                }
//            });
//        }
//    };
//

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        return new ViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemComment itemComment = arrItemComments.get(position);
        final ViewHolderComment holder = (ViewHolderComment) viewHolder;

        Picasso.with(context).load(ConnectSever.IP_SEVER + itemComment.getImageUser()).resize(300, 300).centerCrop().into(holder.ivProfile);
        holder.tvName.setText(itemComment.getNameUser());
        holder.tvTime.setText(itemComment.getTime());
        holder.tvContent.setText(itemComment.getComment());

        holder.tvCountLike.setText(itemComment.getCountLike() + "");
//        holder.tvCountComment.setText(itemComment.getArrItemReplies().size() + "");

//        final ItemReplyAdapter itemReplyAdapter= new ItemReplyAdapter(itemComment.getArrItemReplies(),itemComment.getCountReply(),context,itemComment.isAddReply(),itemComment.isDoneAddReply(),itemComment.getIdComment());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        holder.recyclerView.setLayoutManager(linearLayoutManager);
//        holder.recyclerView.setAdapter(itemReplyAdapter);

//        itemReplyAdapter.setRemoveAddReplyBlog(new RemoveAddReplyBlog() {
//            @Override
//            public void cancalReply() {
//                addReplyBlog.addReply();
//                for(int i=0;i<arrItemComments.size();i++){
//                    arrItemComments.get(i).setAddReply(false);
//                }
////                itemComment.setAddReply(true);
//                notifyDataSetChanged();
//                removeAddReplyCommentBlog.removeAddReplyComment();
//            }
//
//            @Override
//            public void sendReply(String string) {
//                addReplyBlog.addReply();
//                for(int i=0;i<arrItemComments.size();i++){
//                    arrItemComments.get(i).setAddReply(false);
//                    if(arrItemComments.get(i).getIdComment().equals(string)){
//                        arrItemComments.get(i).setDoneAddReply(true);
//                    }
//                }
////                itemComment.setAddReply(true);
//                notifyDataSetChanged();
//                removeAddReplyCommentBlog.removeAddReplyComment();
//            }
//        });

//        holder.ivComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.e("aaa","aaa");
//
//                addReplyBlog.addReply();
//                for (int i = 0; i < arrItemComments.size(); i++) {
//                    arrItemComments.get(i).setAddReply(false);
//                }
//                itemComment.setAddReply(true);
//                notifyDataSetChanged();
//            }
//        });

        if (itemComment.isLike()) {
            holder.ivLike.setImageResource(R.drawable.like_true);
            holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog));
        } else {
            holder.ivLike.setImageResource(R.drawable.like);
            holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog_false));
        }

        setOnClick(holder, itemComment);
    }

    private void setOnClick(final ViewHolderComment holder, final ItemComment itemComment) {
        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("iduser", getIdUser());
                map.put("idblog", getIdBlog());
                map.put("idcomment",itemComment.getIdComment());
//                int countLike = itemBlog.getLikeSize();
                if (!itemComment.isLike()) {
//                    countLike++;
                    itemComment.setCountLike(itemComment.getCountLike() + 1);
                    PostDataAsyncTask postDataAsyncTask = new PostDataAsyncTask(-1, hander, (Activity) context, ConnectSever.LINK_ADD_LIKE_COMMENT);
                    postDataAsyncTask.execute(map, null);
                    holder.ivLike.setImageResource(R.drawable.like_true);
                    holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog));
                    itemComment.setLike(true);
                    holder.tvCountLike.setText(itemComment.getCountLike() + "");
                } else {
                    itemComment.setCountLike(itemComment.getCountLike() - 1);
                    PostDataAsyncTask postDataAsyncTask = new PostDataAsyncTask(-2, hander, (Activity) context, ConnectSever.LINK_REMOVE_LIKE_COMMENT);
                    postDataAsyncTask.execute(map, null);
                    holder.ivLike.setImageResource(R.drawable.like);
                    holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog_false));
                    itemComment.setLike(false);
                    holder.tvCountLike.setText(itemComment.getCountLike() + "");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrItemComments.size();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(String idBlog) {
        this.idBlog = idBlog;
    }

    class ViewHolderComment extends RecyclerView.ViewHolder {

        private ImageView ivProfile;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvContent;
        private TextView tvCountLike;
        private ImageView ivLike;
//        private TextView tvCountComment;
//        private ImageView ivComment;
        private RecyclerView recyclerView;

        public ViewHolderComment(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.iv_profile);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvCountLike = (TextView) itemView.findViewById(R.id.tv_count_like);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
//            tvCountComment = (TextView) itemView.findViewById(R.id.tv_count_comment);
//            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
        }
    }

}
