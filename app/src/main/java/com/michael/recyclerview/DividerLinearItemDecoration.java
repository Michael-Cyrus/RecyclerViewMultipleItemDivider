package com.michael.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenyao on 2017/7/26.
 */

public class DividerLinearItemDecoration extends DividerItemDecoration {
    private int mOrientation;

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context     Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    public DividerLinearItemDecoration(Context context, int orientation) {
        super(context, orientation);
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int itemCount = parent.getAdapter().getItemCount();
        if(position == itemCount - 1){
            outRect.set(0, 0, 0, 0);
        }else{
            super.getItemOffsets(outRect, view, parent, state);
        }

//        outRect.set(0, 0, 0, 2);
    }
}
