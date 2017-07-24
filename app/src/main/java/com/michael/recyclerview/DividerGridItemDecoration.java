package com.michael.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by chenyao on 2017/7/20.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private final Paint mColorPaint;
    private Drawable mDivider;
    private GridLayoutManager manager;

    public DividerGridItemDecoration(Context context, GridLayoutManager manager) {
        this.manager = manager;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        //绘制颜色分割线的画笔
        mColorPaint = new Paint();
        mColorPaint.setColor(ContextCompat.getColor(context, R.color.green));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    private int getSpanCount(RecyclerView parent, int itemPosition) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

//            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            spanCount = manager.getSpanSizeLookup().getSpanSize(itemPosition) == manager.getSpanCount() ? 1 : manager.getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        Log.e("GridItemDecoration", "getSpanCount, spanCount : " + spanCount);
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if(mColorPaint != null) {
                c.drawRect(left, top, right, bottom, mColorPaint);
            }
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if(mColorPaint != null) {
                c.drawRect(left, top, right, bottom, mColorPaint);
            }
        }
    }

    /**
     * 判断是否是最后一列
     * @param parent
     * @param pos
     * @param spanCount     每一行item 的数量
     * @param childCount
     * @return
     */
    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 获取当前item 所在第几列
            int index = manager.getSpanSizeLookup().getSpanIndex(pos, spanCount);
            // 获取当前item 所在组第几行
            int groupIndex = manager.getSpanSizeLookup().getSpanGroupIndex(pos, spanCount);
            Log.e("GridItemDecoration", "getSpanCount, groupIndex : " + groupIndex);
            Log.e("GridItemDecoration", "getSpanCount, index : " + index);
            if(index >= spanCount - 1){
                return true;
            }
//            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
//            {
//                return true;
//            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    /**
     *  判断是否是最后一行
     * @param parent
     * @param pos
     * @param spanCount     每一行的数量
     * @param childCount    所有item总数
     * @return
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
//            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
            if (pos > childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        Log.e("getItemOffsets", "过时的 getItemOffsets ");
        int spanCount = getSpanCount(parent, itemPosition);
        int childCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {   // 如果是最后一行，则不需要绘制底部
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {  // 如果是最后一列，则不需要绘制右边
            Log.e("getItemOffsets", "mDivider.getIntrinsicHeight() : "+ mDivider.getIntrinsicHeight());
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            Log.e("getItemOffsets", "mDivider.getIntrinsicWidth() : "+ mDivider.getIntrinsicWidth());
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }
//        outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//
//        int spanCount = getSpanCount(parent, position);
//        int childCount = parent.getAdapter().getItemCount();
//        if (isLastRaw(parent, position, spanCount, childCount)) {   // 如果是最后一行，则不需要绘制底部
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
//        } else if (isLastColum(parent, position, spanCount, childCount)) {  // 如果是最后一列，则不需要绘制右边
//            Log.e("getItemOffsets", "mDivider.getIntrinsicHeight() : "+ mDivider.getIntrinsicHeight());
//            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
//        } else {
//            Log.e("getItemOffsets", "mDivider.getIntrinsicWidth() : "+ mDivider.getIntrinsicWidth());
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
//        }

    }
}
