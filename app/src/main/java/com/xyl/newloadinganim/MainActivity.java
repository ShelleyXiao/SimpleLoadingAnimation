package com.xyl.newloadinganim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements LoadingCirclerView.PeriodFinishListener{
    private LoadingCirclerView mLoadingCirclerView;
    private ImageView mImageView;

    private AnimationSet mAnimationSet;
    private AnimationSet mAnimationSetToo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingCirclerView = (LoadingCirclerView) findViewById(R.id.loading_circle_view);
        mImageView = (ImageView) findViewById(R.id.loading_circle_image);

        setupAnimation();
        show();

    }

    private void setupAnimation() {
        mLoadingCirclerView.setCircleWidht(5);
        mLoadingCirclerView.setCircleIntervalTimeDecrment(10);
        mLoadingCirclerView.setCirclePercentIncremental(10);
        mLoadingCirclerView.setFinishListener(this);

        mAnimationSet = new AnimationSet(true);

        //放大透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(500);
        mAnimationSet.addAnimation(alphaAnimation);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.05f, 1.0f,
                1.05f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(550);
        mAnimationSet.addAnimation(scaleAnimation);
        mAnimationSet.setFillAfter(true);

        mAnimationSetToo = new AnimationSet(true);

        // 缩放透明
        AlphaAnimation alphaAnimationToo = new AlphaAnimation(1.0f, 0.2f);
        alphaAnimationToo.setDuration(100);
        mAnimationSetToo.addAnimation(alphaAnimationToo);

        ScaleAnimation scaleAnimationToo = new ScaleAnimation(1.0f, 0.6f,
                1.0f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationToo.setDuration(100);
        mAnimationSetToo.addAnimation(scaleAnimationToo);
        mAnimationSetToo.setFillAfter(true);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimationSetToo.setStartOffset(650);
                mImageView.startAnimation(mAnimationSetToo);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void show() {
        mImageView.setVisibility(View.VISIBLE);
        mLoadingCirclerView.setVisibility(View.VISIBLE);
        mLoadingCirclerView.setReset();
        mLoadingCirclerView.startAnimation();
        mLoadingCirclerView.invalidate();

        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
              mImageView.startAnimation(mAnimationSetToo);
            }
        }, 200);
    }

    @Override
    public void onFinish() {
        mImageView.setVisibility(View.VISIBLE);
        mImageView.startAnimation(mAnimationSet);
    }
}
