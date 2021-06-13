package com.kang.project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PreviousAdapter extends BaseAdapter {
    private ArrayList<PreviousItem> listViewItemList = new ArrayList<PreviousItem>();
    public PreviousAdapter() {}

    @Override
    public int getCount() { return listViewItemList.size(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.previous_item, parent, false);
        }

        ImageView icon_professor = (ImageView) convertView.findViewById(R.id.icon_professor);
        TextView text_professor = (TextView) convertView.findViewById(R.id.text_professor);
        TextView text_summary = (TextView) convertView.findViewById(R.id.text_summary);
        TextView reservation_date = (TextView) convertView.findViewById(R.id.reservation_date);
        TextView reservation_way = (TextView) convertView.findViewById(R.id.reservation_way);
        TextView reservation_place = (TextView) convertView.findViewById(R.id.reservation_place);
        TextView reservation_state = (TextView) convertView.findViewById(R.id.reservation_state);

        PreviousItem listViewItem = listViewItemList.get(position);

        icon_professor.setImageDrawable(listViewItem.getIconDrawable());
        text_professor.setText(listViewItem.getProfessorStr());
        text_summary.setText(listViewItem.getSummaryStr());
        reservation_date.setText(listViewItem.getDateStr());
        reservation_way.setText(listViewItem.getWayStr());
        reservation_place.setText(listViewItem.getPlaceStr());
        reservation_state.setText(listViewItem.getAllowStr());

        if (String.valueOf(listViewItem.getAllowStr()).equals("상담 대기")) {
            reservation_state.setTextColor(Color.parseColor("#2F5596"));
            reservation_state.setBackgroundResource(R.drawable.round_background);
        }


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

    public void addItem(Drawable iconDrawable, String ProfessorStr, String SummaryStr, String DateStr, String WayStr, String PlaceStr, String allowStr) {
        PreviousItem item = new PreviousItem();

        item.setIconDrawable(iconDrawable);
        item.setProfessorStr(ProfessorStr);
        item.setSummaryStr(SummaryStr);
        item.setDateStr(DateStr);
        item.setWayStr(WayStr);
        item.setPlaceStr(PlaceStr);
        item.setAllowStr(allowStr);

        listViewItemList.add(item);
    }

    public void deleteItem(int position) { listViewItemList.remove(position); }
}