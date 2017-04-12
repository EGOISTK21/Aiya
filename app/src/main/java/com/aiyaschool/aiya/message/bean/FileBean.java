package com.aiyaschool.aiya.message.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.ImageView;

import com.aiyaschool.aiya.R;

import java.text.DecimalFormat;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class FileBean implements Parcelable {

    public enum FileType {
        ROOT, PARENT, DIR, FILE
    }

    private int id;
    private String dir;     //文件夹目录
    private String path;    //全路径
    private String size;    //文件大小
    private FileType type;  //文件类型
    private String time;    //时间
    private String firstPath;//第一个文件的路径
    private String name;     //文件名称
    private String thumbPath; //缩略图路径
    private int count;       //文件数量

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        if (dir.contains("/")) {
            int lastIndexOf = this.dir.lastIndexOf("/");
            this.name = this.dir.substring(lastIndexOf + 1);
        } else {
            this.name = dir;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstPath() {
        return firstPath;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    //    方便数组转换成list，用对象toString方法
    @Override
    public String toString() {
        return name;
    }

    public String toStrings() {
        return "FileBean{" +
                "id=" + id +
                ", dir='" + dir + '\'' +
                ", path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", firstPath='" + firstPath + '\'' +
                ", name='" + name + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                ", count=" + count +
                '}';
    }

    public static String changeBite(long fileSize) {
        String str = HumanReadableFileSize(fileSize);
        return str;
    }

    private static String HumanReadableFileSize(double size) {
        String[] units = new String[] { "B", "KB", "MB", "GB", "TB", "PB" };
        double mod = 1024.0;
        if (size < mod) {
            return "<1KB";
        }
        int i = 0;
        for (i = 0; size >= mod; i++) {
            size /= mod;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(size) + units[i];
    }

    public static String formatTime(Context context, long when) {
        int flags = DateUtils.FORMAT_ABBREV_ALL /*| DateUtils.FORMAT_24HOUR */| DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE;
        return DateUtils.formatDateTime(context, when, flags);
    }

    /**
     * 根据文件对应不同图标
     *
     * @param fileName
     * @return
     */
    public static void setFileIcon(ImageView view, String fileName, String filePath) {
        // 取得扩展名
        String end = fileName.substring(fileName.lastIndexOf(".") + 1,
                fileName.length());
        if ((end == null)) {
            view.setImageResource(R.mipmap.file_icon_unknow);
            return;
        } else {
            end = end.toLowerCase();
        }
			/* 依扩展名的类型决定MimeType */
        if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            if (!TextUtils.isEmpty(filePath)){
//                ImageUtil.loadViewLocal(IMApp.getContext(), filePath, view, R.mipmap.file_icon_pic);
            }else{
                view.setImageResource(R.mipmap.file_icon_pic);
            }
        } else if (end.equals("apk")) {
            view.setImageResource(R.mipmap.file_icon_apk);
        } else if (end.equals("exe")) {
            view.setImageResource(R.mipmap.file_icon_exe);
        } else if (end.startsWith("xls")) {
            view.setImageResource(R.mipmap.file_icon_excel);
        } else if (end.startsWith("doc") || end.equals("rtf")) {
            view.setImageResource(R.mipmap.file_icon_doc);
        } else if (end.equals("rar") || end.equals("zip") || end.equals("gz") || end.equals("tar")) {
            view.setImageResource(R.mipmap.file_icon_rar);
        } else if (end.equals("txt") || end.equals("log")) {
            view.setImageResource(R.mipmap.file_icon_txt);
        } else if (end.equals("mpg") || end.equals("mp4") || end.equals("mpeg") || end.equals("3gp")
                || end.equals("3gpp") || end.equals("rm") || end.equals("mwv") || end.equals("avi")
                || end.equals("mov")) {
            view.setImageResource(R.mipmap.file_icon_asf);
        } else if (end.equals("mp3") || end.equals("wav") || end.equals("ra") || end.equals("ram") || end.equals("amr")) {
            view.setImageResource(R.mipmap.file_icon_aiff);
        } else if (end.equals("ppt")) {
            view.setImageResource(R.mipmap.file_icon_bat);
        } else if (end.equals("bat")) {
            view.setImageResource(R.mipmap.file_icon_bat);
        } else if (end.equals("dll")) {
            view.setImageResource(R.mipmap.file_icon_dll);
        } else if (end.equals("html")) {
            view.setImageResource(R.mipmap.file_icon_html);
        } else if (end.equals("pdf")) {
            view.setImageResource(R.mipmap.file_icon_pdf);
        } else {
            view.setImageResource(R.mipmap.file_icon_unknow);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.dir);
        dest.writeString(this.path);
        dest.writeString(this.size);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.time);
        dest.writeString(this.firstPath);
        dest.writeString(this.name);
        dest.writeString(this.thumbPath);
        dest.writeInt(this.count);
    }

    public FileBean() {
    }

    protected FileBean(Parcel in) {
        this.id = in.readInt();
        this.dir = in.readString();
        this.path = in.readString();
        this.size = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : FileType.values()[tmpType];
        this.time = in.readString();
        this.firstPath = in.readString();
        this.name = in.readString();
        this.thumbPath = in.readString();
        this.count = in.readInt();
    }

    public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
        public FileBean createFromParcel(Parcel source) {
            return new FileBean(source);
        }

        public FileBean[] newArray(int size) {
            return new FileBean[size];
        }
    };
}

