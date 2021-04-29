package com.example.mymemoapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class MemoContentProvider : ContentProvider() {

    companion object{
        const val AUTHORITY = "com.example.mymemoapp.MemoContentProvider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/${MemoContract.Memos.TABLE_NAME}")

        //UriMatcher
        private val MEMOS = 1
        private val MEMO_ITEM = 2
        private val uriMatcher: UriMatcher

        init{
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            // テーブル全体にアクセスがあった場合はMEMOSを返す
            uriMatcher.addURI(AUTHORITY, MemoContract.Memos.TABLE_NAME, MEMOS)
            // 特定のカラムにアクセスがあった場合はMEMO_ITEMを返す
            uriMatcher.addURI(AUTHORITY, "${MemoContract.Memos.TABLE_NAME}/#", MEMO_ITEM)
        }
    }
    private lateinit var memoOpenHelper: MemoOpenHelper

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        memoOpenHelper = MemoOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        when{
            uriMatcher.match(uri) != MEMOS || uriMatcher.match(uri) != MEMO_ITEM -> throw IllegalArgumentException("Invalid Uri: $uri")
        }
        val db: SQLiteDatabase = memoOpenHelper.readableDatabase
        val c: Cursor = db.query(
            MemoContract.Memos.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        return c
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
