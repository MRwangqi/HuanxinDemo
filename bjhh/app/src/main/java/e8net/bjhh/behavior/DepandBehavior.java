package e8net.bjhh.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 某个view监听另一个view的状态变化，例如大小、位置、显示状态等
 * layoutDependsOn和onDependentViewChanged方法，
 * 将id设置在depanden view上面，app设置在child view上面
 * <p/>
 * 某个view监听CoordinatorLayout里的滑动状态
 * onStartNestedScroll和onNestedPreScroll方法。
 * <p/>
 * Created by Administrator on 2016/9/13.
 */
public class DepandBehavior extends CoordinatorLayout.Behavior<View> {

    //CoordinatorLayout里利用反射去获取这个Behavior的时候就是拿的这个构造
    public DepandBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        ViewCompat.offsetTopAndBottom(child, dependency.getTop() - child.getTop());
        return true;
    }

    //设置关心的View
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof TextView;
    }
}
