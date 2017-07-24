package com.michael.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by chenyao on 2017/7/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public AppCompatActivity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        context = this;
        afterCreate();
    }

    protected abstract int getLayoutResID();

    protected abstract void afterCreate();

    protected void initSwipeRefreshLayout(SwipeRefreshLayout swipRefresh){
        //改变加载显示的颜色
        swipRefresh.setColorSchemeColors(getResources().getColor(R.color.green),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.blue));
        //设置背景颜色
        //swipeRefreshLayout.setBackgroundColor(Color.YELLOW);
        //设置初始时的大小
        swipRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        //设置向下拉多少出现刷新
        swipRefresh.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipRefresh.setProgressViewEndTarget(false, 200);
        //设置监听
        swipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefreshData();
            }
        });
    }

    protected void loadRefreshData(){};
}
