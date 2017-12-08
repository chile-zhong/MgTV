package com.hunan.mgtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hunan.mgtv.bean.Question;

import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/12/7.
 */

public class QuestionSingleAdapter extends ArrayAdapter<Question> {

    private int resourceId;
    private int index = -1;

    public QuestionSingleAdapter(Context context, int textViewResourceId,
                                 List<Question> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //重写适配器的getItem()方法
        Question question = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.number = (TextView) view.findViewById(R.id.number);
            viewHolder.content = (TextView) view.findViewById(R.id.content);
            viewHolder.checkBox = (RadioButton) view.findViewById(R.id.checkBox);
            view.setTag(viewHolder);
        } else { //若有缓存布局，则直接用缓存（利用的是缓存的布局，利用的不是缓存布局中的数据）
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    index = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (index == position) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }
        viewHolder.number.setText(question.getXh()+"");
        viewHolder.content.setText(question.getMs());
        notifyDataSetChanged();
        return view;
    }

    class ViewHolder {
        RadioButton checkBox;
        TextView number;
        TextView content;
    }

}
