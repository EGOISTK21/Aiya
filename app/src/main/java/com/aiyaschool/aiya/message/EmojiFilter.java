package com.aiyaschool.aiya.message;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.widget.EditText;

import com.sj.emoji.EmojiDisplay;
import com.sj.emoji.EmojiSpan;

import java.util.regex.Matcher;

import sj.keyboard.interfaces.EmoticonFilter;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

/**
 * Created by XZY on 2017/3/8.
 */

public class EmojiFilter extends EmoticonFilter {

    private int emojiSize = -1;


    @Override
    public void filter(EditText editText, CharSequence text, int start, int lengthBefore, int lengthAfter) {
        emojiSize = emojiSize == -1 ? EmoticonsKeyboardUtils.getFontHeight(editText) : emojiSize;
        clearSpan(editText.getText(), start, text.toString().length());
        Matcher m = EmojiDisplay.getMatcher(text.toString().substring(start, text.toString().length()));
        if (m != null) {
            while (m.find()) {
                String emojiHex = Integer.toHexString(Character.codePointAt(m.group(), 0));
                Drawable drawable = getDrawable(editText.getContext(), EmojiDisplay.HEAD_NAME + emojiHex);
                if (drawable != null) {
                    int itemHeight;
                    int itemWidth;
                    if (emojiSize == EmojiDisplay.WRAP_DRAWABLE) {
                        itemHeight = drawable.getIntrinsicHeight();
                        itemWidth = drawable.getIntrinsicWidth();
                    } else {
                        itemHeight = emojiSize;
                        itemWidth = emojiSize;
                    }

                    drawable.setBounds(0, 0, itemHeight, itemWidth);
                    EmojiSpan imageSpan = new EmojiSpan(drawable);
                    editText.getText().setSpan(imageSpan, start + m.start(), start + m.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    private void clearSpan(Spannable spannable, int start, int end) {
        if (start == end) {
            return;
        }
        EmojiSpan[] oldSpans = spannable.getSpans(start, end, EmojiSpan.class);
        for (int i = 0; i < oldSpans.length; i++) {
            spannable.removeSpan(oldSpans[i]);
        }
    }
}
