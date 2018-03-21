package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.OddsEntity;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NoScollGridView;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.IDialog;

/**
 * 竞彩详情比赛-列表适配器
 * Created by 1 on 2018/3/20.
 */
public class CompetitionDetailsSubListViewAdapter extends BaseAdapter implements OddsContract.IOddsView {

    private Context mContext;

    private List<OrderDetailListEntity> mDatas = new ArrayList<>();

    private OddsPresenter mOddsPresenter;

    private IDialog loaddialog;

    public CompetitionDetailsSubListViewAdapter(Context mContext) {
        mOddsPresenter = new OddsPresenter(this);
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
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
    public View getView(int position, View v, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_competition_qiu_recyclerlistview, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.item_index.setText(mDatas.get(position).getOrderGroup());
        if (!mDatas.get(position).getOrderGroup().equals("odds")) {
            holder.layout_odds.setVisibility(View.GONE);
            holder.item_index.setVisibility(View.VISIBLE);
        } else {
            holder.layout_odds.setVisibility(View.VISIBLE);
            holder.item_index.setVisibility(View.GONE);
            //赔率数据
            List<OddsEntity> oddsList = FastJsonUtil.getBeanList(mDatas.get(position).getOddsList(), OddsEntity.class);
            OddsGridListAdapter mOddsGridListAdapter = new OddsGridListAdapter(mContext, oddsList);
            holder.mAddsGridView.setAdapter(mOddsGridListAdapter);
            holder.mAddsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   DialogBuilder.editTextDialog(mContext).setTitle(mOddsGridListAdapter.getOddsName(i))
                           .setOnEditTextDialogListener(new DialogBuilder.OnEditTextDialogListener() {
                               @Override
                               public void onEditTextCreated(EditText editText) {
                                   editText.setHint("请输入赔率");
                                   //  editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                   editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                                   editText.addTextChangedListener(new TextWatcher() {
                                       private int selectionStart;
                                       private int selectionEnd;
                                       private CharSequence temp;

                                       @Override
                                       public void onTextChanged(CharSequence s, int start, int before, int count) {
                                           // TODO Auto-generated method stub
                                           temp = s;
                                       }

                                       @Override
                                       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                           // TODO Auto-generated method stub

                                       }

                                       @Override
                                       public void afterTextChanged(Editable s) {
                                           // TODO Auto-generated method stub
                                           selectionStart = editText.getSelectionStart();
                                           selectionEnd = editText.getSelectionEnd();

                                           if (!isOnlyPointNumber(editText.getText().toString())) {
                                               //删除多余输入的字（不会显示出来）
                                               s.delete(selectionStart - 1, selectionEnd);
                                               editText.setText(s);
                                           }

                                       }
                                   });
                               }

                               @Override
                               public boolean onEditTextSelected(EditText editText, String text) {
                                   if (editText.getText().toString().isEmpty()) {
                                       ToastUtil.ShortToast("赔率不能为空");
                                       return true;
                                   }
                                   mOddsGridListAdapter.chengeOdds(text, i);
                                   hideKeyboard(editText);
                                   putOrderOdds(oddsList.get(i).getOddsId(), oddsList.get(i).getOddsFiles(), text);
                                   return false;
                               }
                           }).show();
                }
            }
            );
        }
        return v;
    }

    //强制隐藏键盘

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //保留三位小数
    public static boolean isOnlyPointNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,3}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public void setInfo(List<OrderDetailListEntity> datas) {
        mDatas.clear();
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void Ondestroy(){
        mOddsPresenter.unSubscribe();
    }

    //修改赔率
    private void putOrderOdds(String oddsId, String oddsFiles, String text) {
        if (loaddialog == null) {
            loaddialog = DialogBuilder.loadingDialog(mContext).show();
        }
        mOddsPresenter.putOrderOdds(SPUtil.get(mContext, Constants.key_uSessionId, "").toString(),
                "0", oddsId, oddsFiles, text);
    }

    @Override
    public void ChengeOddsSuccessful(String msg) {
        if (loaddialog != null) {
            loaddialog.dismiss();
        }
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void ChengeFaild(String msg) {
        if (loaddialog != null) {
            loaddialog.dismiss();
        }
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void SureOddsSuccessful(String msg) {
       //无返回
    }

    @Override
    public void SureFaild(String msg) {
        //无返回
    }

    @Override
    public void putOrderPicsSuccessful(String msg) {
        //无返回
    }

    @Override
    public void putOrderPicsFaild(String msg) {
        //无返回
    }

    class ViewHolder {
        @Bind(R.id.item_index)
        TextView item_index;
        @Bind(R.id.layout_odds)
        LinearLayout layout_odds;
        @Bind(R.id.mAddsGridView)
        NoScollGridView mAddsGridView;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

//    @Override
//    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        bindContext(parent.getContext());
//        return new ItemViewHolder(
//                LayoutInflater.from(getContext()).inflate(R.layout.item_competition_qiu_recyclerlistview, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(ClickableViewHolder holder, int position) {
//        if (holder instanceof ItemViewHolder) {
//            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
//            itemViewHolder.item_index.setText(mDatas.get(position).getOrderGroup());
//            if (!mDatas.get(position).getOrderGroup().equals("odds")) {
//                itemViewHolder.layout_odds.setVisibility(View.GONE);
//                itemViewHolder.item_index.setVisibility(View.VISIBLE);
//            } else {
//                itemViewHolder.layout_odds.setVisibility(View.VISIBLE);
//                itemViewHolder.item_index.setVisibility(View.GONE);
//
//                //赔率数据
//                List<OddsEntity> oddsList = FastJsonUtil.getBeanList(mDatas.get(position).getOddsList(), OddsEntity.class);
//                OddsGridListAdapter mOddsGridListAdapter = new OddsGridListAdapter(getContext(), oddsList);
//                itemViewHolder.mAddsGridView.setAdapter(mOddsGridListAdapter);
//            }
//        }
//        super.onBindViewHolder(holder, position);
//    }

//    @Override
//    public int getItemCount() {
//        return mDatas == null ? 0 : mDatas.size();
//    }

//    private class ItemViewHolder extends ClickableViewHolder {
//
//        TextView item_index;
//        LinearLayout layout_odds;
//        NoScollGridView mAddsGridView;
//
//        public ItemViewHolder(View itemView) {
//            super(itemView);
//            item_index = $(R.id.item_index);
//            layout_odds = $(R.id.layout_odds);
//            mAddsGridView = $(R.id.mAddsGridView);
//        }
//    }


}
