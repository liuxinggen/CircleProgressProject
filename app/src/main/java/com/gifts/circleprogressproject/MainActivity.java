package com.gifts.circleprogressproject;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressView view;
    /**
     * 总成绩
     */
    private int totalScore = 200;
    /**
     * 当前成绩
     */
    private int currentScore=180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.circleProgress);
        view.setTotalScore(totalScore);
        /**
         * 进度条从0到100的动画
         * 除了startAnimator1()方法中用的ValueAnimator.ofInt()，我们还有
         * ofFloat()、ofObject()这些生成器的方法;
         * 我们可以通过ofObject()去实现自定义的数值生成器
         */
        ValueAnimator animator = ValueAnimator.ofFloat(0, currentScore);
        animator.setDuration(2000);
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
                view.setmCurrent((int)current);
            }
        });
        animator.start();
        view.setOnLoadingCompleteListenter(new ProgressView.OnLoadingCompleteListenter() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
