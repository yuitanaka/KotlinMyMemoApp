package com.example.mymemoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class MemoOpenHelper(c: Context?): SQLiteOpenHelper(c, DB_NAME, null, DB_VERSION) {
    companion object{
        const val DB_NAME = "myapp.db"
        const val DB_VERSION = 1

        const val CREATE_TABLE =
                "create tables ${MemoContract.Memos.TABLE_NAME} (" +
                "${BaseColumns._ID} integer primary key autoincrement, " +
                "${MemoContract.Memos.COL_TITLE} text, " +
                "${MemoContract.Memos.COL_BODY} text, " +
                "${MemoContract.Memos.COL_CREATE} datetime default current_timestamp, " +
                "${MemoContract.Memos.COL_UPDATED} datetime default current_timestamp)"
        const val INIT_TABLE =
                "insert into ${MemoContract.Memos.TABLE_NAME}(${MemoContract.Memos.COL_TITLE}, ${MemoContract.Memos.COL_BODY}) values " +
                        "('t1', 'b1'), " +
                        "('t2', 'b2'), " +
                        "('t3', 'b3') "
        const val DROP_TABLE = "drop table if exists ${MemoContract.Memos.TABLE_NAME}"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
        db?.execSQL(INIT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}