package com.example.almaz.test.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.almaz.test.R;

public class ClothesProvider extends ContentProvider {
    final String LOG_TAG = "myLogs";

    //Database
    static final String DB_NAME = "mydb";
    static final int DB_VERSION = 1;

    //Tables
    static final String CLOTHES_TABLE = "clothes";

    //Fields
    static final String CLOTHES_ID = "_id";
    static final String CLOTHES_NAME = "name";
    static final String CLOTHES_LAYOUT = "layout";
    static final String CLOTHES_TEMPERATURE_COEFFICIENT = "temperature_coefficient";
    static final String CLOTHES_STYLE_OFFICIAL = "style_official";
    static final String CLOTHES_STYLE_REGULAR = "style_regular";
    static final String CLOTHES_STYLE_SPORT = "style_sport";
    static final String CLOTHES_STYLE_EVENING = "style_evening";
    static final String CLOTHES_WINDPROOF = "windproof";
    static final String CLOTHES_RAIN_COVER = "rain_cover";
    static final String CLOTHES_PICTURE_RESOURCES = "picture_resources";
    static final String CLOTHES_FOR_MEN = "for_men";
    static final String CLOTHES_FOR_WOMEN = "for_women";

    //Database creating script
    static final String DB_CREATE = "create table " + CLOTHES_TABLE + "(" + CLOTHES_ID + " integer primary key autoincrement, " + CLOTHES_NAME + " text, " + CLOTHES_LAYOUT + " text, " + CLOTHES_TEMPERATURE_COEFFICIENT + " integer, " + CLOTHES_STYLE_OFFICIAL + " boolean, " + CLOTHES_STYLE_REGULAR + " boolean, " + CLOTHES_STYLE_SPORT + " boolean, " + CLOTHES_STYLE_EVENING + " boolean, " + CLOTHES_WINDPROOF + " boolean, "+ CLOTHES_RAIN_COVER + " boolean, " +CLOTHES_PICTURE_RESOURCES + " integer, "+ CLOTHES_FOR_MEN +" boolean, "+ CLOTHES_FOR_WOMEN + " boolean" + ");";

    // // Uri
    //authority
    final static String AUTHORITY = "almaz.example.com.test";

    //path
    final static String CLOTHES_PATH = "clothes";

    //united Uri
    public static final Uri CLOTHES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CLOTHES_PATH);

    //data types
    //set of rows
    static final String CLOTHES_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CLOTHES_PATH;

    //one row
    static final String CLOTHES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + CLOTHES_PATH;

    // // UriMatcher
    //united Uri
    static final int URI_CLOTHES = 1;

    //Uri with id
    static final int URI_CLOTHES_ID = 2;

    //describing and creating UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CLOTHES_PATH, URI_CLOTHES);
        uriMatcher.addURI(AUTHORITY, CLOTHES_PATH + "/#", URI_CLOTHES_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(CLOTHES_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                CLOTHES_CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CLOTHES:
                return CLOTHES_CONTENT_TYPE;
            case URI_CLOTHES_ID:
                return CLOTHES_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_CLOTHES)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(CLOTHES_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CLOTHES_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "delete, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CLOTHES:
                Log.d(LOG_TAG, "URI_CONTACTS");
                break;
            case URI_CLOTHES_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = CLOTHES_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CLOTHES_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(CLOTHES_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "update, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CLOTHES:
                Log.d(LOG_TAG, "URI_CONTACTS");

                break;
            case URI_CLOTHES_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = CLOTHES_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CLOTHES_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(CLOTHES_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            db.insert(CLOTHES_TABLE, null, addNewRow("t-shirt", "body", 5, false, true, true, false, false, false, R.drawable.t_shirt, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("cap", "head", 30, true, true, true, false, false, false, R.drawable.cap, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("coat", "bodyTop", 15, true, true, false, false, true, false, R.drawable.coat, false, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("jacket", "bodyTop", 10, false, true, true, false, false, false, R.drawable.jacket, true, false));
            db.insert(CLOTHES_TABLE, null, addNewRow("shirt", "body", 5, true, false, false, true, false, false, R.drawable.shirt, true, false));
            db.insert(CLOTHES_TABLE, null, addNewRow("trousers", "legs", 15, true, false, false, true, false, false, R.drawable.trousers, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("winter-coat", "bodyTop", 15, false, true, true, false, true, true, R.drawable.winter_coat, true, false));
            db.insert(CLOTHES_TABLE, null, addNewRow("zipper", "footwear", 5, true, true, false, false, true, true, R.drawable.zipper, false, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("shorts", "legs", 5, false, true, true, false, false, false, R.drawable.shorts, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("b-cap", "head", 0, false, true, true, false, false, false, R.drawable.b_cap, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("blouse", "body", 5, true, false, false, false, false, false, R.drawable.blouse, false, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("dress", "body", 10, true, false, false, true, false, false, R.drawable.dress, false, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("sunglasses", "accessory", 0, false, true, false, false, false, false, R.drawable.glasses, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("hoody", "bodyTop", 10, false, true, true, false, false, false, R.drawable.hoody, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("jacket_2", "bodyTop", 5, true, false, false, true, false, false, R.drawable.jacket_2, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("jacket_3", "bodyTop", 30, true, true, true, false, true, true, R.drawable.jacket_3, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("jeans", "legs", 10, false, true, false, false, false, false, R.drawable.jeans, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("keds", "footwear", 5, false, true, false, false, false, false, R.drawable.keds, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("man_shoe", "footwear", 10, true, false, false, true, false, false, R.drawable.man_shoe, true, false));
            db.insert(CLOTHES_TABLE, null, addNewRow("pullover", "body", 10, false, true, false, false, false, false, R.drawable.pullover, true, false));
            db.insert(CLOTHES_TABLE, null, addNewRow("scarf", "accessory", 30, false, true, true, true, false, false, R.drawable.scarf, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("skirt", "legs", 5, true, false, false, false, false, false, R.drawable.skirt, false, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("skirt_2", "legs", 5, true, false, false, true, false, false, R.drawable.skirt_2, false, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("sneakers", "footwear", 15, false, true, true, false, false, false, R.drawable.sneakers, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("tie", "accessory", 0, true, false, false, true, false, false, R.drawable.tie, true, false));
            db.insert(CLOTHES_TABLE, null, addNewRow("umbrella", "accessory", 0, true, true, true, true, true, true, R.drawable.umbrella, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("woman_shoe", "footwear", 5, true, true, false, true, false, false, R.drawable.woman_shoe, false, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("noHead", "head", 0, true, true, true, true, false, false, R.drawable.nothing, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("noBodyTop", "bodyTop", 0, true, true, true, true, false, false, R.drawable.nothing, true, true));
            db.insert(CLOTHES_TABLE, null, addNewRow("noAccessory", "accessory", 0, true, true, true, true, false, false, R.drawable.nothing, true, true));
        }

        public ContentValues addNewRow(String name, String layout, int tempC, boolean isOfficial, boolean isRegular, boolean isSport, boolean isEvening, boolean isWindproof, boolean isRainCover, int pictureResources, boolean forMen, boolean forWomen){
            ContentValues cv = new ContentValues();
            cv.put(CLOTHES_NAME, name);
            cv.put(CLOTHES_LAYOUT, layout);
            cv.put(CLOTHES_TEMPERATURE_COEFFICIENT, tempC);
            cv.put(CLOTHES_STYLE_OFFICIAL, isOfficial);
            cv.put(CLOTHES_STYLE_REGULAR, isRegular);
            cv.put(CLOTHES_STYLE_SPORT, isSport);
            cv.put(CLOTHES_STYLE_EVENING, isEvening);
            cv.put(CLOTHES_WINDPROOF, isWindproof);
            cv.put(CLOTHES_RAIN_COVER, isRainCover);
            cv.put(CLOTHES_PICTURE_RESOURCES, pictureResources);
            cv.put(CLOTHES_FOR_MEN, forMen);
            cv.put(CLOTHES_FOR_WOMEN, forWomen);
            return cv;
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
