package com.example.tungmai.feedy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.CommentBlogActivity;
import com.example.tungmai.feedy.custominterface.RemoveAddReplyBlog;
import com.example.tungmai.feedy.models.ItemComment;
import com.example.tungmai.feedy.models.ItemReply;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by TungMai on 3/24/2017.
 */

public class ItemReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ItemReply> arrItemReplies;
    private int totalReply;
    private LayoutInflater inflater;
    private Context context;
    private boolean clickAddView;
    private boolean addReply;
    private boolean sendReply;
    private String idComment;
    private RemoveAddReplyBlog removeAddReplyBlog;

    public void setRemoveAddReplyBlog(RemoveAddReplyBlog removeAddReplyBlog) {
        this.removeAddReplyBlog = removeAddReplyBlog;
    }

    public ItemReplyAdapter(ArrayList<ItemReply> arrItemReplies, int totalReply, Context context, boolean addReply, boolean sendReply, String idComment) {
        this.arrItemReplies = arrItemReplies;
        this.context = context;
        this.totalReply = totalReply;
        inflater = LayoutInflater.from(context);
        this.addReply = addReply;
        clickAddView = false;
        this.idComment = idComment;
        this.sendReply = sendReply;
    }

    @Override
    public int getItemViewType(int position) {
        if (totalReply > 1 && clickAddView == false && clickAddView ==false) {
            if (position == 1) return 1;
        }
        if (addReply == true && position == arrItemReplies.size()) return 2;
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == 0) {
            view = inflater.inflate(R.layout.item_reply, parent, false);
            viewHolder = new ViewHolderReply(view);
        } else if (viewType == 1) {
            view = inflater.inflate(R.layout.item_add_view_reply, parent, false);
            viewHolder = new ViewHolderAddView(view);
        } else {
            view = inflater.inflate(R.layout.item_edt_input_comment, parent, false);
            viewHolder = new ViewHolderComment(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == 1) {
            ViewHolderAddView viewHolderAddView = (ViewHolderAddView) viewHolder;
            viewHolderAddView.tvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickAddView = true;
                    notifyDataSetChanged();
                }
            });
        } else if (getItemViewType(position) == 0) {
            ItemReply itemReply = arrItemReplies.get(position);
            ViewHolderReply holder = (ViewHolderReply) viewHolder;
            Picasso.with(context).load(itemReply.getImageUser()).resize(300, 300).centerCrop().into(holder.ivProfile);
            holder.tvName.setText(itemReply.getNameUser());
            holder.tvTime.setText(itemReply.getTime());
            holder.tvContent.setText(itemReply.getComment());

            holder.tvCountLike.setText(itemReply.getCountLike() + "");

            if (itemReply.isLike()) {
                holder.ivLike.setImageResource(R.drawable.like_true);
                holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog));
            } else {
                holder.ivLike.setImageResource(R.drawable.like);
                holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog_false));
            }
        } else {
            final ViewHolderComment holder = (ViewHolderComment) viewHolder;
            holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAddReplyBlog.cancalReply();
                }
            });
            holder.tvSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = holder.editText.getText().toString();
                    if (content.trim().isEmpty()) {
                        Toast.makeText(context, "Bạn chưa nhập reply", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();
                    String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ", " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);

//                    String idComment ;
//                    String nameUser;
//                    String imageUser;
                    String comment = content;
                    String timeReply = time;
                    int countLike = 0;
                    boolean isLike = false;
                    CommentBlogActivity commentBlogActivity = (CommentBlogActivity) context;
                    String blogId = commentBlogActivity.getIdBlog();
                    String userId = commentBlogActivity.getIdUser();

                    HashMap<String, String> map = new HashMap<>();
                    map.put("user_id", userId);
                    map.put("id_comment", idComment);
                    map.put("blog_id", blogId);
                    map.put("comment", comment);
                    map.put("time", time);

                    commentBlogActivity.getmSocket().emit("client_send_comment_reply_blog", new JSONObject(map));


                    removeAddReplyBlog.sendReply(idComment);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (totalReply > 1 && clickAddView == false && sendReply == false)
            return 2;
        else if (addReply == true) return arrItemReplies.size() + 1;
        return arrItemReplies.size();
    }

    class ViewHolderReply extends RecyclerView.ViewHolder {

        private ImageView ivProfile;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvContent;
        private TextView tvCountLike;
        private ImageView ivLike;

        public ViewHolderReply(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.iv_profile);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvCountLike = (TextView) itemView.findViewById(R.id.tv_count_like);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
        }
    }

    class ViewHolderAddView extends RecyclerView.ViewHolder {
        private TextView tvView;

        public ViewHolderAddView(View itemView) {
            super(itemView);
            tvView = (TextView) itemView.findViewById(R.id.tv_add_view);
        }
    }

    class ViewHolderComment extends RecyclerView.ViewHolder {
        private EditText editText;
        private TextView tvSend;
        private TextView tvCancel;

        public ViewHolderComment(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.edt_comment);
            tvSend = (TextView) itemView.findViewById(R.id.btn_send);
            tvCancel = (TextView) itemView.findViewById(R.id.btn_cancel);
        }
    }
}
