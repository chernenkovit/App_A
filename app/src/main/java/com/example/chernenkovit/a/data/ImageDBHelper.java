package com.example.chernenkovit.a.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_ID_COLUMN;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_LINK_COLUMN;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_STATUS_COLUMN;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_TABLE_NAME;
import static com.example.chernenkovit.a.data.ImageContract.Images.IMAGES_TIMESTAMP_COLUMN;

/** Helper class for database creating. */
class ImageDBHelper extends SQLiteOpenHelper {
    static final String IMAGES_DATABASE_NAME = "IMAGES_DATABASE";
    static final int IMAGES_DATABASE_VERSION = 1;

        public ImageDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, IMAGES_DATABASE_NAME, factory, IMAGES_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + IMAGES_TABLE_NAME + " (" +
                IMAGES_ID_COLUMN + " integer primary key autoincrement, " +
                IMAGES_LINK_COLUMN + " text, " +
                IMAGES_STATUS_COLUMN + " integer, " +
                IMAGES_TIMESTAMP_COLUMN + " integer" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
