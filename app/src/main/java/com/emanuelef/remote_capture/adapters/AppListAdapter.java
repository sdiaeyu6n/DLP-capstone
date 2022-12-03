package com.emanuelef.remote_capture.adapters;

import static com.emanuelef.remote_capture.activities.AppList.Checked_Lebles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.emanuelef.remote_capture.R;
import com.emanuelef.remote_capture.model.AppListItem;

import java.util.ArrayList;
import java.util.HashMap;

public class AppListAdapter extends BaseAdapter {
    ArrayList<AppListItem> items = new ArrayList<AppListItem>();
    Context context;
    HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked(int position){
        return items.get(position).Checked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        AppListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_list_item_2, parent,false);
        }

        TextView applebel = convertView.findViewById(R.id.applebel);
        TextView appuid = convertView.findViewById(R.id.appuid);
        CheckBox checkBox = convertView.findViewById(R.id.check_apps);
        checkBox.setChecked(items.get(position).Checked);
        applebel.setText(listItem.getAppLebel());
        appuid.setText(listItem.getAppUid());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.get(position).isChecked()){
                    Checked_Lebles.remove(listItem.getAppLebel());
                }
                else {
                    boolean newState = !items.get(position).isChecked();
                    items.get(position).Checked = newState;
                    Checked_Lebles.add(listItem.getAppLebel());
                }
            }
        });
        checkBox.setChecked(isChecked(position));


        return convertView;
    }

    public void addItem(AppListItem item){
        items.add(item);
    }
}
