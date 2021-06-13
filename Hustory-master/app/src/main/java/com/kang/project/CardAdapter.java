package com.kang.project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends BaseAdapter {
    private ArrayList<CardItem> listViewItemList = new ArrayList<CardItem>();
    public CardAdapter() {}

    @Override
    public int getCount() { return listViewItemList.size(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.card_item, parent, false);
        }

        ImageView icon_student = (ImageView) convertView.findViewById(R.id.icon_student);
        TextView name_student = (TextView) convertView.findViewById(R.id.name_student);
        TextView version_student = (TextView) convertView.findViewById(R.id.version_student);

        CardItem listViewItem = listViewItemList.get(position);

        icon_student.setImageDrawable(listViewItem.getIconDrawable());
        name_student.setText(listViewItem.getNameStr());
        version_student.setText(listViewItem.getVersionStr());

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

    public void addItem(Drawable iconDrawable, String nameStr, String versionStr) {
        CardItem item = new CardItem();

        item.setIconDrawable(iconDrawable);
        item.setNameStr(nameStr);
        item.setVersionStr(versionStr);

        listViewItemList.add(item);
    }

    public void deleteItem(int position) { listViewItemList.remove(position); }
}