package com.cjkj.jcb_caizhan.widget.NineGridView;

import java.util.ArrayList;

/**
 * RecyclerView嵌套ExpandableListView中，操作
 * RecyclerView列表进行增与删时，对象
 * Created by 1 on 2018/3/13.
 */
public class ChildImages {

    private ArrayList<ImageItem> Imgs = new ArrayList<>();

    private PhotoAdapter adapter;

    public PhotoAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PhotoAdapter adapter) {
        this.adapter = adapter;
    }

    public ChildImages(){}

    public ChildImages(ArrayList<ImageItem> imgs,PhotoAdapter adapter){
        this.Imgs = imgs;
        this.adapter = adapter;
    }

    public ArrayList<ImageItem> getImgs() {
        return Imgs;
    }

    public void setImgs(ArrayList<ImageItem> imgs) {
        Imgs = imgs;
    }
}
