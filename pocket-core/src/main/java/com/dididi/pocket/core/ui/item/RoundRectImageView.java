package com.dididi.pocket.core.ui.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.dididi.pocket.core.R;


/**
 * Created by dididi
 * on 04/09/2018 .
 */

public class RoundRectImageView extends AppCompatImageView {

    private Paint mPaint;
    private float mRoundPx;

    public RoundRectImageView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        mPaint = new Paint();
    }

    public RoundRectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context,attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            // 等比例缩放拉伸
            float widthScale = getWidth() * 1.0f / bitmap.getWidth();
            float heightScale = getHeight() * 1.0f / bitmap.getHeight();
            //TODO:这里需要优化,避免在onDraw中创建新对象,(onDraw经常会多次调用)
            Matrix matrix = new Matrix();
            matrix.setScale(widthScale, heightScale);
            Bitmap newBt =
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Bitmap b = getRoundRect(newBt, mRoundPx);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
            mPaint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, mPaint);
        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 指定xPerform图形叠加混合模式为SRC_IN
     * 显示两图形叠加部分
     *
     * @param bitmap  待处理图片
     * @param roundPx 圆角半径
     */
    private Bitmap getRoundRect(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            throw new NullPointerException("bitmap can not be null");
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        mPaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        mPaint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, mPaint);
        return output;
    }

    /**
     * 获取自定义属性 添加corner
     */
    private void getAttrs(Context context, AttributeSet attr) {
        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.RoundRectImageView);
        mRoundPx = typedArray.getDimension(R.styleable.RoundRectImageView_roundRect_corner,0);
        typedArray.recycle();
    }
}
