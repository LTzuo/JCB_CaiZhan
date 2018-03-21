package com.cjkj.jcb_caizhan.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
/**
 * Fragment基类
 * Created by 1 on 2017/6/9.
 */
public abstract class BaseFragment extends Fragment {
	// 当前fragment是否可见
	private boolean isPrepared;

	protected RxBaseActivity mActivity;

	protected abstract int bindLayout();

	public Handler mHandler = new Handler(Looper.getMainLooper());

	View view;
	protected RxBaseActivity getHoldingActivity() {
		return mActivity;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (RxBaseActivity) activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initPrepare();
	}

	/**
	 * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
	 */
	private boolean isFirstResume = true;

	public synchronized void initPrepare() {
		if (isPrepared) {
			onFirstUserVisible();
		} else {
			isPrepared = true;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isFirstResume) {
			isFirstResume = false;
			return;
		}
		if (getUserVisibleHint()) {
			onUserVisible();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (getUserVisibleHint()) {
			onUserInvisible();
		}
	}

	// 第一次可见
	private boolean isFirstVisible = true;
	// 第一次不可见
	private boolean isFirstInvisible = true;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (isFirstVisible) {
				isFirstVisible = false;
				initPrepare();
			} else {
				onUserVisible();
			}
		} else {
			if (isFirstInvisible) {
				isFirstInvisible = false;
				onFirstUserInvisible();
			} else {
				onUserInvisible();
			}
		}
	}

	/**
	 * 加载数据
	 */
	protected void loadData() {
	}

	/**
	 * 初始化recyclerView
	 */
	protected void initRecyclerView() {
	}

	/**
	 * 初始化statusManagerLayout
	 */
	protected void initstatusManagerLayout() {
	}

	/**
	 * 设置数据显示
	 */
	protected void finishTask() {
	}


	/**
	 * 第一次fragment可见（进行初始化工作）
	 */
	public abstract void onFirstUserVisible();

	/**
	 * fragment可见（切换回来或者onResume）
	 */
	public abstract void onUserVisible();

	/**
	 * 第一次fragment不可见（不建议在此处理事件）
	 */
	public void onFirstUserInvisible() {

	}

	/**
	 * fragment不可见（切换掉或者onPause）
	 */
	public abstract void onUserInvisible();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view != null) {
			return view;
		}
		view = inflater.inflate(bindLayout(), container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ButterKnife.unbind(this);
	}
}
