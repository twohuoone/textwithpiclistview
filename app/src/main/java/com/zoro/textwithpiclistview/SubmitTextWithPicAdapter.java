package com.zoro.textwithpiclistview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Zoro
 * Created on 2019/7/12
 * description:
 */
public class SubmitTextWithPicAdapter extends BaseRecyclerAdapter<String, RecyclerView.ViewHolder> {

    private int pic_Limit = 9;
    private PhotoListener listener;

    public static enum ITEM_TYPE {
        ITEM_TYPE_PHOTO, ITEM_TYPE_DEFAULT
    }

    public SubmitTextWithPicAdapter(Context context, int pic_Limit) {
        super(context);
        this.pic_Limit = pic_Limit;
    }

    public void setListener(PhotoListener listener) {
        this.listener = listener;
    }

    //复用了父类中的方法，不再使用本方法
    @Override
    protected int getItemLayout() {
        return 0;
    }

    //复用了父类中的方法，不再使用本方法
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(View view) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mList.size()) {
            return ITEM_TYPE.ITEM_TYPE_DEFAULT.ordinal();
        } else {
            return ITEM_TYPE.ITEM_TYPE_PHOTO.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_PHOTO.ordinal()) {
            return new PhotoHolder(mLayoutInflater.inflate(R.layout.photo_item, parent, false));
        } else {
            return new DefaultHolder(mLayoutInflater.inflate(R.layout.default_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PhotoHolder) {
            Glide.with(mContext)
                    .load(mList.get(position))
                    .into(((PhotoHolder) holder).image);

            ((PhotoHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    删除图片，并且删除掉服务器那边已经上传的图片
                    listener.deltetPhoto(position);

                }
            });
        } else if (holder instanceof DefaultHolder) {
            if (position == pic_Limit) {
                ((DefaultHolder) holder).ivPic.setVisibility(View.GONE);
            } else {
                ((DefaultHolder) holder).ivPic.setVisibility(View.VISIBLE);
            }
            ((DefaultHolder) holder).ivPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    点击增加图片，已经做了最多显示五张的限制
                    listener.addPhoto();
                }
            });

        }
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, String s, int position) {
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.delete)
        ImageView delete;

        public PhotoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class DefaultHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_Pic)
        ImageView ivPic;

        public DefaultHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static interface PhotoListener {
        void deltetPhoto(int position);

        void addPhoto();
    }

}
