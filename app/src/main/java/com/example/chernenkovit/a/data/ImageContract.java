package com.example.chernenkovit.a.data;


import android.net.Uri;
import android.provider.BaseColumns;

/** Contract for using Content Provider. */
public class ImageContract {
    static final String AUTHORITY = "com.example.chernenkovit.providers.media";
    static final String IMAGE_PATH = "images";
    public static final Uri IMAGES_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + IMAGE_PATH);

    public static final class Images implements BaseColumns {
        private Images() {
        }

        static final String IMAGE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
                + AUTHORITY + "." + IMAGE_PATH;
        ;
        static final String IMAGE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
                + AUTHORITY + "." + IMAGE_PATH;
        ;
        public static final String IMAGES_TABLE_NAME = "images";
        public static final String IMAGES_ID_COLUMN = "_id";
        public static final String IMAGES_LINK_COLUMN = "link";
        public static final String IMAGES_STATUS_COLUMN = "status";
        public static final String IMAGES_TIMESTAMP_COLUMN = "timestamp";

        public static final String[] DEFAULT_PROJECTION = new String[]{
                Images.IMAGES_ID_COLUMN,
                Images.IMAGES_LINK_COLUMN,
                Images.IMAGES_STATUS_COLUMN,
                Images.IMAGES_TIMESTAMP_COLUMN
        };
    }
}

