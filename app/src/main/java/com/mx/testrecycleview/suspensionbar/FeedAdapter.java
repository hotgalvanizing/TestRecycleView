package com.mx.testrecycleview.suspensionbar;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.testrecycleview.R;
import com.squareup.picasso.Picasso;

/**
 * Created by snowbean on 16-11-4.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private static final String TAG = "FeedAdapter";

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        Picasso.with(holder.itemView.getContext())
                .load(getAvatarResId(position))
                .centerInside()
                .fit()
                .into(holder.mIvAvatar);

        Picasso.with(holder.itemView.getContext())
                .load(getContentResId(position))
                .centerInside()
                .fit()
                .into(holder.mIvContent);

        holder.mTvNickname.setText("Taeyeon " + position);
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

    private int getContentResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.taeyeon_one;
            case 1:
                return R.drawable.taeyeon_two;
            case 2:
                return R.drawable.taeyeon_three;
            case 3:
                return R.drawable.taeyeon_four;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        public ImageView mIvAvatar;
        public ImageView mIvContent;
        public TextView mTvNickname;

        public FeedHolder(View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mIvContent = itemView.findViewById(R.id.iv_content);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
        }
    }
}
