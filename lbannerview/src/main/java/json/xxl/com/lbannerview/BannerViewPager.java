package json.xxl.com.lbannerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxl on 2017/10/28.
 */

public class BannerViewPager extends ViewPager {
    private static final String TAG = "BannerViewPager";
    private BannerAdapter mAdapter;
    private int SCROLL_MSG = 0x0011;
    private long SCROLL_DELAY = 5 * 1000;
    private BannerScroller mBannerScroller;
    private List<View> mConvertViews;//界面复用
    private static final int START_INDEX = 214748; // 2147483647


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurrentItem(getCurrentItem() + 1);
            startScroll();
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBannerScroller = new BannerScroller(context);
        //改变ViewPager的滑动速率
        //ViewPager源码里面通过这个方法来滑动ViewPaget,只要改变duration，就可以改变其滑动速率
        //但是dration 是私有的，并且是局部变量，拿不到，mScroller也是私有的，是成员变量
        //可以通过反射获取mScroller变量,把Scroller设置为自己设定的Scroller，在自定义的Scroller里面复写
        //mScroller.startScroll(sx, sy, dx, dy, duration) 方法，修改dration;
        try {
            //通过反射获取到mScroller成员变量
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            //设置私有成员变量的值可以被改变
            mScroller.setAccessible(true);
            //第一个参数是代表当前属性在哪个类，第二个参数是代表要设置的值
            mScroller.set(this,mBannerScroller);//改成了自定义的BanerScroller
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;//对象Adapter设计模式
        mConvertViews = new ArrayList<>();
        setAdapter(new BannerViewAdapter());
        setCurrentItem(START_INDEX);
    }

    /**
     * 开始自动滚动
     */
    public void startScroll() {
        //清除消息
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, SCROLL_DELAY);
        Log.e(TAG, "startScroll: ");
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        mHandler.removeMessages(SCROLL_MSG);
    }


    /**
     * 改变ViewPager切换速率
     * @param duration
     */
    public void changeScrollDuration(int duration) {
        mBannerScroller.setScrollerDuration(duration);
    }

    /**
     * Activity销毁的时候回回调此方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        Log.e("BannerViewPager", "onDetachedFromWindow: ");
    }

    /**
     * 获取界面复用的View
     * @return
     */
    public View getConvertView(){
        for (int i = 0; i < mConvertViews.size(); i++) {
            if (mConvertViews.get(i).getParent() == null){//已经从父布局中被移除
                return mConvertViews.get(i);
            }
        }
        return null;
    }

    /**
     * ViewPager适配器
     */
    public class BannerViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //实现无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //固定写法 判断当前要显示的页面
            return view == object;
        }

        /**
         * ViewPager创建条目回调的方法
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //用Adapter设计模式将具体的view让用户获取添加
            //position 变化范围 0 - 2的31次方 Inter.MaxValue
            View view = mAdapter.getView(position % mAdapter.getCount(),getConvertView());
            container.addView(view);
            return view;
        }

        /**
         * ViewPager条目销毁回调的方法
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            //object = null;
            mConvertViews.add((View) object);//把销毁的View保存，用于复用
        }
    }
}
