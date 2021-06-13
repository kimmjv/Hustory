package com.kang.project.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kang.project.R;

import java.util.ArrayList;

public class AnswerAdapter extends BaseAdapter {
    private ArrayList<AnswerItem> listViewItemList = new ArrayList<AnswerItem>();
    public AnswerAdapter() {}

    @Override
    public int getCount() { return listViewItemList.size(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.answer_item, parent, false);
        }

        TextView answer_content = (TextView) convertView.findViewById(R.id.answer_content);
        TextView answer_name = (TextView) convertView.findViewById(R.id.answer_name);
        TextView answer_time = (TextView) convertView.findViewById(R.id.answer_time);

        AnswerItem listViewItem = listViewItemList.get(position);

        answer_content.setText(listViewItem.getContentStr());
        answer_name.setText(listViewItem.getNameStr());
        answer_time.setText(listViewItem.getTimeStr());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(String contentStr, String nameStr, String timeStr) {
        AnswerItem item = new AnswerItem();

        item.setContentStr(contentStr);
        item.setNameStr(nameStr);
        item.setTimeStr(timeStr);

        listViewItemList.add(item);
    }

    public void deleteItem(int position) { listViewItemList.remove(position); }
}