package com.michael.recyclerview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyao on 2017/7/20.
 */

public class WorkbenchAdapter extends RecyclerView.Adapter {

    private final LayoutInflater inflater;
    private final Context context;
    private int spanCount;
    private RecyclerView recyclerview;
    private List<ItemBean> dataList;

    public WorkbenchAdapter(Context context, int spanCount, RecyclerView recyclerview) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.spanCount = spanCount;
        this.recyclerview = recyclerview;
    }

    public enum ITEM_TYPE {
        TITLE,
        ITEM
    }

    public void setData(List<ItemBean> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TITLE.ordinal()) {
            return new TitleViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, null, false));
        } else {
            return new ItemViewHolder(inflater.inflate(R.layout.workbench_item_img_layout, null, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemBean itemBean = dataList.get(position);
        int itemViewType = getItemViewType(position);
        if(itemViewType == ITEM_TYPE.TITLE.ordinal()){
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.text1.setText(itemBean.title);
            titleViewHolder.text1.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }else{
            ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            itemViewHolder.itemName.setText(itemBean.itemName);
//            itemViewHolder.itemCl.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            Glide.with(context).load(itemBean.url)
                    .placeholder(R.mipmap.iv_default_pic)   // 加载过程中的占位Drawable
                    .error(R.mipmap.iv_default_pic)
                    .into(itemViewHolder.itemImg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.imgItemClick(itemBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        ItemBean itemBean = dataList.get(position);
        return itemBean.isTitle ? ITEM_TYPE.TITLE.ordinal() : ITEM_TYPE.ITEM.ordinal();
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    private WorkbenchItemClick listener;

    public void setItemClickListener(WorkbenchItemClick listener) {
        this.listener = listener;
    }

    public interface WorkbenchItemClick{
        void imgItemClick(ItemBean bean);
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        @Bind(android.R.id.text1)
        TextView text1;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_img)
        ImageView itemImg;
        @Bind(R.id.item_cl)
        ConstraintLayout itemCl;
        @Bind(R.id.item_name)
        TextView itemName;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            int width = recyclerview.getMeasuredWidth() - recyclerview.getPaddingLeft()*2;
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width / spanCount, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemCl.setLayoutParams(params);
            ConstraintLayout.LayoutParams ivParams = (ConstraintLayout.LayoutParams) itemImg.getLayoutParams();
            int ivWidth = width / 5;
            ivParams.height = ivWidth;
            ivParams.width = ivWidth;
            itemImg.setLayoutParams(ivParams);
        }
    }
}
