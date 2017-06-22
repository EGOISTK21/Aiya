package com.aiyaschool.aiya.message.utils;

import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

/**
 * Created by EGOISTK21 on 2017/6/21.
 */

public class MsgPreUtil {

    public static String toText(TIMMessage timMessage) {
        String text = null;
        TIMElem timElem = timMessage.getElement((int) (timMessage.getElementCount() - 1));
        TIMElemType type = timElem.getType();
        if (type == TIMElemType.Text) {
            text = ((TIMTextElem) timElem).getText();
        } else if (type == TIMElemType.Image) {
            text = "图片";
        }
        return text;
    }

}
