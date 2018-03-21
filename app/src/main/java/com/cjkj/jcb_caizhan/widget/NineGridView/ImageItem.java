package com.cjkj.jcb_caizhan.widget.NineGridView;

import android.graphics.Rect;
import android.os.Parcel;

import com.previewlibrary.enitity.IThumbViewInfo;

/**
 * 图片预览实体类
 * Created by 1 on 2018/2/12.
 */
public class ImageItem implements IThumbViewInfo  {

    private String url;  //图片地址
    private Rect mBounds; // 记录坐标
    private boolean isLocal = true;//图片路径是否是本地路径

    public Rect getmBounds() {
        return mBounds;
    }

    public void setmBounds(Rect mBounds) {
        this.mBounds = mBounds;
    }

    public ImageItem(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {//将你的图片地址字段返回
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Rect getBounds() {//将你的图片显示坐标字段返回
        return mBounds;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, 0);
    }

    protected ImageItem(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };


    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
