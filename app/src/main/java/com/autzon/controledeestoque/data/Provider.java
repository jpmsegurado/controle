package com.autzon.controledeestoque.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class Provider extends ContentProvider {

    public Provider() {
    }

    public static final String PROVIDER_NAME = "com.autzon.controle";
    public static final String URL = "content://"+PROVIDER_NAME+"/";
    public static final Uri CONTENT_URL = Uri.parse(URL);

    public static final int transacao = 1;

    private SQLiteDatabase writeDb,readDb;

    public static final UriMatcher matcher;
    static{
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PROVIDER_NAME,Contract.transacao,transacao);
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rows = 0;

        switch(matcher.match(uri)){
            case transacao:
                rows = writeDb.delete(Contract.transacao,selection,selectionArgs);
                break;
        }

        return rows;

    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = null;
        Log.d("uri matcher result", "" + matcher.match(uri));
        long id = 0;

        switch(matcher.match(uri)){
            case transacao:
                id = writeDb.insert(Contract.transacao,null,values);
                if(id > 0){
                    _uri = ContentUris.withAppendedId(CONTENT_URL, id);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
        }

        return _uri;
    }

    @Override
    public boolean onCreate() {
        DbHelper helper = new DbHelper(getContext());
        writeDb = helper.getWritableDatabase();
        readDb = helper.getReadableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        switch(matcher.match(uri)){
            case transacao:
                if(selection == null && sortOrder == null){
                    cursor = readDb.rawQuery("SELECT * FROM "+Contract.transacao,selectionArgs);
                }else if(selection == null && sortOrder != null){
                    cursor = readDb.rawQuery("SELECT * FROM "+Contract.transacao+" ORDER BY "+sortOrder,selectionArgs);
                }else if(selection != null && sortOrder == null){
                    cursor = readDb.rawQuery("SELECT * FROM "+Contract.transacao+" "+selection,selectionArgs);
                }else{
                    cursor = readDb.rawQuery("SELECT * FROM "+Contract.transacao+" WHERE "+selection+" ORDER BY "+sortOrder,selectionArgs);
                }
                break;
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rows = 0;

        switch(matcher.match(uri)){
            case transacao:
                rows = writeDb.update(Contract.transacao,values,selection,selectionArgs);
                break;
        }

        return rows;
    }
}
