package xyz.egoistk.aiya.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.util.List;

import xyz.egoistk.aiya.R;

/**
 * Created by EGOISTK on 2017/3/19.
 */

public class StringScrollPicker extends ScrollPickerView<String> {

    private int mMeasureWidth;
    private int mMeasureHeight;

    private Paint mPaint;
    private int mMinTextSize = 20; // 上下未被选中item的字体大小
    private int mMaxTextSize = 30; // 中间选中item的字体大小
    private int mStartColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark); // 中间选中item的颜色
    private int mEndColor = ContextCompat.getColor(getContext(), R.color.colorDivider); // 上下未被选中item的颜色

    public StringScrollPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StringScrollPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollPickerView);
            setMinTestSize(typedArray.getDimensionPixelSize(
                    R.styleable.ScrollPickerView_spv_min_text_size, mMinTextSize));
            setMaxTestSize(typedArray.getDimensionPixelSize(
                    R.styleable.ScrollPickerView_spv_max_text_size, mMaxTextSize));
            setStartColor(typedArray.getColor(
                    R.styleable.ScrollPickerView_spv_start_color, mStartColor));
            setEndColor(typedArray.getColor(
                    R.styleable.ScrollPickerView_spv_end_color, mEndColor));
            setCenterItemBackground(typedArray.getColor(
                    R.styleable.ScrollPickerView_spv_center_item_background, getCenterItemBackground()));
            setVisibleItemCount(typedArray.getInt(
                    R.styleable.ScrollPickerView_spv_visible_item_count, getVisibleItemCount()));
            setCenterPosition(typedArray.getInt(
                    R.styleable.ScrollPickerView_spv_center_item_position, getCenterPosition()));
            setIsCirculation(typedArray.getBoolean(
                    R.styleable.ScrollPickerView_spv_is_circulation, isIsCirculation()));
            setDisallowInterceptTouch(typedArray.getBoolean(
                    R.styleable.ScrollPickerView_spv_disallow_intercept_touch, isDisallowInterceptTouch()));
            typedArray.recycle();
        }
    }

    public void setStartColor(int startColor) {
        mStartColor = startColor;
    }

    public void setEndColor(int endColor) {
        mEndColor = endColor;
    }

    public void setMinTestSize(int minTestSize) {
        mMinTextSize = minTestSize;
    }

    public void setMaxTestSize(int maxTestSize) {
        mMaxTextSize = maxTestSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();
    }

    @Override
    public void drawItem(Canvas canvas, List<String> data, int position,
                         int relative, float moveLength, float top) {
        String text = data.get(position);
        int itemHeight = getItemHeight();
        switch (relative) {
            case -1:
                mPaint.setTextSize(mMinTextSize
                        + (moveLength < 0 ? 0 : (mMaxTextSize - mMinTextSize) * moveLength / itemHeight));
                break;
            case 0:
                mPaint.setTextSize(mMinTextSize
                        + (mMaxTextSize - mMinTextSize) * (itemHeight - Math.abs(moveLength)) / itemHeight);
                break;
            case 1:
                mPaint.setTextSize(mMinTextSize
                        + (moveLength > 0 ? 0 : (mMaxTextSize - mMinTextSize) * -moveLength / itemHeight));
                break;
            default:
                mPaint.setTextSize(mMinTextSize);
                break;
        }
        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        // 绘制文字时，文字的baseline是对齐ｙ坐标的，下面换算使其垂直居中。fmi.top值是相对baseline的，为负值
        float x = (mMeasureWidth - mPaint.measureText(text)) / 2;
        float y = top + itemHeight / 2 - fmi.descent + (fmi.bottom - fmi.top) / 2;
        computeColor(relative, itemHeight, moveLength);
        canvas.drawText(text, x, y, mPaint);
    }

    /**
     * 计算字体颜色，渐变
     *
     * @param relative 　相对中间item的位置
     */
    private void computeColor(int relative, int mItemHeight, float moveLength) {

        int color = mEndColor; // 其他默认为ｍEndColor

        if (relative == -1 || relative == 1) { // 上一个或下一个
            if ((relative == -1 && moveLength < 0) || (relative == 1 && moveLength > 0)) {
                // 处理上一个item且向上滑动或者下一个item且向下滑动,颜色为mEndColor
                color = mEndColor;
            } else { // 计算渐变的颜色
                float rate = (mItemHeight - Math.abs(moveLength)) / mItemHeight;
                color = computeGradientColor(mStartColor, mEndColor, rate);
            }
        } else if (relative == 0) { // 中间选中item
            float rate = Math.abs(moveLength) / mItemHeight;
            color = computeGradientColor(mStartColor, mEndColor, rate);
        }
        mPaint.setColor(color);
    }

    private int computeGradientColor(int startColor, int endColor, float rate) {
        if (rate < 0) rate = 0;
        if (rate > 1) rate = 1;

        int alpha = Color.alpha(endColor) - Color.alpha(startColor);
        int red = Color.red(endColor) - Color.red(startColor);
        int green = Color.green(endColor) - Color.green(startColor);
        int blue = Color.blue(endColor) - Color.blue(startColor);

        return Color.argb(
                Math.round(Color.alpha(startColor) + alpha * rate),
                Math.round(Color.red(startColor) + red * rate),
                Math.round(Color.green(startColor) + green * rate),
                Math.round(Color.blue(startColor) + blue * rate));
    }
}
