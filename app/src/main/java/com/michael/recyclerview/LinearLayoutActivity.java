package com.michael.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by chenyao on 2017/7/26.
 */

public class LinearLayoutActivity extends BaseActivity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipRefresh)
    SwipeRefreshLayout swipRefresh;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private WorkbenchAdapter adapter;
    private List<ItemBean> list;
    private List<WorkbenchBean.DataBean> dataList;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, LinearLayoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_workbench_layout;
    }

    @Override
    protected void afterCreate() {
        initView();
        initData();
    }


    private void initView() {
        toolbar.setTitle("线性布局");
        setSupportActionBar(toolbar);
        initSwipeRefreshLayout(swipRefresh);
        list = new ArrayList<>();
        dataList = new ArrayList<>();
        adapter = new WorkbenchAdapter(this, 1, recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter.setItemClickListener(new WorkbenchAdapter.WorkbenchItemClick() {
            @Override
            public void imgItemClick(ItemBean bean) {
                Toast.makeText(context, bean.itemName, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerview.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayout.VERTICAL));
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
    }

    private void initData() {
        String jsonStr = DataUtil.getJsonStr();

        Gson gson = new Gson();
        WorkbenchBean bean = gson.fromJson(jsonStr,WorkbenchBean.class);
        dataList.addAll(bean.getData());
        for (int i = 0; i < dataList.size(); i++) {
            WorkbenchBean.DataBean dataBean = dataList.get(i);
            ItemBean itemBean = new ItemBean();
            itemBean.isTitle = true;
            itemBean.title = dataBean.getTitle();
            list.add(itemBean);
            for (int j = 0; j < 3; j++) {
                WorkbenchBean.DataBean.InfoBean infoBean = dataBean.getInfo().get(j);
                itemBean = new ItemBean();
                itemBean.itemName = infoBean.getTitle();
                itemBean.url = infoBean.getImg();
                itemBean.order = infoBean.getOrder();
                list.add(itemBean);
            }
        }
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void loadRefreshData() {
        dataList.clear();
        list.clear();
        initData();
        swipRefresh.setRefreshing(false);
    }
}
