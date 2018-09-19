package json.xxl.com.lbannerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by xxl on 2017/10/28.
 * 自定义ViewPager 显示的View 添加BannerViewPager，文字描述和小点
 */

public class BannerView extends RelativeLayout {
    private Context mContext;
    private BannerViewPager mBannerViewPager;
    private TextView mDes;
    private LinearLayout mLLDotIndicator;
    private BannerAdapter mBannerAdapter;
    private Drawable mIndecatorNormal;//点未选中drawble
    private Drawable mIndecatorFocus;//点选中drawable
    private int mCurrentPosition = 0;//当前选中的页面位置,默认位置是第0个位置
    private int mDotSize = 8;//小点默认大小
    private int mDotGravity = 1;//小点默认位置 1 为右边
    private RelativeLayout mRLBottom;
    private int mBVBottomColor = Color.TRANSPARENT;//底部容器颜色默认值 透明
    private int mDotDistance = 8;
    private Drawable mBVBottomColorDrawable;//底部容器颜色值，shape文件资源颜色方式设置底部容器颜色
    private int mWidthProportion, mHeightProportion;//BannerView（ViewPager）宽高比
    private boolean mBulge = false;//ViewPager 是否两边凸出
    private int mBulgeDistance = 25;//两边凸出距离
    private int mBottomType = 0;//小点 文字描述位置 默认底部类型是小点覆盖在图片上） 0位覆盖在图片上 1是在底部图片下方
    private boolean mScollFlag = false;//滚动标记 标记是否设置过滚动

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttribute(attrs);
        switch (mBottomType) {
            case 0:
                inflate(context, R.layout.ui_dot_text_cover_banner_layout, this);
                break;
            case 1:
                inflate(context, R.layout.ui_banner_layout, this);
                break;
        }
        initView();
    }

    private void initView() {
        mBannerViewPager = (BannerViewPager) findViewById(R.id.banner_vp);
        mDes = (TextView) findViewById(R.id.tv_des);
        mLLDotIndicator = (LinearLayout) findViewById(R.id.ll_dot_container);
        mRLBottom = (RelativeLayout) findViewById(R.id.rl_bottom);

        if (mBulge) {
            RelativeLayout parent = (RelativeLayout) mBannerViewPager.getParent();
            mBannerViewPager.setClipChildren(false);
            parent.setClipChildren(false);
            mBannerViewPager.setOffscreenPageLimit(2);//预加载数量
            LayoutParams layoutParams = (LayoutParams) mBannerViewPager.getLayoutParams();
            layoutParams.setMargins(dip2px(mBulgeDistance), 0, dip2px(mBulgeDistance), 0);
            mBannerViewPager.setLayoutParams(layoutParams);
            mBannerViewPager.setPageMargin(10);//ViewPager页面之间的距离
        }

        //手先判断是否是shape等drawable文件作为底部容器的背景，有的话就优先使用，没有的话判断是否传入了颜色值
        if (mBVBottomColorDrawable != null) {
            mRLBottom.setBackground(mBVBottomColorDrawable);
        } else {
            mRLBottom.setBackgroundColor(mBVBottomColor);
        }
    }

    /**
     * 初始化自定义属性
     */
    private void initAttribute(AttributeSet attrs) {
        //获取自定义属性
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        //获取选中点的drawable
        mIndecatorFocus = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndecatorFocus == null) {
            //设置默认值
            mIndecatorFocus = new ColorDrawable(Color.RED);
        }
        //获取未选中点的drawable
        mIndecatorNormal = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndecatorNormal == null) {
            //设置默认值
            mIndecatorNormal = new ColorDrawable(Color.WHITE);
        }

        //获取点的间距
        mDotDistance = (int) typedArray.getDimension(R.styleable.BannerView_dotDistance, mDotDistance);

        //获取点的大小
        mDotSize = (int) typedArray.getDimension(R.styleable.BannerView_dotSize, mDotSize);
        //获取点的位置
        mDotGravity = typedArray.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        //获取底部容器颜色 可以设置shape等drawable资源作为底部容器的背景
        mBVBottomColorDrawable = typedArray.getDrawable(R.styleable.BannerView_bottomColor);
        if (mBVBottomColorDrawable == null) {
            mBVBottomColor = typedArray.getColor(R.styleable.BannerView_bottomColor, mBVBottomColor);
        }
        //获取自定义属性，宽高比
        mWidthProportion = (int) typedArray.getFloat(R.styleable.BannerView_widthProportion, mWidthProportion);
        mHeightProportion = (int) typedArray.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        mBulge = typedArray.getBoolean(R.styleable.BannerView_bulge, mBulge);
        mBulgeDistance = typedArray.getInteger(R.styleable.BannerView_bulgeDistance, mBulgeDistance);
        mBottomType = typedArray.getInt(R.styleable.BannerView_bottomType, mBottomType);
        typedArray.recycle();
    }

    /**
     * 改变速率
     * @param duration
     */
    public void changeDuration(int duration) {
        mBannerViewPager.changeScrollDuration(duration);
    }


    /**
     * 设置Adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mBannerAdapter = adapter;
        mBannerViewPager.setAdapter(adapter);

        //设置广告位小点
        initDotIndicator();

        //监听ViewPager滑动
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                pagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if (mScollFlag)
                            mBannerViewPager.stopScroll();
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mScollFlag)
                            mBannerViewPager.startScroll();
                        break;
                }
            }
        });

        //初始化第一个位置的广告位文字描述
        String bannerDes = mBannerAdapter.getBannerDes(0);
        mDes.setText(bannerDes);

        //设置BannerView宽高比
        int width = getMeasuredWidth();
        //如果没有设置宽高比就不动态计算宽高比
        if (mWidthProportion == 0 || mHeightProportion == 0) {
            return;
        }
        int height = width * mHeightProportion / mWidthProportion;

        //动态计算设置宽高
        getLayoutParams().height = height;
    }

    /**
     * 根据切换页面改变小点状态和文字描述
     */
    private void pagerSelected(int position) {
        //恢复上一个点的显示，显示为默认的状态
        IndicatorView oldDotView = (IndicatorView) mLLDotIndicator.getChildAt(mCurrentPosition);
        oldDotView.setDrawble(mIndecatorNormal);

        //设置当前点的状态为选中状态
        mCurrentPosition = position % mBannerAdapter.getCount();
        IndicatorView currentDotView = (IndicatorView) mLLDotIndicator.getChildAt(mCurrentPosition);
        currentDotView.setDrawble(mIndecatorFocus);

        //设置广告位文字描述
        String bannerDes = mBannerAdapter.getBannerDes(mCurrentPosition);
        mDes.setText(bannerDes);
    }

    /**
     * 初始化小点
     */
    private void initDotIndicator() {
        int count = mBannerAdapter.getCount();//获取广告位数量
        //设置点的位置
        mLLDotIndicator.setGravity(getDotGravity());
        for (int i = 0; i < count; i++) {
            //根据 条目图片张数动态添加小点
            IndicatorView indicatorView = new IndicatorView(mContext);
            //设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            //设置点的间距
            params.leftMargin = mDotDistance;
            //设置Drawable 背景
            if (i == 0) {
                indicatorView.setDrawble(mIndecatorFocus);
            } else {
                indicatorView.setDrawble(mIndecatorNormal);
            }
            indicatorView.setLayoutParams(params);
            mLLDotIndicator.addView(indicatorView);
        }
    }

    /**
     * dip 转px
     */
    private int dip2px(int dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getResources().getDisplayMetrics());
    }

    /**
     * 设置ViewPager自动滚动
     */
    public void startScroll() {
        mScollFlag = true;
        mBannerViewPager.startScroll();
    }

    /**
     * 获取点的位置
     */
    private int getDotGravity() {
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT;
            case 1:
                return Gravity.RIGHT;
        }
        return Gravity.LEFT;
    }
}
