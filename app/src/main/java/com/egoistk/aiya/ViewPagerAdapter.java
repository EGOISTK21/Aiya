package com.egoistk.aiya;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by EGOISTK on 2017/3/14.
 */

class ViewPagerAdapter extends PagerAdapter {

    private TextView mTextView;
    private List<View> mViews;
    private Context mContext;

    ViewPagerAdapter(List<View> views, Context context) {
        this.mViews = views;
        this.mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
        Log.i("remove", String.valueOf(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        switch (position) {
            case 0:
                mTextView = (TextView) mViews.get(0).findViewById(R.id.message1);
                mTextView.setOnClickListener(new TextView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTextView.setText("社区状态已被更改");
                    }
                });
                break;
        }
        Log.i("add", String.valueOf(position));
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
