package json.xxl.com.lbannerview;

import android.view.View;

/**
 * Created by xxl on 2017/10/28.
 */

public abstract class BannerAdapter {
    /**
     * 获取View
     */
    public abstract View getView(int position,View convertView);

    /**
     * 获取ViewPager 轮播图的数量
     */
    public abstract int getCount();

    /**
     * 获取广告位描述
     */
    public String getBannerDes(int position) {
        return "";
    }
}
