package com.example.simplerss;

import java.util.ArrayList;

import com.google.ads.*;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity {
//    private static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
    private static final String RSS_FEED_URL = "http://namabolic.exblog.jp/index.xml";
//    private static final String RSS_FEED_URL = "http://www.engadget.com/rss.xml";
    public static final int MENU_ITEM_RELOAD = Menu.FIRST;
    private RssListAdapter mAdapter;
    private ArrayList<Item> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        AdView adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest());

        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();
        mAdapter = new RssListAdapter(this, mItems);
        // タスクを起動する
        RssParserTask task = new RssParserTask(this, mAdapter);
        task.execute(RSS_FEED_URL);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = mItems.get(position);
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("TITLE", item.getTitle());
        intent.putExtra("DESCRIPTION", item.getDescription());
        intent.putExtra("DATE", item.getDate());
        intent.putExtra("CREATOR", item.getCreator());
        intent.putExtra("LINK", item.getLink());
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.isLongPress()) {
            if (KeyEvent.KEYCODE_MENU == keyCode) {
                mItems = new ArrayList();
                mAdapter = new RssListAdapter(this, mItems);
                // タスクはその都度生成する
                RssParserTask task = new RssParserTask(this, mAdapter);
                task.execute(RSS_FEED_URL);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        // デフォルトではアイテムを追加した順番通りに表示する
        menu.add(0, MENU_ITEM_RELOAD, 0, "更新");
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 更新
            case MENU_ITEM_RELOAD:
                // アダプタを初期化し、タスクを起動する
                mItems = new ArrayList();
                mAdapter = new RssListAdapter(this, mItems);
                // タスクはその都度生成する
                RssParserTask task = new RssParserTask(this, mAdapter);
                task.execute(RSS_FEED_URL);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
