package com.cjkj.jcb_caizhan.widget.exlistview;

import java.util.List;

/**
 * ExpandableListView group base bean
 * Created by 1 on 2018/3/5.
 */
public class ExBaseGroupBean {

    private List<ExBaseChildBean> childList;

    public List<ExBaseChildBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ExBaseChildBean> childList) {
        this.childList = childList;
    }
}
