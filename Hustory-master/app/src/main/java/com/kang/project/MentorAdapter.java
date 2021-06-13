package com.kang.project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MentorAdapter extends BaseAdapter {
        private ArrayList<MentorItem> listViewItemList = new ArrayList<MentorItem>();
        public MentorAdapter() {}

        @Override
        public int getCount() { return listViewItemList.size(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.mentor_item, parent, false);
            }

            TextView name_student = (TextView) convertView.findViewById(R.id.name_student);
            TextView version_student = (TextView) convertView.findViewById(R.id.version_student);
            CheckBox checkBox1 = (CheckBox) convertView.findViewById(R.id.checkBox1);

            MentorItem listViewItem = listViewItemList.get(position);

            name_student.setText(listViewItem.getNameStr());
            version_student.setText(listViewItem.getVersionStr());
            checkBox1.setChecked(listViewItem.getCheckBool());

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

        public void addItem(String nameStr, String versionStr, Boolean checkBool) {
            MentorItem item = new MentorItem();

            item.setNameStr(nameStr);
            item.setVersionStr(versionStr);
            item.setCheckBool(checkBool);

            listViewItemList.add(item);
        }

        public void deleteItem(int position) { listViewItemList.remove(position); }

        public void clear() {
        listViewItemList.clear();
    }
}