package com.michael.recyclerview;

import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.bt_ll)
    Button btLl;
    @Bind(R.id.bt_gl)
    Button btGl;
    @Bind(R.id.bt_sg)
    Button btSg;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate() {

    }

    @OnClick({R.id.bt_ll, R.id.bt_gl, R.id.bt_sg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_ll:
                break;
            case R.id.bt_gl:
                GridLayoutActivity.startActivity(this);
                break;
            case R.id.bt_sg:
                break;
        }
    }
}
