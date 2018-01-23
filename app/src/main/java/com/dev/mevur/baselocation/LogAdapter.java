package com.dev.mevur.baselocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mevur on 01/22/18.
 */

public class LogAdapter extends BaseAdapter {

    private List<LogMessage> data;
    private LayoutInflater inflater;
    private Context context;

    public LogAdapter(Context context, List<LogMessage> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        } else {
            convertView = inflater.inflate(R.layout.list_item, null);
            TextView time = convertView.findViewById(R.id.log_time);
            TextView content = convertView.findViewById(R.id.log_content);
            LogMessage message = data.get(position);
            time.setText(message.getTime());
            content.setText(message.getContent());
            return convertView;
        }
    }


}
