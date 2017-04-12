package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.adapter.AppsAdapter;
import com.aiyaschool.aiya.message.bean.AppBean;
import com.tencent.TIMConversation;

import java.util.ArrayList;

import sj.keyboard.utils.EmoticonsKeyboardUtils;

/**
 * Created by ShootHzj on 2016/11/2.
 */

public class ChatSelectGiftView extends RelativeLayout{

    protected View view;
    protected Context context;
    private GridView gv_apps;
    private boolean isGroup = false;
    private TIMConversation conversation;

    public ChatSelectGiftView(Context context, TIMConversation conversation) {
        super(context);
        this.context = context;
        this.conversation = conversation;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_apps, this);
        setBackgroundColor(getResources().getColor(R.color.white));
        init();
    }
    protected void init() {
        gv_apps = (GridView) view.findViewById(R.id.gv_apps);
        gv_apps.setPadding(0, 0, 0, EmoticonsKeyboardUtils.dip2px(context, 20));
        LayoutParams params = (LayoutParams) gv_apps.getLayoutParams();
        params.bottomMargin = EmoticonsKeyboardUtils.dip2px(context, 20);
        gv_apps.setLayoutParams(params);
        gv_apps.setVerticalSpacing(EmoticonsKeyboardUtils.dip2px(context, 18));
        final ArrayList<AppBean> mAppBeanList = new ArrayList<>();
        mAppBeanList.add(new AppBean(R.mipmap.lde, "视频电话"));
        AppsAdapter adapter = new AppsAdapter(getContext(), mAppBeanList);
        gv_apps.setAdapter(adapter);
    }


    /**
     * 获取GridView对象
     */
    public GridView getQqGridView() {
        return gv_apps;
    }
}
