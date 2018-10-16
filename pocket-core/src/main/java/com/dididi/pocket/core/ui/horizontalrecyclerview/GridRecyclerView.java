package com.dididi.pocket.core.ui.horizontalrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dididi.pocket.core.ui.pageindicator.DotPageIndicator;

import java.util.List;


/**
 * @author dididi
 * @describe
 * @since 16/10/2018
 */

public class GridRecyclerView extends RecyclerView {

    private Context mContext = null;

    private GridRecyclerViewAdapter mAdapter = null;

    /**
     * 滑动阈值
     */
    private int thresholdDistance;

    /**
     * 手指按下x坐标
     */
    private float downX = 0;

    /**
     * 手指滑动距离
     */
    private float slideDistance = 0;

    /**
     * 当前手指x坐标
     */
    private float scrollX = 0;

    /**
     * 默认行数
     */
    private int spanRow = 2;

    /**
     * 默认列数
     */
    private int spanColumn = 3;

    /**
     * 总页数
     */
    private int totalPage = 0;

    /**
     * 当前页数
     */
    private int currentPage = 1;

    /**
     * 每页间距
     */
    private int pageMargin = 0;

    /**
     * 页面指示器
     */
    private DotPageIndicator mIndicator = null;

    public GridRecyclerView(@NonNull Context context) {
        super(context);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 默认初始化
     */
    private void defaultInit(Context context) {
        this.mContext = context;
        setLayoutManager(new AutoGridLayoutManager(
                mContext, spanRow, AutoGridLayoutManager.HORIZONTAL, false));
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 设置行数和每页列数
     *
     * @param spanRow    行数，<=0表示使用默认的行数
     * @param spanColumn 每页列数，<=0表示使用默认每页列数
     */
    public void setPageSize(int spanRow, int spanColumn) {
        this.spanRow = spanRow <= 0 ? this.spanRow : spanRow;
        this.spanColumn = spanColumn <= 0 ? this.spanColumn : spanColumn;
        setLayoutManager(new AutoGridLayoutManager(
                mContext, this.spanRow, AutoGridLayoutManager.HORIZONTAL, false));
    }

    /**
     * 设置页间距
     *
     * @param pageMargin 间距(px)
     */
    public void setPageMargin(int pageMargin) {
        this.pageMargin = pageMargin;
    }

    /**
     * 设置指示器
     *
     * @param indicator 指示器布局
     */
    public void setIndicator(DotPageIndicator indicator) {
        this.mIndicator = indicator;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        thresholdDistance = getMeasuredWidth() / 3;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.mAdapter = (GridRecyclerViewAdapter) adapter;
        update();
    }

    /**
     * 更新页码指示器和相关数据
     */
    private void update() {
        // 计算总页数
        int temp = ((int) Math.ceil(mAdapter.dataList.size() / (double) (spanRow * spanColumn)));
        if (temp != totalPage) {
            mIndicator.initIndicator(temp);
            // 页码减少且当前页为最后一页
            if (temp < totalPage && currentPage == totalPage) {
                currentPage = temp;
                // 执行滚动
                smoothScrollBy(-getWidth(), 0);
            }
            mIndicator.setSelectedPage(currentPage - 1);
            totalPage = temp;
        }
    }

    /**
     * 滑动事件处理(滑动没超过阈值时不切换页面)
     * @param event touch事件
     * @return 是否拦截
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (currentPage == totalPage && downX - event.getX() > 0) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //计算滑动距离
                slideDistance = event.getX() - downX;
                if (Math.abs(slideDistance) > thresholdDistance) {
                    // 滑动距离足够，执行翻页
                    if (slideDistance > 0) {
                        // 上一页
                        currentPage = currentPage == 1 ? 1 : currentPage - 1;
                    } else {
                        // 下一页
                        currentPage = currentPage == totalPage ? totalPage : currentPage + 1;
                    }
                    // 修改指示器选中项(从0开始 所以需要-1)
                    mIndicator.setSelectedPage(currentPage - 1);
                }
                // 执行滚动
                smoothScrollBy((int) ((currentPage - 1) * getWidth() - scrollX), 0);
                return true;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        scrollX += dx;
        super.onScrolled(dx, dy);
    }


    public class GridRecyclerViewAdapter extends Adapter<ViewHolder> {

        private List<?> dataList = null;
        private Callback mCallback = null;
        /**
         * 每个item的宽度
         */
        private int itemWidth = 0;
        /**
         * 总共的item个数
         */
        private int itemCount = 0;

        public GridRecyclerViewAdapter(List<?> dataList, Callback mCallback) {
            this.dataList = dataList;
            this.mCallback = mCallback;
            //item计算公式=
            itemCount = dataList.size() + spanRow * spanColumn;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (itemWidth <= 0) {
                //item宽度计算公式=(父布局宽度-左右页面间距)/列数
                itemWidth = (viewGroup.getWidth() - pageMargin * 2) / spanColumn;
            }
            ViewHolder holder = mCallback.onCreateViewHolder(viewGroup, i);
            holder.itemView.measure(0, 0);
            holder.itemView.getLayoutParams().width = itemWidth;
            holder.itemView.getLayoutParams().height = holder.itemView.getMeasuredHeight();
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (spanColumn == 1) {
                // 每个Item距离左右两侧各pageMargin
                viewHolder.itemView.getLayoutParams().width = itemWidth + pageMargin * 2;
                viewHolder.itemView.setPadding(pageMargin, 0, pageMargin, 0);
            } else {
                int m = i % (spanRow * spanColumn);
                if (m < spanRow) {
                    // 每页左侧的Item距离左边pageMargin
                    viewHolder.itemView.getLayoutParams().width = itemWidth + pageMargin;
                    viewHolder.itemView.setPadding(pageMargin, 0, 0, 0);
                } else if (m >= spanRow * spanColumn - spanRow) {
                    // 每页右侧的Item距离右边pageMargin
                    viewHolder.itemView.getLayoutParams().width = itemWidth + pageMargin;
                    viewHolder.itemView.setPadding(0, 0, pageMargin, 0);
                } else {
                    // 中间的正常显示
                    viewHolder.itemView.getLayoutParams().width = itemWidth;
                    viewHolder.itemView.setPadding(0, 0, 0, 0);
                }
            }

            if (i < dataList.size()) {
                viewHolder.itemView.setVisibility(View.VISIBLE);
                mCallback.onBindViewHolder(viewHolder, i);
            } else {
                viewHolder.itemView.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return itemCount;
        }

        /**
         * 删除Item
         * @param position 位置
         */
        public void remove(int position) {
            if (position < dataList.size()) {
                // 删除数据
                dataList.remove(position);
                itemCount--;
                // 删除Item
                notifyItemRemoved(position);
                // 更新界面上发生改变的Item
                notifyItemRangeChanged(position, currentPage * spanRow * spanColumn);
                // 更新页码指示器
                update();
            }
        }
    }

    public interface Callback {
        /**
         * 创建VieHolder
         *
         * @param parent
         * @param viewType
         */
        RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        /**
         * 绑定数据到ViewHolder
         *
         * @param holder
         * @param position
         */
        void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
    }
}
