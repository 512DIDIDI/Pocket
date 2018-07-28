package com.dididi.pocket_core.ui.Slide;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;
import android.view.View;

import com.dididi.pocket_core.Util.DimenUtil;

import java.lang.ref.WeakReference;


/**
 * Created by dididi
 * on 28/07/2018 .
 */

@SuppressWarnings("FieldCanBeLocal")
public class ListViewSlideLayout extends RelativeLayout implements View.OnTouchListener {
    //需要达到的滑动速度
    public static final int SNAP_VELOCITY = 200;
    //屏幕宽度
    private int mScreenWidth;
    //左边缘
    private int mLeftEdge = 0;
    //右边缘
    private int mRightEdge = 0;
    //手指滑动阈值
    private int mTouchSlop;
    //记录手指按下x坐标
    private float xDown;
    //记录手指按下y坐标
    private float yDown;
    //记录手指移动时的x坐标
    private float xMove;
    //记录手指移动时的y坐标
    private float yMove;
    //记录手指抬起的x坐标
    private float xUp;
    //左布局是否完全显示
    private boolean isLeftLayoutVisible;
    //是否正在滑动
    private boolean isSliding;
    //左侧布局
    private View mLeftLayout;
    //右侧布局
    private View mRightLayout;
    //监听侧滑事件的view
    private View mBindView;
    //左侧布局的参数
    private MarginLayoutParams mLeftParams;
    //右侧布局的参数
    private MarginLayoutParams mRightParams;
    //计算手指滑动速度
    private VelocityTracker mVelocityTracker;

    //获取屏幕宽度及滑动阈值
    public ListViewSlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = DimenUtil.getScreenWidth();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    //绑定监听侧滑事件的view
    public void setScrollEvent(View bindView) {
        this.mBindView = bindView;
        mBindView.setOnTouchListener(this);
    }

    //将屏幕滚到左侧布局界面
    public void scrollToLeftLayout() {
        new ScrollTask(this).execute(30);
    }

    //将屏幕滚到右侧布局界面
    public void scrollToRightLayout() {
        new ScrollTask(this).execute(-30);
    }

    //左侧布局是否完全显示
    public boolean isLeftLayoutVisible() {
        return isLeftLayoutVisible;
    }


    @Override
    //重新设定左右布局的参数
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //获取左侧布局对象
            mLeftLayout = getChildAt(0);
            mLeftParams = (MarginLayoutParams) mLeftLayout.getLayoutParams();
            mLeftParams.width = mScreenWidth;
            mLeftLayout.setLayoutParams(mLeftParams);
            //获取右侧布局对象
            mRightLayout = getChildAt(1);
            mRightParams = (MarginLayoutParams) mRightLayout.getLayoutParams();
            mLeftEdge = -mRightParams.width;
        }
    }

    @Override
    //监听touch事件
    public boolean onTouch(View view, MotionEvent motionEvent) {
        createVelocityTracker(motionEvent);
        if (mLeftLayout.getVisibility() != View.VISIBLE) {
            mLeftLayout.setVisibility(VISIBLE);
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指按下时,获取相对屏幕x y轴坐标
                xDown = motionEvent.getRawX();
                yDown = motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动时,获取相对屏幕x y轴坐标
                xMove = motionEvent.getRawX();
                yMove = motionEvent.getRawY();
                //手指滑动x y位移
                int moveDistanceX = (int) (xMove - xDown);
                int moveDistanceY = (int) (yMove - yDown);
                //向右滑动时
                if (!isLeftLayoutVisible && moveDistanceX >= mTouchSlop
                        && (isSliding || Math.abs(moveDistanceY) <= mTouchSlop)) {
                    isSliding = true;
                    //右边布局边缘等于负的x位移
                    mRightParams.rightMargin = -moveDistanceX;
                    //再与左边缘进行比较,取较小的值
                    if (mRightParams.rightMargin > mLeftEdge) {
                        mRightParams.rightMargin = mLeftEdge;
                    }
                    mRightLayout.setLayoutParams(mRightParams);
                }
                //向左滑动时
                if (isLeftLayoutVisible && -moveDistanceX >= mTouchSlop) {
                    isSliding = true;
                    mRightParams.rightMargin = mRightEdge - moveDistanceX;
                    if (mRightParams.rightMargin < mRightEdge) {
                        mRightParams.rightMargin = mRightEdge;
                    }
                    mRightLayout.setLayoutParams(mRightParams);
                }
                break;
            case MotionEvent.ACTION_UP:
                xUp = motionEvent.getRawX();
                int upDistanceX = (int) (xUp - xDown);
                if (isSliding) {
                    //当手指抬起时,判断手指意图
                    if (wantToShowLeftLayout()) {
                        if (shouldScrollToLeftLayout()) {
                            scrollToLeftLayout();
                        }
                    } else if (wantToShowRightLayout()) {
                        if (shouldScrollToRightLayout()) {
                            scrollToRightLayout();
                        }
                    }
                } else if (upDistanceX < mTouchSlop && isLeftLayoutVisible) {
                    //向左滑动,显示右侧布局
                    scrollToRightLayout();
                }
                //完成滑动,回收VelocityTracker对象
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        if (view.isEnabled()) {
            if (isSliding) {
                unFocusBindView();
                return true;
            }
            return isLeftLayoutVisible;
        }
        return true;
    }

    //判断当前手势是否想显示右边视图
    private boolean wantToShowRightLayout() {
        return xUp - xDown < 0 && isLeftLayoutVisible;
    }

    //判断当前手势是否想显示左边视图
    private boolean wantToShowLeftLayout() {
        return xUp - xDown > 0 && !isLeftLayoutVisible;
    }

    //判断是否应该将左侧布局显示出来
    //当滑动距离超过屏幕1/2,或者滑动速度超过阈值
    private boolean shouldScrollToLeftLayout() {
        return xUp - xDown > mLeftParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    //判断是否应该将右侧布局显示出来
    //当滑动距离超过屏幕1/2,或者滑动速度超过阈值
    private boolean shouldScrollToRightLayout() {
        return xDown - xUp > mLeftParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    //创建VelocityTracker对象,并将事件添加到mFingerVelocity对象中
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    //返回手指滑动速度
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    //回收mFingerVelocity对象
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    //让绑定的view失去焦点
    private void unFocusBindView() {
        if (mBindView != null) {
            mBindView.setPressed(false);
            mBindView.setFocusable(false);
            mBindView.setFocusableInTouchMode(false);
        }
    }

    static class ScrollTask extends AsyncTask<Integer, Integer, Integer> {
        //静态内部类加弱引用防止内存泄露
        private final WeakReference<ListViewSlideLayout> slideLayoutWeakReference;

        ScrollTask(ListViewSlideLayout slideLayout) {
            slideLayoutWeakReference = new WeakReference<>(slideLayout);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            ListViewSlideLayout slideLayout = slideLayoutWeakReference.get();
            int rightMargin = slideLayout.mRightParams.rightMargin;
            int rightEdge = slideLayout.mRightEdge;
            int leftEdge = slideLayout.mLeftEdge;
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
            while (true) {
                rightMargin = rightMargin + integers[0];
                if (rightMargin < rightEdge) {
                    rightMargin = rightEdge;
                    break;
                }
                if (rightMargin > leftEdge) {
                    rightMargin = leftEdge;
                    break;
                }
                publishProgress(rightMargin);
                //使线程睡眠,达到滚动效果
                slideLayout.sleep(15);
            }
            slideLayout.isLeftLayoutVisible = integers[0] <= 0;
            slideLayout.isSliding = false;
            return rightMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ListViewSlideLayout slideLayout = slideLayoutWeakReference.get();
            slideLayout.mRightParams.rightMargin = values[0];
            slideLayout.mRightLayout.setLayoutParams(slideLayout.mRightParams);
            slideLayout.unFocusBindView();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            ListViewSlideLayout slideLayout = slideLayoutWeakReference.get();
            slideLayout.mRightParams.rightMargin = integer;
            slideLayout.mRightLayout.setLayoutParams(slideLayout.mRightParams);
        }
    }

    //使当前线程睡眠,从而使动画效果出来
    @SuppressWarnings("SameParameterValue")
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
