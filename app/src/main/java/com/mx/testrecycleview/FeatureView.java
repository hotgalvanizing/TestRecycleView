
package com.mx.testrecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public final class FeatureView extends FrameLayout {

	public FeatureView(Context context) {
		super(context);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.feature, this);
	}

	public synchronized void setTitleId(int titleId) {
		((TextView) (findViewById(R.id.title))).setText(titleId);
	}
	public synchronized void setTitleId(String titleId, boolean issub) {
		if (issub) {
			((TextView) (findViewById(R.id.title))).setText("         "+titleId);
		} else{
			((TextView) (findViewById(R.id.title))).setText(titleId);
		}

	}
}
