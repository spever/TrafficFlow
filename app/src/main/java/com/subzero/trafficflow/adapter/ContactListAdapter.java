package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.bean.Contact;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by hui on 2016/3/14.
 */
public class ContactListAdapter extends BasicAdapter<Contact> {
    public ContactListAdapter(Context context, ArrayList<Contact> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.listview_item_contact, null);
        }

        ViewHolder holder = ViewHolder.getHolder(convertView);

        if (list.size() > 0) {

            Contact contact = list.get(position);

            if (contact.getSEX() == null) {
                holder.id_sex.setVisibility(View.INVISIBLE);
            } else if (contact.getSEX().equals("1")) {
                holder.id_sex.setImageResource(R.mipmap.ico_boy);
            } else if (contact.getSEX().equals("2")) {
                holder.id_sex.setImageResource(R.mipmap.ico_girl);
            } else {
                holder.id_sex.setVisibility(View.INVISIBLE);
            }

            holder.id_tv_contact_name.setText(contact.getUSER_NAME());
            holder.id_tv_name_id.setText(MessageFormat.format(" (ID:{0})", contact.getUSER_ID()));
            holder.id_tv_mail.setText(contact.getMAIL());
            holder.id_tv_manager.setText(contact.getGLJGMC());
        }


        return convertView;
    }

    static class ViewHolder {
        public ImageView id_sex;
        public TextView id_tv_contact_name;
        public TextView id_tv_name_id;
        public TextView id_tv_mail;
        public TextView id_tv_manager;

        public ViewHolder(View convertView) {
            id_sex = (ImageView) convertView.findViewById(R.id.id_sex);
            id_tv_contact_name = (TextView) convertView.findViewById(R.id.id_tv_contact_name);
            id_tv_name_id = (TextView) convertView.findViewById(R.id.id_tv_name_id);
            id_tv_mail = (TextView) convertView.findViewById(R.id.id_tv_mail);
            id_tv_manager = (TextView) convertView.findViewById(R.id.id_tv_manager);
        }

        public static ViewHolder getHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            return holder;
        }
    }
}
