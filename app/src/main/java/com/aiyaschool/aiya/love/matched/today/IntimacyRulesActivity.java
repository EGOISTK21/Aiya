package com.aiyaschool.aiya.love.matched.today;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.aiyaschool.aiya.R;

/**
 * Created by EGOISTK21 on 2017/4/8.
 */

public class IntimacyRulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intimacy_rules);
        initView();
    }

    private void initView() {
        Spannable spannable = new SpannableString("匹配成功后，初始亲密度为50");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 12, 14, Spanned.SPAN_POINT_MARK);
        Parcel p = Parcel.obtain();
        p.writeFloat(1.23f);
        p.setDataPosition(0);
        spannable.setSpan(new RelativeSizeSpan(p), 12, 14, Spanned.SPAN_POINT_MARK);
        p.recycle();
        ((TextView) findViewById(R.id.tv_rule0)).setText(spannable);
        spannable = new SpannableString("达成每日标准，获得亲密度加成");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 12, 14, Spanned.SPAN_POINT_MARK);
        ((TextView) findViewById(R.id.tv_sub_title)).setText(spannable);
        spannable = new SpannableString("≥15条+2");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 4, 6, Spanned.SPAN_POINT_MARK);
        ((TextView) findViewById(R.id.tv_rule1_1)).setText(spannable);
        spannable = new SpannableString("≥40条+4");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 4, 6, Spanned.SPAN_POINT_MARK);
        ((TextView) findViewById(R.id.tv_rule1_2)).setText(spannable);
        spannable = new SpannableString("≥1次  +3");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 5, 7, Spanned.SPAN_POINT_MARK);
        ((TextView) findViewById(R.id.tv_rule2_1)).setText(spannable);
        spannable = new SpannableString("≥3次  +6");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 5, 7, Spanned.SPAN_POINT_MARK);
        ((TextView) findViewById(R.id.tv_rule2_2)).setText(spannable);
        spannable = new SpannableString("≥2次  +3");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 5, 7, Spanned.SPAN_POINT_MARK);
        ((TextView) findViewById(R.id.tv_rule3_1)).setText(spannable);
        spannable = new SpannableString("≥5次  +6");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.colorPrimaryDark)), 5, 7, Spanned.SPAN_POINT_MARK);
        ((TextView) findViewById(R.id.tv_rule3_2)).setText(spannable);
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
