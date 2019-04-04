package kr.ac.cnu.cse.myaddressbook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class AddressProvider extends ContentProvider {

    static final String PROVIDER_NAME = "kr.ac.cnu.cse.myaddressbook.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/friends";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String ID = "id";
    static final String EMAIL = "email";
    static final String NAME = "name";
    static final String POSTALADDR = "postaladdr";
    static final String PHONE = "phone";

    static final int FRIENDS = 1;
    static final int FRIENDS_ID = 2;

    DBOpenHelper dbOpenHelper;

    private static HashMap<String, String> AddressMap;
    // maps from the user column names to the database column names
    // set by SQLiteQueryBuilder.setProjectionMap()

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "friends", FRIENDS);
        uriMatcher.addURI(PROVIDER_NAME, "friends/#", FRIENDS_ID);
    }

    private SQLiteDatabase database;

    static final String DATABASE_NAME =
            "AddressBookDB";
    static final String TABLE_NAME = "AddressTable";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " email TEXT NOT NULL, " + " name TEXT NOT NULL, " +
                    " postaladdr TEXT NOT NULL, " + " phone TEXT NOT NULL);";

    public boolean onCreate() {
        Context context = getContext();
        dbOpenHelper = new AddressProvider.DBOpenHelper(context);
        database = dbOpenHelper.getWritableDatabase();
        if(database == null)
            return false;
        else
            return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case FRIENDS:
                queryBuilder.setProjectionMap(AddressMap);
                break;
            case FRIENDS_ID:
                queryBuilder.appendWhere(ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = NAME;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        long row = database.insert(TABLE_NAME, "", values);
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri); // android.database.SQLException
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case FRIENDS:
                count = database.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case FRIENDS_ID:
                count = database.update(TABLE_NAME, values, ID + " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case FRIENDS:
                // delete all the records of the table
                count = database.delete(TABLE_NAME, selection, selectionArgs); break;
            case FRIENDS_ID:
                String id = uri.getLastPathSegment(); //gets the id
                count = database.delete( TABLE_NAME, ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs); break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case FRIENDS:          return "vnd.android.cursor.dir/vnd.cnu.cse.friends";
            case FRIENDS_ID:    return "vnd.android.cursor.item/vnd.cnu.cse.friends";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    } //https://developer.android.com/guide/topics/providers/content-provider-basics#MIMETypeReference


    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBOpenHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }

}
