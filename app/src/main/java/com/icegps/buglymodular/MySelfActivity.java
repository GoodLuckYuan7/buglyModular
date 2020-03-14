package com.icegps.buglymodular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * @Explain 使用阿里ARouter管理路由跳转
 * @Author chenqi
 * @Time 2020/3/12
 */

@Route(path = "/app/myself")
public class MySelfActivity extends AppCompatActivity {
    private TextView myself;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_self);

        initView();

        myself.setText("MySelfActivity第二次修复完成！");
    }

    private void initView(){
        myself = (TextView) findViewById(R.id.mySelf_TextView);
    }

}
