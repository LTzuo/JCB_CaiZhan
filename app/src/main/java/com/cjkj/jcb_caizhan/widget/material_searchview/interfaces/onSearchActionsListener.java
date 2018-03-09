package com.cjkj.jcb_caizhan.widget.Material_Searchview.interfaces;

/**
 * Created by shahroz on 1/12/2016.
 */
public interface onSearchActionsListener {
    void onItemClicked(String item);
    void showProgress(boolean show);
    void listEmpty();
    void onScroll();
    void error(String localizedMessage);
}
