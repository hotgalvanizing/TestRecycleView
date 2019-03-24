package com.mx.testrecycleview;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mx.testrecycleview.base.FundamentalActivity;
import com.mx.testrecycleview.multisuspensionbar.MultiSuspensionBarActivity;
import com.mx.testrecycleview.rvadimage.RvAdImageActivity;
import com.mx.testrecycleview.suspensionbar.SuspensionBarActivity;

public class MainActivity extends ListActivity {

    private static class DemoDetails {
        private final String titleId;
        private final String descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(String titleId, String descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }
            DemoDetails demo = getItem(position);
            featureView.setTitleId(demo.titleId, demo.activityClass != null);
            return featureView;
        }
    }

    private static final DemoDetails[] demos = {

            new DemoDetails("RecycleViewDemo", "", null),
            new DemoDetails("基本用法", "", FundamentalActivity.class),
            new DemoDetails("悬浮条实现", "", SuspensionBarActivity.class),
            new DemoDetails("悬浮条实现2", "", MultiSuspensionBarActivity.class),
            new DemoDetails("RecycleView创意广告", "", RvAdImageActivity.class)

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("RecycleViewDemo");
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        if (demo.activityClass != null) {
            Log.i("MY", "demo!=null");
            startActivity(new Intent(this.getApplicationContext(),
                    demo.activityClass));
        }

    }
}
