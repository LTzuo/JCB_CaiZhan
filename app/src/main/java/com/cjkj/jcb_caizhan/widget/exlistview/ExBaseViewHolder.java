package com.cjkj.jcb_caizhan.widget.exlistview;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * ExpandableListView base ViewHolder
 * Created by 1 on 2018/3/5.
 */
public class ExBaseViewHolder {

    private View parentView;

    public ExBaseViewHolder(View itemView) {
        this.parentView = itemView;
    }

    View getParentView() {
        return parentView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(@IdRes int id) {
        return (T) parentView.findViewById(id);
    }
}
