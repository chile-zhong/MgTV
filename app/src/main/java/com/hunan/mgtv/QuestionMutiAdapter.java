package com.hunan.mgtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hunan.mgtv.bean.AnswersItem;

import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/12/7.
 */

public class QuestionMutiAdapter extends ArrayAdapter<AnswersItem> {

    private int resourceId;

    public QuestionMutiAdapter(Context context, int textViewResourceId,
                               List<AnswersItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //重写适配器的getItem()方法
        AnswersItem question = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.number = (TextView) view.findViewById(R.id.number);
            viewHolder.content = (TextView) view.findViewById(R.id.content);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            view.setTag(viewHolder);
        } else { //若有缓存布局，则直接用缓存（利用的是缓存的布局，利用的不是缓存布局中的数据）
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.checkBox.setChecked(question.isChecked());
        viewHolder.number.setText(position + 1 + "");
        viewHolder.content.setText(question.getTxt());
        notifyDataSetChanged();
        return view;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView number;
        TextView content;
    }

}
