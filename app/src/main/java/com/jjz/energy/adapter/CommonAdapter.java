package com.jjz.energy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context mContext, T xx, int item_home_list_in) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = (List<T>) xx;
        this.layoutId = item_home_list_in;
    }


    public void upDataList(List<T> datas)
    {
        if (null == datas)
            return;

        if (mDatas != datas) {
            mDatas.clear();
            mDatas.addAll(datas);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        if (null == mDatas)
            return 0;

        return mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T datas);

}
