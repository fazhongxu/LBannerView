package json.xxl.com.lbannerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xxl on 2017/10/28.
 * 小圆点
 */

public class IndicatorView extends View {

    private Drawable drawble;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawble != null) {
//            drawble.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
//            drawble.draw(canvas);

            //1.根据drawable获取bitmap对象
            Bitmap bitmap = drawable2Bitmap(drawble);

            //1.把bitmap转换为圆形bitmap
            Bitmap circleBitmap = getCircleBitmap(bitmap);

            canvas.drawBitmap(circleBitmap,0,0,null);

        }
    }


    /**
     * drawable转换为bitmap对象
     */
    private Bitmap drawable2Bitmap(Drawable drawble) {
        //如果是BitmapDrable,直接返回bitmap
        if (drawble instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawble).getBitmap();
        }
        //其他
        //创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(bitmap);
        //把drawable画到bitmap上
        drawble.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        drawble.draw(canvas);
        return bitmap;
    }

    /**
     * 获取圆形图像
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //在canvas上画个圆
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight() /2,getMeasuredWidth()/2,paint);
        //取交集  PorterDuff.Mode.SRC_IN  同时绘制圆形和原来的图形，取交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //把原来的bitmap绘制到新的圆上面
        canvas.drawBitmap(bitmap,0,0,paint);
        return circleBitmap;
    }

    /**
     * 设置点的Drawable
     */
    public void setDrawble(Drawable drawble) {
        this.drawble = drawble;
        //重新绘制
        invalidate();
    }

}
