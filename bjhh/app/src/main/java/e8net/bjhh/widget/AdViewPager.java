package e8net.bjhh.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import e8net.bjhh.R;

/**
 * Created by Administrator on 2016/1/30.
 */
public class AdViewPager extends RelativeLayout {
    private static ViewPager viewPager;
    private Context context;
    private static final int SCROLL_TIME = 7000;//滚屏时间
    private OnitemClickListner listner;
    private MyHandler myHandler;
    private List<ImageView> list = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private LinearLayout linearLayout;//承载view point
    private int oldCurrent;

    public AdViewPager(Context context) {
        super(context);
        initView(context);
    }


    public AdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initView(Context context) {
        this.context = context;
      //  this.setClipChildren(false);//-------
        //初始化添加viewpager
        viewPager = new ViewPager(context);
      //  viewPager.setClipChildren(false);//------
      //  viewPager.setOffscreenPageLimit(Integer.MAX_VALUE);//缓冲3页面
     //   viewPager.setPageMargin(50);
        // viewPager.setPageTransformer(true,new ZoomInTransform());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(viewPager, params);
        //初始化填充point组件
        linearLayout = new LinearLayout(context);
        LayoutParams paramsll = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        paramsll.setMargins(0, 0, 0, 20);
        paramsll.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(linearLayout, paramsll);
    }

    //初始化point View控件
    public void initPoint(int length) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.setMargins(10, 0, 10, 0);
        for (int i = 0; i < length; i++) {
            View view = new View(context);
            if (i == 0)
                view.setBackgroundResource(R.drawable.point_focus);
            else
                view.setBackgroundResource(R.drawable.point_unfocus);
            views.add(view);
            linearLayout.addView(view, params);
        }
    }


    public void setNum(final List<String> banner) {
        initPoint(banner.size());
        myHandler = new MyHandler();
        for (int i = 0; i < banner.size(); i++) {
            ImageView img = new ImageView(context);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(img);
        }


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position % list.size()));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                String imageUrl = banner.get(position % banner.size());
                ImageView img = list.get(position % list.size());
                Glide.with(context).load(imageUrl).error(R.drawable.ic_launcher).into(img);
                img.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                container.addView(img);
                return list.get(position % list.size());
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //改变当前的view
            @Override
            public void onPageSelected(int position) {
                views.get(oldCurrent).setBackgroundResource(R.drawable.point_unfocus);
                views.get(position % views.size()).setBackgroundResource(R.drawable.point_focus);
                oldCurrent = position % views.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myHandler.post(runnable);
    }


    public static final class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            myHandler.postDelayed(runnable, SCROLL_TIME);
        }
    };


    public void setOnItemClickListner(OnitemClickListner listner) {
        this.listner = listner;
    }

    public interface OnitemClickListner {
        void setOnItemClickListner(View v, int position);
    }


    class ZoomInTransform implements ViewPager.PageTransformer {

        public static final String TAG = "simple_PagerTransform";

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void transformPage(View page, float position) {

            int width = page.getWidth();
            int height = page.getHeight();
            //這裏只對右邊的view做了操作
            if (position > 0 && position <= 1) {//right scorlling
                //position是1.0->0,但是沒有等於0
                Log.e(TAG, "right----position====" + position);
                //設置該view的X軸不動
                page.setTranslationX(-width * position);
                //設置縮放中心點在該view的正中心
                page.setPivotX(width / 2);
                page.setPivotY(height / 2);
                //設置縮放比例（0.0，1.0]
                page.setScaleX(1 - position);
                page.setScaleY(1 - position);

            } else if (position >= -1 && position < 0) {//left scrolling

            } else {//center

            }
        }
    }
}
