package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.OddsEntity;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 竞彩赔率数据适配器
 * Created by 1 on 2018/3/20.
 */
public class OddsGridListAdapter extends BaseAdapter {

    private Context mContext;

    private List<OddsEntity> mDatas = new ArrayList<>();

    public OddsGridListAdapter(Context mContext, List<OddsEntity> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public String getOddsName(int position) {
        return mDatas.get(position).getOddsName();
    }

    public String getOddsValue(int position) {
        return mDatas.get(position).getOddsValue();
    }

    /**
     * 修改赔率
     *
     * @param odds
     */
    public void chengeOdds(String odds, int position) {
        mDatas.get(position).setOddsValue(odds);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_odds, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.oddsName.setText(mDatas.get(i).getOddsName());
        holder.oddsValue.setText(mDatas.get(i).getOddsValue());

        return v;
    }

    class ViewHolder {
        @Bind(R.id.oddsName)
        TextView oddsName;
        @Bind(R.id.oddsValue)
        TextView oddsValue;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
