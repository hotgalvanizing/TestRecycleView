package com.mx.testrecycleview.cardrecycleview;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class CardLayoutManager extends RecyclerView.LayoutManager{

    public static final int DEFAULT_GROUP_SIZE = 5;

    private int mGroupSize;
    private int mHorizontalOffset;
    private int mVerticalOffset;
    private int mTotalWidth;
    private int mTotalHeight;
    private int mGravityOffset;
    private boolean isGravityCenter;

    private Pool<Rect> mItemFrames;

    public CardLayoutManager(boolean center) {
        this(DEFAULT_GROUP_SIZE, center);
    }

    public CardLayoutManager(int groupSize, boolean center) {
        mGroupSize = groupSize;
        isGravityCenter = center;
        mItemFrames = new Pool<>(new Pool.New<Rect>() {
            @Override
            public Rect get() { return new Rect();}
        });
    }

    /**
     * Create a default <code>LayoutParams</code> object for a child of the RecyclerView.
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) { return;}
        //detachAndScrapAttachedViews方法, 这个方法是RecyclerView.LayoutManager的,
        // 它的作用是将界面上的所有item都detach掉, 并缓存在scrap中,以便下次直接拿出来显示.
        detachAndScrapAttachedViews(recycler);

        //这里是因为在我们的这个效果中所有的item大小都是一样的,
        // 所以我们只要获取第一个的大小, 就知道所有的item的大小了.
        View first = recycler.getViewForPosition(0);
        measureChildWithMargins(first, 0, 0);
        //这个getDecoratedXXX的作用就是获取该view以及他的decoration的值,
        // 大家都知道RecyclerView是可以设置decoration的
        int itemWidth = getDecoratedMeasuredWidth(first);
        int itemHeight = getDecoratedMeasuredHeight(first);
        //这两句主要是来获取每一组中第一行和第二行中item的个数.
        int firstLineSize = mGroupSize / 2 + 1;
        int secondLineSize = firstLineSize + mGroupSize / 2;
        if (isGravityCenter && firstLineSize * itemWidth < getHorizontalSpace()) {
            mGravityOffset = (getHorizontalSpace() - firstLineSize * itemWidth) / 2;
        } else {
            mGravityOffset = 0;
        }

        for (int i = 0; i < getItemCount(); i++) {
            Rect item = mItemFrames.get(i);
            float coefficient = isFirstGroup(i) ? 1.5f : 1.f;
            int offsetHeight = (int) ((i / mGroupSize) * itemHeight * coefficient);

            // 每一组的第一行
            if (isItemInFirstLine(i)) {
                int offsetInLine = i < firstLineSize ? i : i % mGroupSize;
                item.set(mGravityOffset + offsetInLine * itemWidth, offsetHeight, mGravityOffset + offsetInLine * itemWidth + itemWidth,
                        itemHeight + offsetHeight);
            }else {
                int lineOffset = itemHeight / 2;
                int offsetInLine = (i < secondLineSize ? i : i % mGroupSize) - firstLineSize;
                item.set(mGravityOffset + offsetInLine * itemWidth + itemWidth / 2,
                        offsetHeight + lineOffset, mGravityOffset + offsetInLine * itemWidth + itemWidth  + itemWidth / 2,
                        itemHeight + offsetHeight + lineOffset);
            }
        }

        mTotalWidth = Math.max(firstLineSize * itemWidth, getHorizontalSpace());
        int totalHeight = getGroupSize() * itemHeight;
        if (!isItemInFirstLine(getItemCount() - 1)) { totalHeight += itemHeight / 2;}
        mTotalHeight = Math.max(totalHeight, getVerticalSpace());
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) { return;}
        Rect displayRect = new Rect(mHorizontalOffset, mVerticalOffset,
                getHorizontalSpace() + mHorizontalOffset,
                getVerticalSpace() + mVerticalOffset);

        // Rect rect = new Rect();
        // for (int i = 0; i < getChildCount(); i++) {
        // View item = getChildAt(i);
        // rect.left = getDecoratedLeft(item);
        // rect.top = getDecoratedTop(item);
        // rect.right = getDecoratedRight(item);
        // rect.bottom = getDecoratedBottom(item);
        // if (!Rect.intersects(displayRect, rect)) {
        // removeAndRecycleView(item, recycler);
        // }
        // }

        for (int i = 0; i < getItemCount(); i++) {
            Rect frame = mItemFrames.get(i);
            if (Rect.intersects(displayRect, frame)) {
                View scrap = recycler.getViewForPosition(i);
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                layoutDecorated(scrap, frame.left - mHorizontalOffset, frame.top - mVerticalOffset,
                        frame.right - mHorizontalOffset, frame.bottom - mVerticalOffset);
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (mVerticalOffset + dy < 0) {
            dy = -mVerticalOffset;
        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
        }

        offsetChildrenVertical(-dy);
        fill(recycler, state);
        mVerticalOffset += dy;
        return dy;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (mHorizontalOffset + dx < 0) {
            dx = -mHorizontalOffset;
        } else if (mHorizontalOffset + dx > mTotalWidth - getHorizontalSpace()) {
            dx = mTotalWidth - getHorizontalSpace() - mHorizontalOffset;
        }

        offsetChildrenHorizontal(-dx);
        fill(recycler, state);
        mHorizontalOffset += dx;
        return dx;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private boolean isItemInFirstLine(int index) {
        int firstLineSize = mGroupSize / 2 + 1;
        return index < firstLineSize || (index >= mGroupSize && index % mGroupSize < firstLineSize);
    }

    private int getGroupSize() {
        return (int) Math.ceil(getItemCount() / (float)mGroupSize);
    }

    private boolean isFirstGroup(int index) {
        return index < mGroupSize;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
