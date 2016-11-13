package com.example.chernenkovit.a.ui.adapter;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chernenkovit.a.Const;
import com.example.chernenkovit.a.R;
import com.example.chernenkovit.a.Utils;

import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_LINK_COLUMN;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_STATUS_COLUMN;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_TIMESTAMP_COLUMN;

/**
 * Custom adapter for data presenting.
 */
public class ImageAdapter extends CursorAdapter {

    public ImageAdapter(Context context, Cursor c, ListView listView, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_col_image_history, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String link = cursor.getString(cursor.getColumnIndex(IMAGES_LINK_COLUMN));
        long timestamp = cursor.getLong(cursor.getColumnIndex(IMAGES_TIMESTAMP_COLUMN));
        int status = cursor.getInt(cursor.getColumnIndex(IMAGES_STATUS_COLUMN));

        viewHolder.tv_history_image.setText(link);
        viewHolder.tv_history_timestamp.setText(Utils.getFormattedTimestamp(timestamp));
        if (status == Const.STATUS_DOWNLOADED)
            viewHolder.imageHistoryItemList.setBackgroundColor(Color.GREEN);
        else if (status == Const.STATUS_ERROR)
            viewHolder.imageHistoryItemList.setBackgroundColor(Color.RED);
        else if (status == Const.STATUS_UNKNOWN)
            viewHolder.imageHistoryItemList.setBackgroundColor(Color.GRAY);
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.imageHistoryItemList.setBackground(null);
            } else {
                //noinspection deprecation
                viewHolder.imageHistoryItemList.setBackgroundDrawable(null);
            }
        }
    }

    private static class ViewHolder {
        final TextView tv_history_image;
        final TextView tv_history_timestamp;
        final View imageHistoryItemList;


        ViewHolder(View view) {
            tv_history_image = (TextView) view.findViewById(R.id.tv_history_image);
            tv_history_timestamp = (TextView) view.findViewById(R.id.tv_history_timestamp);
            imageHistoryItemList = view.findViewById(R.id.imageHistoryItemList);
        }
    }
}
