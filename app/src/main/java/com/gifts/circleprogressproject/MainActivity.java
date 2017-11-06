package com.gifts.circleprogressproject;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edTotalScore;
    private EditText edCurrentScore;
    private Spinner spSpeed;
    private Button btnSubmit;
    private ProgressView view;
    /**
     * 总成绩
     */
    private int totalScore = 100;
    /**
     * 当前成绩
     */
    private int currentScore = 98;
    /**
     * 转动的速度
     */
    private long speed = 2000;

    private List<String> listSpeeds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.circleProgress);
        findViews();
        initData();

        initView();
        initEvent();

    }

    private void initData() {
        listSpeeds = new ArrayList<>();
        listSpeeds.add("1000");
        listSpeeds.add("2000");
        listSpeeds.add("3000");
        listSpeeds.add("4000");
        listSpeeds.add("5000");
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, listSpeeds);
        spSpeed.setAdapter(adapter);
    }

    /**
     * 完成后的回调接口
     */
    private void initEvent() {
        view.setOnLoadingCompleteListenter(new ProgressView.OnLoadingCompleteListenter() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
            }
        });
        spSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                speed = Long.parseLong(listSpeeds.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = edTotalScore.getText().toString().trim();
                String data2 = edCurrentScore.getText().toString().trim();
                if (TextUtils.isEmpty(data1)) {
                    Toast.makeText(MainActivity.this, "填写总成绩", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(data2)) {
                    Toast.makeText(MainActivity.this, "填写当前成绩", Toast.LENGTH_SHORT).show();
                    return;
                }
                totalScore = Integer.parseInt(data1);
                currentScore = Integer.parseInt(data2);
                if (currentScore > totalScore) {
                    Toast.makeText(MainActivity.this, "当前成绩不能大于总成绩", Toast.LENGTH_SHORT).show();
                    return;

                }
                initView();
            }
        });

    }

    private void initView() {
        view.setTotalScore(totalScore);
        /**
         * 进度条从0到指定数字的动画
         * 除了startAnimator1()方法中用的ValueAnimator.ofInt()，我们还有
         * ofFloat()、ofObject()这些生成器的方法;
         * 我们可以通过ofObject()去实现自定义的数值生成器
         */
        ValueAnimator animator = ValueAnimator.ofFloat(0, currentScore);
        animator.setDuration(speed);
        /**
         *  Interpolators  插值器，用来控制具体数值的变化规律
         *  LinearInterpolator  线性
         */
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                /**
                 * 通过这样一个监听事件，我们就可以获取
                 * 到ValueAnimator每一步所产生的值。
                 *
                 * 通过调用getAnimatedValue()获取到每个时间因子所产生的Value。
                 * */
                float current = (float) valueAnimator.getAnimatedValue();
                view.setmCurrent((int) current);
            }
        });
        animator.start();

    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-11-03 16:55:55 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        edTotalScore = (EditText) findViewById(R.id.ed_total_score);
        edCurrentScore = (EditText) findViewById(R.id.ed_current_score);
        spSpeed = (Spinner) findViewById(R.id.sp_speed);
        btnSubmit = (Button) findViewById(R.id.btn_submit);


    }


}
