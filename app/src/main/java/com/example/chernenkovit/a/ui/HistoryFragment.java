package com.example.chernenkovit.a.ui;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chernenkovit.a.Const;
import com.example.chernenkovit.a.R;
import com.example.chernenkovit.a.ui.adapter.ImageAdapter;

import static com.example.chernenkovit.a.Const.IMAGE_LOADER;
import static com.example.chernenkovit.a.Const.SORT_BY_DATE;
import static com.example.chernenkovit.a.Const.SORT_BY_STATUS;
import static com.example.chernenkovit.a.data.ImageContract.IMAGES_CONTENT_URI;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_LINK_COLUMN;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_STATUS_COLUMN;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_TIMESTAMP_COLUMN;

/** History tab fragment with loader implementation. */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView imageList;
    private ImageAdapter imageAdapter;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageList = (ListView) view.findViewById(R.id.imageList);

        //check if any database contains records
        Cursor cursor = getActivity().getContentResolver().query(
                IMAGES_CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor == null)
            Toast.makeText(getActivity().getApplicationContext(), "No images", Toast.LENGTH_SHORT).show();
        else {
            imageAdapter = new ImageAdapter(getActivity(), cursor, imageList, 0);
            imageList.setAdapter(imageAdapter);

            getActivity().getSupportLoaderManager().initLoader(IMAGE_LOADER, null, this);
        }

        //start App b by clicking on list item
        imageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                if (cursor != null) {
                                        Intent showImage = new Intent("com.example.chernenkovit.action.showImage");
                    String link = cursor.getString(cursor.getColumnIndex(IMAGES_LINK_COLUMN));
                    int status=cursor.getInt(cursor.getColumnIndex(IMAGES_STATUS_COLUMN));
                    showImage.putExtra("Link", link);
                    showImage.putExtra("Prev", Const.HISTORY_FRAGMENT);
                    showImage.putExtra("Id",id);
                    showImage.putExtra("Status",status);
                    startActivity(showImage);
                }
            }
        });
    }

    //loader implementation according to selected sorting order
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (MainActivity.sortOrder) {
            case SORT_BY_DATE:
                return new CursorLoader(getActivity(), IMAGES_CONTENT_URI,
                        null, null, null, IMAGES_TIMESTAMP_COLUMN + " DESC");
            case SORT_BY_STATUS:
                return new CursorLoader(getActivity(), IMAGES_CONTENT_URI,
                        null, null, null, IMAGES_STATUS_COLUMN + " ASC");
            default:
                return new CursorLoader(getActivity(), IMAGES_CONTENT_URI,
                        null, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        imageAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        imageAdapter.swapCursor(null);
    }
}
