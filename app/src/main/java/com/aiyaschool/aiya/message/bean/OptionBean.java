package com.aiyaschool.aiya.message.bean;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class OptionBean {

    public static final int TYPE_PIC = 0X01;
    public static final int TYPE_AVATAR = 0x02;
//    public static final int TYPE_FILE = 0X02;
    public static final int TYPE_POSITION = 0X03;
    public static final int TYPE_CARD = 0X04;
    public static final int TYPE_DELAY = 0X05;
    public static final int TYPE_REMIND = 0X06;
    public static final int TYPE_BURN = 0X07;
    public static final int TYPE_SHARK = 0X08;
    public static final int TYPE_PRIVATE = 0X09;
    public static final int TYPE_THIRD = 0X0A;
    public static final int TYPE_BURN_OUT = 0X0B;
    public static final int TYPE_CARD_DISABLE = 0x0C;
    public static final int TYPE_VIDEO_CHAT = 0x0D;//音频视频聊天

    //消息操作
    public static final int TYPE_OPTION_MSG_FORWARD = 0x30;// 转发
    public static final int TYPE_OPTION_MSG_COLLECTION = 0x31;// 收藏
    public static final int TYPE_OPTION_MSG_DELETE = 0x32;// 删除
    public static final int TYPE_OPTION_MSG_WITHDRAW = 0x33;// 撤回



    public OptionBean() {
    }

    public OptionBean(int icon, String name) {
        this.name = name;
        this.icon = icon;
    }

    private int type;
    private int icon;
    private String name;
    private String intent;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    @Override
    public String toString() {
        return "OptionBean{" +
                "type=" + type +
                ", icon=" + icon +
                ", name='" + name + '\'' +
                ", intent='" + intent + '\'' +
                '}';
    }
}

