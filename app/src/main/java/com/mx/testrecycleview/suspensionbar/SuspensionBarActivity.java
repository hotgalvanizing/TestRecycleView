package com.mx.testrecycleview.suspensionbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mx.testrecycleview.R;
import com.squareup.picasso.Picasso;

public class SuspensionBarActivity extends AppCompatActivity {

    private RecyclerView mFeedList;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;
    private int mCurrentPosition = 0;

    private int mSuspensionHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspension_bar);

        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.tv_nickname);
        mSuspensionIv = findViewById(R.id.iv_avatar);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final FeedAdapter adapter = new FeedAdapter();

        mFeedList = findViewById(R.id.feed_list);
        mFeedList.setLayoutManager(linearLayoutManager);
        mFeedList.setAdapter(adapter);

        mFeedList.setHasFixedSize(false);

        mFeedList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * @param recyclerView  当前在滚动的RecyclerView
             * @param newState      当前滚动状态 0,1,2 静止，拖动滚动，自动滚动
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            /**
             * @param recyclerView  当前滚动的view
             * @param dx            水平滚动距离
             * @param dy            垂直滚动距离
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= mSuspensionHeight) {

                        mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        mSuspensionBar.setY(0);
                    }
                }

                //刷新当前位置
                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    //获取第一个可见view的位置
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    updateSuspensionBar();
                    mSuspensionBar.setY(0);
                }
            }
        });

        updateSuspensionBar();
    }

    private void updateSuspensionBar() {
        Picasso.with(SuspensionBarActivity.this)
                .load(getAvatarResId(mCurrentPosition))
                .centerInside()
                .fit()
                .into(mSuspensionIv);

        mSuspensionTv.setText("Taeyeon " + mCurrentPosition);
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }
}
