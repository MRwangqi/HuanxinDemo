package e8net.bjhh.behavior;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * Created by Administrator on 2016/7/11.
 */
public class BottomHideBehavior extends CoordinatorLayout.Behavior<View> {
    private Interpolator INTERPOLATER = new FastOutLinearInInterpolator();
    private Boolean isAnimator = false;
    private float viewY;
    private static final int DEFAULT_VALUE=30;

    public BottomHideBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    //快速滑动处理的函数
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


    //一般关心滑动方向
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (child.getVisibility() == View.VISIBLE && viewY == 0) {
            viewY = coordinatorLayout.getHeight() - child.getY();
        }

        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断当前是竖向滑动
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (dy >= DEFAULT_VALUE && !isAnimator && child.getVisibility() == View.VISIBLE) {//向上滑  这个dy应该是滑动的距离
            hide(child);
        } else if (dy < -DEFAULT_VALUE && !isAnimator && child.getVisibility() == View.GONE) {
            show(child);
        }
    }

    //该方法是手指离开界面后滑动
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void hide(final View view) {
        ViewPropertyAnimator anim = view.animate().translationY(viewY).setInterpolator(INTERPOLATER);
        anim.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimator = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                show(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void show(final View view) {
        ViewPropertyAnimator anim = view.animate().translationY(0).setInterpolator(INTERPOLATER);

        anim.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                isAnimator = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                isAnimator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                hide(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();

    }
}
