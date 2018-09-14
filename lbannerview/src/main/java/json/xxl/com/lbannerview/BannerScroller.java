package json.xxl.com.lbannerview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by xxl on 2017/10/28.
 * 自定义的改变ViewPager速率的Scroller
 */

public class BannerScroller extends Scroller {
    //默认切换速率
    private int mScrollerDuration = 900;

    /**
     * 设置改变ViewPager滑动速率的方法
     * @param duration
     */
    public void setScrollerDuration(int duration) {
        this.mScrollerDuration = duration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}
