package com.subzero.trafficflow.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.subzero.trafficflow.R;


public class BeeEditText extends EditText {

    private Paint mPaint;
    private Bitmap mClearBitmap;
    private RectF mClearRect;
    /**
     * 触摸时按下的点
     **/
    PointF downP = new PointF();
    /**
     * 触摸时当前的点
     **/
    PointF curP = new PointF();

    public BeeEditText(Context context) {
        super(context);
        init(context);
    }

    public BeeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BeeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mClearBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_close);
        mPaint = new Paint();
        mClearRect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String content = getEditableText().toString();
        if (content.length() > 0) {
            int width = getWidth();
            int height = getHeight();

            int left = width - mClearBitmap.getWidth() - 20;
            int top = (height - mClearBitmap.getHeight()) / 2;
            canvas.drawBitmap(mClearBitmap, left, top, mPaint);

            mClearRect.set(left, top, left + mClearBitmap.getWidth(), top + mClearBitmap.getHeight());
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        int x, y;
        // 每次进行onTouch事件都记录当前的按下的坐标
        curP.x = arg0.getX();
        curP.y = arg0.getY();
        switch (arg0.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下时候的坐标
                // 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
                downP.x = arg0.getX();
                downP.y = arg0.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                x = (int) (curP.x - downP.x);
                y = (int) (curP.y - downP.y);
                break;
            case MotionEvent.ACTION_UP:
                // 在up时判断是否按下和松手的坐标为一个点
                // 如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
                if (mClearRect.contains(downP.x, downP.y) && mClearRect.contains(curP.x, curP.y)) {
                    this.setText("");
                    //return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(arg0);
    }
}
