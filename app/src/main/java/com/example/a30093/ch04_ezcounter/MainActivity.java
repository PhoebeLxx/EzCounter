package com.example.a30093.ch04_ezcounter;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.view.MotionEvent.ACTION_DOWN;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
    public TextView tv, tt;
    public Button bt;
    long count = 0;
    long media = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        tt = (TextView) findViewById(R.id.txvTran);
        bt = (Button) findViewById(R.id.button);
        tv.setOnLongClickListener(this);
        bt.setOnTouchListener(this);
        tv.setOnClickListener(this);
        tt.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
            media++;
            tt.setText("Now Plus " + media);
    }

    @Override
    public boolean onLongClick(View view) {
        count = 0;
        tv.setText("0");
        tt.setText("Now Plus 1");
        //长按将计数器归零，长按后不应再引发“加值事件”，true表示事件处理完毕
        //一个时间在这个程序中要么被onClick处理，要么被onLongClick处理，事件处理完后不应再引发别的监听对象处理
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);    //获取震动对象
        if(view.getId() == R.id.button && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if(count <= Integer.MAX_VALUE) {
                count += media;
                bt.setText("+" + count);
                tv.setText(String.valueOf(count));
            }else{
                tv.setText("Out of Bound");
                long[] pattern = {0, 200, 100, 500, 100, 800, 100, 1100};
                vb.vibrate(pattern, -1);
            }
        } else if (view.getId() == R.id.txvTran) {
            media = count;
            tt.setText("Now Plus " + media);
            if(motionEvent.getAction() == ACTION_DOWN) vb.vibrate(3000);
            else if(motionEvent.getAction() == MotionEvent.ACTION_UP) vb.cancel();
        }
        return true;
    }
}
