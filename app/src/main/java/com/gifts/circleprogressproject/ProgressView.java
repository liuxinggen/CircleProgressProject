package com.gifts.circleprogressproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 包名： com.gifts.circleprogressproject
 * 创建人： Liu_xg
 * 时间： 2017/11/3 11:39
 * 描述： 自定义view
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ProgressView extends View {
    /**
     * 当前进度
     */
    private int mCurrent;
    /**
     * 背景的圆
     */
    private Paint mPaintOut;
    /**
     * 当前的圆
     */
    private Paint mPaintCurrent;
    /**
     * 字体
     */
    private Paint mPaintText;

    /**
     * 自定义属性
     */
    private float mTextSize, mPaintWidth;
    private int mPaintColor = Color.RED;
    private int mTextColor = Color.BLACK;
    /**
     * 开始的角度
     */
    private int startAngle = 135;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获取自定的属性
         */
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.circle_progress_view);
        mPaintWidth = typedArray
                .getDimension(R.styleable.circle_progress_view_progress_paint_width,
                        dip2px(context, 10));
        mTextSize = typedArray
                .getDimension(R.styleable.circle_progress_view_progress_text_size,
                        dip2px(context, 18));
        mPaintColor = typedArray.getColor(R.styleable.circle_progress_view_progress_paint_color,
                mPaintColor);
        mTextColor = typedArray.getColor(R.styleable.circle_progress_view_progress_text_color,
                mTextColor);
        typedArray.recycle();//释放

        mPaintOut = new Paint();
        mPaintOut.setAntiAlias(true);
        mPaintOut.setColor(Color.GRAY);
        mPaintOut.setStrokeWidth(mPaintWidth);
        /**
         * 画笔样式
         *
         */
        mPaintOut.setStyle(Paint.Style.STROKE);
        /**
         * 笔刷的样式
         * Paint.Cap.ROUND 圆形
         * Paint.Cap.SQUARE 方型
         */
        mPaintOut.setStrokeCap(Paint.Cap.ROUND);

        mPaintCurrent = new Paint();
        mPaintCurrent.setAntiAlias(true);
        mPaintCurrent.setColor(mPaintColor);
        mPaintCurrent.setStrokeWidth(mPaintWidth);
        mPaintCurrent.setStyle(Paint.Style.STROKE);
        mPaintCurrent.setStrokeCap(Paint.Cap.ROUND);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(mTextColor);
        mPaintText.setStrokeWidth(mTextSize);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //高度
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(mPaintWidth / 2,
                mPaintWidth / 2,
                getWidth() - mPaintWidth / 2,
                getHeight() - mPaintWidth / 2);

        canvas.drawArc(rectF, startAngle, 270, false, mPaintOut);

        mCurrent = 50;
        float sweepAngle = mCurrent * 270 / 100;
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mPaintCurrent);

//        String text = mCurrent + "分";
        String text = "我是测试文字";
        //测量文字的宽度
        float textWidth = mPaintText.measureText(text, 0, text.length());
        //测量文字的高度
        float textHeight = (float) getTxtHeight(mPaintText);
        /**
         * 基线x的坐标即为：
         * view宽度的一半减去文字宽度的一半
         */
        float dx = getWidth() / 2 - textWidth / 2;
        /**
         * 基线y的坐标为：
         * view高度的一半减去文字高度的一半
         */
        float dy = getHeight() / 2 - textHeight / 2;
        canvas.drawText(text, dx, dy, mPaintText);

    }

    /**
     * 获取文字的高度
     *
     * @param mPaint
     * @return
     */
    public double getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
