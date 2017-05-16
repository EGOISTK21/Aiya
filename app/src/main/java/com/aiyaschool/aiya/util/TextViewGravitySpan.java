package com.aiyaschool.aiya.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

import com.aiyaschool.aiya.R;

/**
 * Created by EGOISTK21 on 2017/5/16.
 */

public class TextViewGravitySpan extends ReplacementSpan {

    private float fontSizeSp = 23;
    private Context mContext;

    public TextViewGravitySpan(Context context, float frontSizeSp) {
        this.mContext = context;
        this.fontSizeSp = frontSizeSp;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, @Nullable Paint.FontMetricsInt fm) {
        text = text.subSequence(start, end);
        Paint p = getCustomTextPaint(paint);
        return (int) p.measureText(text.toString());
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        text = text.subSequence(start, end);
        Paint p = getCustomTextPaint(paint);
        p.setColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        Paint.FontMetricsInt fm = p.getFontMetricsInt();
        canvas.drawText(text.toString(), x, y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2), p);    //此处重新计算y坐标，使字体居中
    }

    private TextPaint getCustomTextPaint(Paint srcPaint) {
        TextPaint paint = new TextPaint(srcPaint);
        paint.setTextSize(ViewUtil.sp2px(mContext, fontSizeSp));   //设定字体大小, sp转换为px
        return paint;
    }
}
