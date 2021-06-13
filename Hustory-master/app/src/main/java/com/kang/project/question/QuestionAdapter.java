package com.kang.project.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kang.project.R;

import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {
    private ArrayList<QuestionItem> listViewItemList = new ArrayList<QuestionItem>();
    public QuestionAdapter() {}

    @Override
    public int getCount() { return listViewItemList.size(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.question_item, parent, false);
        }

        TextView question_content = (TextView) convertView.findViewById(R.id.question_content);
        TextView question_name = (TextView) convertView.findViewById(R.id.question_name);
        TextView question_time = (TextView) convertView.findViewById(R.id.question_time);
        TextView answer_count = (TextView) convertView.findViewById(R.id.answer_count);

        QuestionItem listViewItem = listViewItemList.get(position);

        question_name.setText(listViewItem.getQ_title());
        question_content.setText(listViewItem.getQ_title());
        question_time.setText(listViewItem.getQ_time());
        answer_count.setText(String.valueOf(listViewItem.getQ_count()));

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

    public void addItem(String questionStr, String nameStr, String timeStr, String countStr) {
        QuestionItem item = new QuestionItem(nameStr, questionStr, timeStr, countStr);

        item.setQ_title(nameStr);
        item.setQ_content(questionStr);
        item.setQ_time(timeStr);
        item.setQ_count(countStr);

        listViewItemList.add(item);
    }

    public void addItem(String id, String q_Num, String q_title, String q_content, String q_date, String q_time, String q_count, String q_like, String q_dislike) {
        QuestionItem item = new QuestionItem(id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);

        item.setQ_title(q_title);
        item.setQ_content(q_content);
        item.setQ_time(q_date);
        item.setQ_count(q_count);

        listViewItemList.add(item);
    }

    public void addItemDESC(int idx, String id, String q_Num, String q_title, String q_content, String q_date, String q_time, String q_count, String q_like, String q_dislike) {
        QuestionItem item = new QuestionItem(id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);

        item.setQ_title(q_title);
        item.setQ_content(q_content);
        item.setQ_time(q_date);
        item.setQ_count(q_count);

        listViewItemList.add(idx, item);
    }

    public void deleteItem(int position) { listViewItemList.remove(position); }

    public void clear() {
        listViewItemList.clear();
    }
}