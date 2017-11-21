package com.taoz27.ideaapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by taoz27 on 2017/11/16.
 */

public class BaseActivity extends AppCompatActivity{
    Toolbar toolbar;

    void setupToolBar(String title, boolean hasRight, View.OnClickListener onRightClickListener){
        toolbar=findViewById(R.id.toolbar);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((TextView)findViewById(R.id.title)).setText(title);
        if (hasRight){
            ImageView right=findViewById(R.id.right);
            right.setVisibility(View.VISIBLE);
            if (onRightClickListener!=null)
                right.setOnClickListener(onRightClickListener);
        }
    }
}
