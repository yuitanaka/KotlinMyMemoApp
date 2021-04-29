package com.example.mymemoapp

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.BaseAdapter
import android.widget.SimpleCursorAdapter
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>{

    private lateinit var adapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: ドットインストールでやってることが非推奨になってる！！！！！
        // Loaderの初期化　引数１：Loaderを指定するid　引数２：起動オプション　引数３：Loaderをどこで実装するか
        // getLoaderManager().initLoader(0, null, this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_settings){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection: Array<String> = arrayOf(
            BaseColumns._ID,
            MemoContract.Memos.COL_TITLE,
            MemoContract.Memos.COL_UPDATED
        )
        // Loaderを作った時に実行されるクエリを返す
        return CursorLoader(
            this,
            MemoContentProvider.CONTENT_URI,
            projection,
            null,
            null,
            "${MemoContract.Memos.COL_UPDATED} DESC"
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        // ContentProviderからデータが返ってきたときに実行される
        // 渡ってきたデータでadapterを更新する
        adapter.swapCursor(data)

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        // 何らかの理由でLoaderがリセットされた時の処理
        adapter.swapCursor(null)
    }
}
