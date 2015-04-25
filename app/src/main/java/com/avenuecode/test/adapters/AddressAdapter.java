package com.avenuecode.test.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.avenuecode.test.R;
import com.avenuecode.test.models.google.Geocoding;

import java.util.List;

/**
 * Created by Marcel on 24/04/2015 22:36
 */
public class AddressAdapter extends ArrayAdapter<Geocoding> {

    private Context context;
    private final List<Geocoding> listGeocoding;

    public AddressAdapter(Context context, List<Geocoding> listGeocoding) {
        super(context, R.layout.activity_main_address_item, listGeocoding);
        this.context = context;
        this.listGeocoding = listGeocoding;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            viewHolder = new ViewHolder();

            view = inflater.inflate(R.layout.activity_main_address_item, parent, false);

            viewHolder.tvName = (TextView) view.findViewById(R.id.activity_main_address_item_tv_name);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //Retrieve the object in the position
        Geocoding geocoding = listGeocoding.get(position);

        //Set the name of the address
        viewHolder.tvName.setText(geocoding.getFormattedAddress());

        return view;
    }

    private static class ViewHolder {
        TextView tvName;
    }
}