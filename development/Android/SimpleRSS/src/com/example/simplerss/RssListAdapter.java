package com.example.simplerss;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RssListAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;

    public RssListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView mTitle = null;
        TextView mDescr = null;
        TextView mDate = null;
        ImageView mImage = null;
    }

    // 1行ごとのビューを生成する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_row, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) view.findViewById(R.id.item_title);
            holder.mDescr = (TextView) view.findViewById(R.id.item_descr);
            holder.mDate = (TextView) view.findViewById(R.id.item_time_stamp);
            holder.mImage = (ImageView) view.findViewById(R.id.item_thumb);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 現在参照しているリストの位置からItemを取得する
        Item item = this.getItem(position);
        if (item != null) {
            // Itemから必要なデータを取り出し、それぞれTextViewにセットする
            String title = "";
            if (null != item.getTitle()) {
                title = item.getTitle().toString();
            }
            holder.mTitle.setText(title);
            String descr = "";
            if (null != item.getDescription()) {
                descr = item.getDescription().toString();
            }
            holder.mDescr.setText(Html.fromHtml(descr));
            String date = "";
            if (null != item.getDate()) {
                date = item.getDate().toString();
            }
            holder.mDate.setText(date);

            holder.mImage.setVisibility(item.hasImg() ? View.VISIBLE : View.GONE);
        }
        return view;
    }
}
