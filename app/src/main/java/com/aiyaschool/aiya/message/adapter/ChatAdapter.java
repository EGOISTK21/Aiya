package com.aiyaschool.aiya.message.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.aiyaschool.aiya.message.ui.view.ChatMessageView;
import com.aiyaschool.aiya.message.ui.view.ChatTimeZoneView;
import com.tencent.TIMElem;
import com.tencent.TIMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    private final String TAG = ChatAdapter.class.getSimpleName();
    private ChatQQActivity activity;
    public ArrayList<TIMElem> msgList = new ArrayList<>();
    public ArrayList<Integer> isSendList = new ArrayList<>();
    public ArrayList<Long> timeList = new ArrayList<>();
    private Map<Integer,Long> showTimeMap = new HashMap<>();
    public boolean onBind = false;//adapter加载时,不允许刷新
    private final int ME = 1;
    private final int OTHER = 0;



    //msg from 收到的消息
    private final int MSG_FROM = 1;
    //msg to 我发送出去的消息
    private final int MSG_TO = 0;

    public ChatAdapter (ChatQQActivity activity, ArrayList<TIMElem> msgList, ArrayList<Integer> isSendList, ArrayList<Long> timeList){
        this.activity = activity;
        this.msgList = msgList;
        this.isSendList = isSendList;
        this.timeList = timeList;
        updateShowTimeMap();
    }

    public void updateShowTimeMap(){
        //todo
//        if(msgList!=null){
//            showTimeMap.clear();
//            int index = 0;
//            Iterator<String> iterator = msgList.iterator();
//            while(iterator.hasNext()){
//
//            }
//        }
    }

    public void customeNotifyDataSetChanged(){
        updateShowTimeMap();
        this.notifyDataSetChanged();
    }

    public void customNotifyItemRangedInserted(int positionStart,int itemCount,RecyclerView recyclerView){
        //todo
        //定位问题
        //插入新数据前，记录消息id
//        this.notifyDataSetChanged(positionStart,itemCount);
        this.notifyItemRangeChanged(positionStart,itemCount);
//        this.notifyItemRangeInserted(positionStart, itemCount);
        updateShowTimeMap();
        activity.scrollToBottom();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            return new MyViewHolder(View.inflate(activity, R.layout.chat_item_to,null));
        }else{
            return new MyViewHolder(View.inflate(activity, R.layout.chat_item_from,null));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        onBind = true;
        handleTime(position,holder.timeZone);
        TIMElem message = msgList.get(position);

        holder.rlMsg.setVisibility(View.VISIBLE);
        handleHand(position,holder.imgUserIcon);
        holder.viewMessage.setViews(message,isSendList.get(position));
        holder.timeZone.setViews(timeList.get(position),0);
        onBind = false;
    }

    private void handleHand(int position,ImageView imageView){
        //todo
    }


    private void handleTime(int position,ChatTimeZoneView timezone){
        boolean isShow = false;
//        long currentMsgTime =
    }

    @Override
    public int getItemViewType(int position) {

        if(isSendList.get(position)==1){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public void addChatMsg(TIMMessage msg) {
        //此处应有本地缓存
        int arg1,arg2;
        ArrayList<TIMElem> aux = new ArrayList<>();
        ArrayList<Integer> aux1 = new ArrayList<>();
        ArrayList<Long> aux2 = new ArrayList<>();
        int k = msg.isSelf()?1:0;
        long l = msg.timestamp();
        arg1 = aux.size();
        for(int j=0;j<msg.getElementCount();j++){
            aux.add(msg.getElement(j));
            aux1.add(k);
            aux2.add(l);
        }
        msgList.addAll(aux);
        isSendList.addAll(aux1);
        timeList.addAll(aux2);
        customNotifyItemRangedInserted(arg1,aux.size(),null);
    }

    public void addBeforeChatMsgs(List<TIMMessage> msgs) {
        int auxx = 0;
        for(TIMMessage msg:msgs){
            ArrayList<TIMElem> aux = new ArrayList<>();
            ArrayList<Integer> aux1 = new ArrayList<>();
            ArrayList<Long> aux2 = new ArrayList<>();
            int k = msg.isSelf()?1:0;
            long l = msg.timestamp();
            for(int j=0;j<msg.getElementCount();j++){
                aux.add(msg.getElement(j));
                aux1.add(k);
                aux2.add(l);
            }
            msgList.addAll(auxx,aux);
            isSendList.addAll(auxx,aux1);
            timeList.addAll(auxx,aux2);
            auxx++;

        }
        customeNotifyDataSetChanged();
    }

    public void addAll(int i, List<TIMMessage> list) {
        int auxhzj2151 = 0;
        for(TIMMessage m:list){
            msgList.add(0,list.get(auxhzj2151).getElement(0));
            isSendList.add(0,list.get(auxhzj2151).isSelf()?1:0);
            timeList.add(0,list.get(auxhzj2151).timestamp());
            auxhzj2151++;
        }
    }

    public void customeNotifyItemRangeInserted(int i, int size, RecyclerView recyclerView) {
        //todo
        this.notifyItemRangeInserted(i, size);
        //        customeNotifyDataSetChanged();
        updateShowTimeMap();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        
        public ChatTimeZoneView timeZone;//时间戳
        public RelativeLayout rlMsg;
        public ImageView imgUserIcon;// 头像
        public ChatMessageView viewMessage;
        public MyViewHolder(View itemView) {
            super(itemView);
            timeZone = (ChatTimeZoneView) itemView.findViewById(R.id.view_chat_timezone);
            rlMsg = (RelativeLayout) itemView.findViewById(R.id.rl_msg_content_info);
            imgUserIcon = (ImageView) itemView.findViewById(R.id.img_chat_fromIcon);
            viewMessage = (ChatMessageView) itemView.findViewById(R.id.view_list_message);
        }

    }
}
