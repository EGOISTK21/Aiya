package com.aiyaschool.aiya.message.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aiyaschool.aiya.message.ui.view.ChatGiftOneView;
import com.aiyaschool.aiya.message.ui.view.ChatImgView;
import com.aiyaschool.aiya.message.ui.view.ChatMsgItemView;
import com.aiyaschool.aiya.message.ui.view.ChatSoundView;
import com.aiyaschool.aiya.message.ui.view.ChatTxtView;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;

/**
 * Created by ShootHzj on 2016/11/1.
 */

public class ChatMsgItemFactory {
    public static ChatMsgItemView createItemView(Context context, TIMElem messageBean, int isSend){
        Toast.makeText(context, "111", Toast.LENGTH_SHORT).show();
        Log.e("111", String.valueOf(isSend));
        ChatMsgItemView itemView;
        itemView = getChatViewByMsgType(context,messageBean,isSend);
        return itemView;
    }

    private static ChatMsgItemView getChatViewByMsgType(Context context, TIMElem messageBean, int isSend) {
        if(messageBean.getType()== TIMElemType.Text){
            return new ChatTxtView(context,messageBean);
        }else if(messageBean.getType()== TIMElemType.Image) {
            return new ChatImgView(context,messageBean);
        }else if(messageBean.getType()== TIMElemType.Sound){
            return new ChatSoundView(context,messageBean,isSend);
        }else if(messageBean.getType()== TIMElemType.Custom){
            TIMCustomElem tim = (TIMCustomElem) messageBean;
            byte[] parse = tim.getData();
            if(parse[0]==0){
                if(parse[1]==1){
                    return new ChatGiftOneView(context,null);
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
