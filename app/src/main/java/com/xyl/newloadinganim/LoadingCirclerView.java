package com.xyl.newloadinganim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xyl.newloadinganim.utils.DisplayUtils;

/**
 * User: ShaudXiao
 * Date: 2017-03-13
 * Time: 14:46
 * Company: zx
 * Description:
 * FIXME
 */


public class LoadingCirclerView extends ImageView {

    private final int DEFALUT_SIZE = 80;
    private final int CIRCLE_SIZE = 3;
    private final int DRAW_INTERVAL_TIME = 100;
    private final int CIRCLE_PERCENT_INCREMNTAL = 25;
    private final int CIRCLE_INTERVAL_TIME_DECREMENT = 25;
    private final int CIRCLE_FIEXD_TIME = 1200;

    private int mCirclePercent;
    private int mWidth;
    private int mHeight;
    private int mCircleWidht = CIRCLE_SIZE;
    private int mIntervalTime = DRAW_INTERVAL_TIME;
    private int mCirclePercentIncremental = CIRCLE_PERCENT_INCREMNTAL;
    private int mCircleIntervalTimeDecrment = CIRCLE_INTERVAL_TIME_DECREMENT;
    private RectF mCricleRectF;

    private boolean isStart;
    private boolean isRestart;

    private Paint mCrrclePaint;

    private PeriodFinishListener mFinishListener;


    public LoadingCirclerView(Context context) {
        this(context, null);
    }

    public LoadingCirclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCirclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoadingCirclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        mCrrclePaint = new Paint();
        mCrrclePaint.setStrokeWidth(mCircleWidht);
        mCrrclePaint.setColor(getResources().getColor(R.color.circleColor));
        mCrrclePaint.setStyle(Paint.Style.STROKE);
        mCrrclePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widhtMode = MeasureSpec.getMode(widthMeasureSpec);

        if(heightMode == MeasureSpec.EXACTLY) {
            mHeight = height;
        } else {
            mHeight = DisplayUtils.dip2px(DEFALUT_SIZE);
        }

        if(widhtMode == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
            mWidth = DisplayUtils.dip2px(DEFALUT_SIZE);
        }


        mCricleRectF = new RectF(mCircleWidht, mCircleWidht, mWidth - mCircleWidht, mHeight - mCircleWidht);

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(isStart) {
            if(mCirclePercent <= 100) {
                isRestart = true;
                canvas.drawArc(mCricleRectF, 270, - mCirclePercent / 100.f * 360, false, mCrrclePaint );
                mCirclePercent += mCirclePercentIncremental;
            } else if(isRestart) {

                if(null != mFinishListener) {
                    mFinishListener.onFinish();
                }

                isRestart = false;

                setReset();

                canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - mCircleWidht, mCrrclePaint);

                postInvalidateDelayed(CIRCLE_FIEXD_TIME);

            }

            if(isStart && isRestart) {
                postInvalidateDelayed(mIntervalTime);
                mIntervalTime -= mCircleIntervalTimeDecrment;
            }
        } else {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - mCircleWidht, mCrrclePaint);

        }

        super.onDraw(canvas);
    }

    public void startAnimation() {
        isStart = true;
    }

    public void stopAnimation() {
        isStart = false;
    }

    public boolean hasStartAnimation() {
        return isStart;
    }

    public void setReset() {
        mCirclePercent = 0;
        mIntervalTime = DRAW_INTERVAL_TIME;
    }

    public interface PeriodFinishListener {
        void onFinish();
    }

    public void setFinishListener(PeriodFinishListener finishListener) {
        mFinishListener = finishListener;
    }


    public void setCircleWidht(int circleWidht) {
        mCircleWidht = circleWidht;
    }


    public void setIntervalTime(int intervalTime) {
        mIntervalTime = intervalTime;
    }

    public void setCirclePercentIncremental(int circlePercentIncremental) {
        mCirclePercentIncremental = circlePercentIncremental;
    }


    public void setCircleIntervalTimeDecrment(int circleIntervalTimeDecrment) {
        mCircleIntervalTimeDecrment = circleIntervalTimeDecrment;
    }
}
