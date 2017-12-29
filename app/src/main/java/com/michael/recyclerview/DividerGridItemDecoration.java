package com.michael.recyclerview;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


/**
 * 为 Recyclerview 三种布局添加分割线
 * Created by chenyao on 2017/7/20.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private final Paint mColorPaint;
    private Drawable mDivider;
    private Context context;

    public DividerGridItemDecoration(Context context) {
        this(context, R.color.white_press);
    }

    public DividerGridItemDecoration(Context context, int color) {
        this.context = context;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        //绘制颜色分割线的画笔
        mColorPaint = new Paint();
        mColorPaint.setColor(ContextCompat.getColor(context, color));
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            return;
        }
        if(layoutManager.getClass().getSimpleName().equals(LinearLayoutManager.class.getSimpleName())){
            LinearLayoutManager manager = (LinearLayoutManager)layoutManager;
            int orientation = manager.getOrientation();
            if (orientation == LinearLayout.VERTICAL) {
                drawHorizontal(c, parent);
            }else {
                drawVertical(c, parent);
            }
        }else {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }

    private int getSpanCount(RecyclerView parent, int itemPosition) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            int spanSize = manager.getSpanSizeLookup().getSpanSize(itemPosition);
            int spanNum = manager.getSpanCount();
            Log.e("michael", "spanSize = " + spanSize);
            Log.e("michael", "spanNum = " + spanNum);
            spanCount = spanSize == spanNum ? 1 : spanNum;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int top = child.getBottom() + params.bottomMargin;
            final int right = child.getRight() + params.rightMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if(mColorPaint != null) {
                mColorPaint.setColor(context.getResources().getColor(R.color.green));
                c.drawRect(left, top, right, bottom, mColorPaint);
            }
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            int left = 0;
            int right = 0;
            if(isFirstColumn(parent, i, getSpanCount(parent, i), childCount)){
                left = child.getLeft();
                right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                if (mColorPaint != null) {
                    mColorPaint.setColor(context.getResources().getColor(R.color.black));
                    c.drawRect(left, top, right, bottom, mColorPaint);
                }
                //item右边分割线
                left = child.getRight() + params.rightMargin - mDivider.getIntrinsicWidth();
                right = left + mDivider.getIntrinsicWidth();
            }else{
                //非左边第一列
                left = child.getRight() + params.rightMargin - mDivider.getIntrinsicWidth();
                right = left + mDivider.getIntrinsicWidth();
            }

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if(mColorPaint != null) {
                mColorPaint.setColor(context.getResources().getColor(R.color.yellow));
                c.drawRect(left, top, right, bottom, mColorPaint);
            }
        }
    }

    /**
     * 判断是否是最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount  每一行item 的数量
     * @param childCount
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            // 获取当前item 所在第几列
            int index = manager.getSpanSizeLookup().getSpanIndex(pos, spanCount);
            Log.e("michael", "当前Item所在列数 index = " + index);
            if (index >= spanCount - 1) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {       // 如果是最后一列，则不需要绘制右边
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }else if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager manager = (LinearLayoutManager)layoutManager;
            int orientation = manager.getOrientation();
            if (orientation == LinearLayout.VERTICAL) {
                return true;
            }else{
                if(pos == childCount - 1){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断是否是最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount  每一行item 的数量
     * @param childCount
     * @return
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            // 获取当前item 所在第几列
            int index = manager.getSpanSizeLookup().getSpanIndex(pos, spanCount);
            Log.e("michael", "当前Item所在列数 index = " + index);
            if (index == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {       // 如果是最后一列，则不需要绘制右边
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }else if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager manager = (LinearLayoutManager)layoutManager;
            int orientation = manager.getOrientation();
            if (orientation == LinearLayout.VERTICAL) {
                return true;
            }else{
                if(pos == childCount - 1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是最后一行
     * @param parent
     * @param pos
     * @param spanCount  每一行的数量
     * @param childCount 所有item总数
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
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else {   // StaggeredGridLayoutManager 且横向滚动
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }else if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager manager = (LinearLayoutManager)layoutManager;
            int orientation = manager.getOrientation();
            if (orientation == LinearLayout.VERTICAL) {
                if(pos == childCount - 1){
                    return true;
                }
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount = getSpanCount(parent, itemPosition);
        int childCount = parent.getAdapter().getItemCount();
        int intrinsicWidth = mDivider.getIntrinsicWidth();
        if(layoutManager.getClass().getSimpleName().equals(LinearLayoutManager.class.getSimpleName())) {
            LinearLayoutManager manager = (LinearLayoutManager)layoutManager;
            int orientation = manager.getOrientation();
            if (orientation == LinearLayout.VERTICAL) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight()*5);
            } else {
                outRect.set(0, 0, intrinsicWidth *5, 0);
            }
        }else {
            if (isLastRaw(parent, itemPosition, spanCount, childCount)) {   // 如果是最后一行，则不需要绘制底部
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else if (isLastColumn(parent, itemPosition, spanCount, childCount)) {  // 如果是最后一列，则不需要绘制右边
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
//            outRect.set(intrinsicWidth, intrinsicWidth, intrinsicWidth, intrinsicWidth);
//            outRect.set(0, 0, intrinsicWidth, intrinsicWidth);
        }
    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//    }
}
