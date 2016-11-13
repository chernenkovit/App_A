package com.example.chernenkovit.a.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import static com.example.chernenkovit.a.data.ImageContract.AUTHORITY;
import static com.example.chernenkovit.a.data.ImageContract.IMAGES_CONTENT_URI;
import static com.example.chernenkovit.a.data.ImageContract.IMAGE_PATH;

/** Image Content Provider. */
public class ImageContentProvider extends ContentProvider {
    static final int URI_IMAGES = 1;
    static final int URI_IMAGES_ID = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, IMAGE_PATH, URI_IMAGES);
        uriMatcher.addURI(AUTHORITY, IMAGE_PATH + "/#", URI_IMAGES_ID);
    }

    ImageDBHelper dbHelper;
    SQLiteDatabase db;

    public boolean onCreate() {
        dbHelper = new ImageDBHelper(getContext(), ImageDBHelper.IMAGES_DATABASE_NAME, null, ImageDBHelper.IMAGES_DATABASE_VERSION);
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_IMAGES:
                break;
            case URI_IMAGES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = ImageContract.Images.IMAGES_ID_COLUMN + " = " + id;
                } else {
                    selection = selection + " AND " + ImageContract.Images.IMAGES_ID_COLUMN + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(ImageContract.Images.IMAGES_TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(),
                IMAGES_CONTENT_URI);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != URI_IMAGES)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(ImageContract.Images.IMAGES_TABLE_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(IMAGES_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_IMAGES:
                break;
            case URI_IMAGES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = ImageContract.Images.IMAGES_ID_COLUMN + " = " + id;
                } else {
                    selection = selection + " AND " + ImageContract.Images.IMAGES_ID_COLUMN + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(ImageContract.Images.IMAGES_TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_IMAGES:
                break;
            case URI_IMAGES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = ImageContract.Images.IMAGES_ID_COLUMN + " = " + id;
                } else {
                    selection = selection + " AND " + ImageContract.Images.IMAGES_ID_COLUMN + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(ImageContract.Images.IMAGES_TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_IMAGES:
                return ImageContract.Images.IMAGE_CONTENT_TYPE;
            case URI_IMAGES_ID:
                return ImageContract.Images.IMAGE_CONTENT_ITEM_TYPE;
        }
        return null;
    }
}
