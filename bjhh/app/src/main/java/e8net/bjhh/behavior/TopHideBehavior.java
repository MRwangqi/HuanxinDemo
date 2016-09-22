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
 * Created by Administrator on 2016/9/13.
 */
public class TopHideBehavior extends CoordinatorLayout.Behavior<View> {

    private Interpolator INTERPOLATER = new FastOutLinearInInterpolator();
    private float viewY;
    boolean isAnimator;
    private static final int DEFAULT_VALUE=30;
    public TopHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (child.getVisibility() == View.VISIBLE && viewY == 0) {
            viewY = child.getHeight();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (dy > DEFAULT_VALUE&& !isAnimator && child.getVisibility() == View.VISIBLE) {
            hide(child);
        }
        if (dy < -DEFAULT_VALUE&& !isAnimator && child.getVisibility() == View.GONE) {
            show(child);
        }

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void hide(final View view) {
        ViewPropertyAnimator anim = view.animate().translationY(-viewY).setInterpolator(INTERPOLATER);
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
