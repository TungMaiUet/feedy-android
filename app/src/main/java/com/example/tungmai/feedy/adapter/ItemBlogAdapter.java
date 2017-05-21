package com.example.tungmai.feedy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.CommentBlogActivity;
import com.example.tungmai.feedy.activity.ImageCommentActivity;
import com.example.tungmai.feedy.activity.PostBlogActivity;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.PostDataAsyncTask;
import com.example.tungmai.feedy.custom.CustomImage;
import com.example.tungmai.feedy.custom.Emoji;
import com.example.tungmai.feedy.fragment.BlogFragment;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.models.ItemBlog;
import com.example.tungmai.feedy.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by TungMai on 3/15/2017.
 */

public class ItemBlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ItemBlogAdapter";
    private static final String URL_SERVER = ConnectSever.IP_SEVER;
    public static final String ARRAY_IMAGE_BLOG = "array image blog";
    private LayoutInflater inflater;
    private ArrayList<ItemBlog> arrItemBlogs;
    private Context context;
    private User user;
    private PostDataAsyncTask postDataAsyncTask;
    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public ItemBlogAdapter(ArrayList<ItemBlog> arrItemBlogs, Context context, User user) {
        this.arrItemBlogs = arrItemBlogs;
        this.context = context;
        this.user = user;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == 0) {
            view = inflater.inflate(R.layout.item_blog_post_main, parent, false);
            viewHolder = new ViewHolerPost(view);
        } else {
            view = inflater.inflate(R.layout.item_blog, parent, false);
            viewHolder = new ViewHolerBlog(view);
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        Log.e(TAG, position + ":" + viewHolder.getItemViewType());
//        Log.e(TAG, arrItemBlogs.size() + "");
        if (viewHolder.getItemViewType() == 0) {
            ViewHolerPost holderPost = (ViewHolerPost) viewHolder;
            holderPost.postStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(((Activity) context), PostBlogActivity.class);
                    intent.putExtra(FragmentLogin.INTENT_USER, user);
                    ((Activity) context).startActivityForResult(intent, BlogFragment.REQUEST_CODE_LOGIN);
                }
            });

        } else if (viewHolder.getItemViewType() == 1) {
            ViewHolerBlog holder = (ViewHolerBlog) viewHolder;
            final ItemBlog itemBlog = arrItemBlogs.get(position - 1);

//        holder.ivProfile.setImageBitmap(CustomImage.base64ToImage(itemBlog.getImageUser()));

            Picasso.with(context).load(URL_SERVER + itemBlog.getImageUser()).resize(300, 300).centerCrop().into(holder.ivProfile);
            holder.tvName.setText(itemBlog.getName());
            holder.tvTime.setText(itemBlog.getTime());
            holder.tvContent.setText(Emoji.replaceInText(itemBlog.getContent()));
            holder.tvCountLike.setText(itemBlog.getLikeSize() + "");
            holder.tvCountComment.setText(itemBlog.getCommentSize() + "");

            if (itemBlog.isLike()) {
                holder.ivLike.setImageResource(R.drawable.like_true);
                holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog));
            } else {
                holder.ivLike.setImageResource(R.drawable.like);
                holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog_false));
            }

            setOnClick(holder, itemBlog);

            holder.tvCountImage.setVisibility(View.VISIBLE);
            final String[] images = itemBlog.getImages();
            int countImage = images.length;
//            if (countImage == 0) holder.relativeLayout.setVisibility(View.GONE);
//            else {


//                holder.ivImage_1.setVisibility(View.INVISIBLE);
//                holder.ivImage_2_1.setVisibility(View.INVISIBLE);
//                holder.ivImage_2_2.setVisibility(View.INVISIBLE);
//                holder.tvCountImage.setVisibility(View.INVISIBLE);
            holder.ivImage_1.setVisibility(View.GONE);
            holder.ivImage_2_1.setVisibility(View.GONE);
            holder.ivImage_2_2.setVisibility(View.GONE);
            holder.tvCountImage.setVisibility(View.GONE);


            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(((Activity) context), ImageCommentActivity.class);
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (int i = 0; i < images.length; i++) {
                            arrayList.add(images[i]);
                        }
                        intent.putExtra(ARRAY_IMAGE_BLOG, arrayList);
                        ((Activity) context).startActivity(intent);
                    }
                });


                if (countImage == 1) {
//                    Log.e("xxccc", position + ": " + countImage + "");
                    holder.ivImage_1.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(URL_SERVER + images[0]).resize(300, 300).centerCrop().into(holder.ivImage_1);
//                    Glide.with(context).load(URL_SERVER + images[0]).placeholder(R.drawable.backgroup_loading_image).fitCenter().centerCrop().into(holder.ivImage_1);
//                    holder.ivImage_2_1.setVisibility(View.INVISIBLE);
//                    holder.ivImage_2_2.setVisibility(View.INVISIBLE);
//                    holder.tvCountImage.setVisibility(View.INVISIBLE);
                } else if (countImage == 2) {
                    holder.ivImage_2_1.setVisibility(View.VISIBLE);
                    holder.ivImage_2_2.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(URL_SERVER + images[0]).resize(300, 300).centerCrop().into(holder.ivImage_2_1);
                    Picasso.with(context).load(URL_SERVER + images[1]).resize(300, 300).centerCrop().into(holder.ivImage_2_2);
//                    Glide.with(context).load(URL_SERVER + images[0]).placeholder(R.drawable.backgroup_loading_image).fitCenter().centerCrop().into(holder.ivImage_2_1);
//                    Glide.with(context).load(URL_SERVER + images[1]).placeholder(R.drawable.backgroup_loading_image).fitCenter().centerCrop().into(holder.ivImage_2_2);
//                    holder.tvCountImage.setVisibility(View.INVISIBLE);
                } else if (countImage > 2) {
                    holder.ivImage_2_1.setVisibility(View.VISIBLE);
                    holder.ivImage_2_2.setVisibility(View.VISIBLE);
                    holder.tvCountImage.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(URL_SERVER + images[0]).resize(300, 300).centerCrop().into(holder.ivImage_2_1);
                    Picasso.with(context).load(URL_SERVER + images[1]).resize(300, 300).centerCrop().into(holder.ivImage_2_2);
//                    Glide.with(context).load(URL_SERVER + images[0]).placeholder(R.drawable.backgroup_loading_image).fitCenter().centerCrop().into(holder.ivImage_2_1);
//                    Glide.with(context).load(URL_SERVER + images[1]).placeholder(R.drawable.backgroup_loading_image).fitCenter().centerCrop().into(holder.ivImage_2_2);
                    holder.tvCountImage.setText("+" + (countImage - 2) + "");
                }
            }
//        }

    }

    private void setOnClick(final ViewHolerBlog holder, final ItemBlog itemBlog) {
        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("user_id", user.getIdToken());
                map.put("blog_id", itemBlog.getIdBlog());
//                int countLike = itemBlog.getLikeSize();
                if (!itemBlog.isLike()) {
//                    countLike++;
                    itemBlog.setLikeSize(itemBlog.getLikeSize() + 1);
                    postDataAsyncTask = new PostDataAsyncTask(-1, hander, (Activity) context, ConnectSever.LINK_ADD_LIKE_BLOG);
                    postDataAsyncTask.execute(map, null);
                    holder.ivLike.setImageResource(R.drawable.like_true);
                    holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog));
                    itemBlog.setLike(true);
                    holder.tvCountLike.setText(itemBlog.getLikeSize() + "");
                } else {
                    itemBlog.setLikeSize(itemBlog.getLikeSize() - 1);
                    postDataAsyncTask = new PostDataAsyncTask(-2, hander, (Activity) context, ConnectSever.LINK_REMOVE_LIKE_BLOG);
                    postDataAsyncTask.execute(map, null);
                    holder.ivLike.setImageResource(R.drawable.like);
                    holder.tvCountLike.setTextColor(context.getResources().getColor(R.color.color_like_blog_false));
                    itemBlog.setLike(false);
                    holder.tvCountLike.setText(itemBlog.getLikeSize() + "");
                }
            }
        });

        holder.ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("user_id", user.getIdToken());
                map.put("blog_id", itemBlog.getIdBlog());
                if (!itemBlog.isSave()) {
                    postDataAsyncTask = new PostDataAsyncTask(-1, hander, (Activity) context, ConnectSever.LINK_ADD_SAVE_BLOG);
                    postDataAsyncTask.execute(map, null);
                    holder.ivSave.setImageResource(R.drawable.saved);
                    itemBlog.setSave(true);
                } else {
                    postDataAsyncTask = new PostDataAsyncTask(-2, hander, (Activity) context, ConnectSever.LINK_ADD_REMOVE_BLOG);
                    postDataAsyncTask.execute(map, null);
                    holder.ivLike.setImageResource(R.drawable.save);
                    itemBlog.setSave(false);
                }
            }
        });

        holder.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((Activity) context), CommentBlogActivity.class);
                intent.putExtra(FragmentLogin.INTENT_USER, user);
                intent.putExtra(FragmentLogin.INTENT_ITEM_BLOG, itemBlog.getIdBlog());
                ((Activity) context).startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrItemBlogs.size() + 1;
    }

    class ViewHolerPost extends RecyclerView.ViewHolder {
        private CardView postStatus;

        public ViewHolerPost(View itemView) {
            super(itemView);
            postStatus = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    class ViewHolerBlog extends RecyclerView.ViewHolder {
        private ImageView ivProfile;
        private TextView tvName;
        private TextView tvTime;
        private ImageView ivSave;
        private TextView tvContent;
        private ImageView ivImage_1;
        private ImageView ivImage_2_1;
        private ImageView ivImage_2_2;
        private TextView tvCountImage;
        private TextView tvCountLike;
        private TextView tvCountComment;
        private ImageView ivLike;
        private ImageView ivComment;
        private RelativeLayout relativeLayout;

        public ViewHolerBlog(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.iv_profile);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivSave = (ImageView) itemView.findViewById(R.id.iv_save);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            ivImage_1 = (ImageView) itemView.findViewById(R.id.iv_image_1);
            ivImage_2_1 = (ImageView) itemView.findViewById(R.id.iv_image_2_1);
            ivImage_2_2 = (ImageView) itemView.findViewById(R.id.iv_image_2_2);
            tvCountImage = (TextView) itemView.findViewById(R.id.tv_count_image);
            tvCountLike = (TextView) itemView.findViewById(R.id.tv_count_like);
            tvCountComment = (TextView) itemView.findViewById(R.id.tv_count_comment);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
        }

        public void bindView(String[] images) {
//            Picasso.with(url).load(persons.get(i).photoId).fit().into(personPhoto);
            ivImage_1.setVisibility(View.INVISIBLE);
            ivImage_2_1.setVisibility(View.INVISIBLE);
            ivImage_2_2.setVisibility(View.INVISIBLE);
            tvCountImage.setVisibility(View.INVISIBLE);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(((Activity) context), ImageCommentActivity.class);
//                    intent.putExtra(ARRAY_IMAGE_BLOG, itemBlog.getImages());
////                    intent.putExtra(FragmentLogin.INTENT_ITEM_BLOG, itemBlog.getIdBlog());
//                    ((Activity) context).startActivity(intent);
                }
            });

            int countImage = images.length;
            if (countImage == 1) {
//                Log.e("xxccc", position + ": " + countImage + "");
                ivImage_1.setVisibility(View.VISIBLE);
                Picasso.with(context).load(URL_SERVER + images[0]).resize(300, 300).centerCrop().into(ivImage_1);
//                    holder.ivImage_2_1.setVisibility(View.INVISIBLE);
//                    holder.ivImage_2_2.setVisibility(View.INVISIBLE);
//                    holder.tvCountImage.setVisibility(View.INVISIBLE);
            } else if (countImage == 2) {
                ivImage_2_1.setVisibility(View.VISIBLE);
                ivImage_2_2.setVisibility(View.VISIBLE);
                Picasso.with(context).load(URL_SERVER + images[0]).resize(300, 300).centerCrop().into(ivImage_2_1);
                Picasso.with(context).load(URL_SERVER + images[1]).resize(300, 300).centerCrop().into(ivImage_2_2);
//                    holder.tvCountImage.setVisibility(View.INVISIBLE);
            } else if (countImage > 2) {
                ivImage_2_1.setVisibility(View.VISIBLE);
                ivImage_2_2.setVisibility(View.VISIBLE);
                tvCountImage.setVisibility(View.VISIBLE);
                Picasso.with(context).load(URL_SERVER + images[0]).resize(300, 300).centerCrop().into(ivImage_2_1);
                Picasso.with(context).load(URL_SERVER + images[1]).resize(300, 300).centerCrop().into(ivImage_2_2);
                tvCountImage.setText("+" + (countImage - 2) + "");
            }
        }

    }
}
