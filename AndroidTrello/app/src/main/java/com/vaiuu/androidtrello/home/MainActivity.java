package com.vaiuu.androidtrello.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.vaiuu.androidtrello.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
private RelativeLayout container_relative;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

    }
    private void initUI(){
        container_relative=(RelativeLayout)findViewById(R.id.container_relative);
        container_relative.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.container_relative:
        buttonClickEffect(container_relative);
        Intent intent=new Intent(this,TrelloMainActivity.class);
        startActivity(intent);
        break;
}
    }
}
