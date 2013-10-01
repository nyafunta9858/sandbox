package com.example.simplerss;

import java.util.ArrayList;

import com.google.ads.*;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ItemDetailActivity extends Activity {
    public static final int MENU_ITEM_LINK = Menu.FIRST;

    private TextView mTitle;
    private TextView mDescr;
    private TextView mDate;
    private TextView mCreator;
    private String mLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();

        String title = intent.getStringExtra("TITLE");
        mTitle = (TextView) findViewById(R.id.item_detail_title);
        mTitle.setText(title);
        String descr = intent.getStringExtra("DESCRIPTION");
        mDescr = (TextView) findViewById(R.id.item_detail_descr);
        mDescr.setText(Html.fromHtml(descr));
        String date = intent.getStringExtra("DATE");
        mDate = (TextView) findViewById(R.id.item_detail_time_stamp);
        mDate.setText(Html.fromHtml(date));
        String creator = intent.getStringExtra("CREATOR");
        mCreator = (TextView) findViewById(R.id.item_detail_creator);
        mCreator.setText(Html.fromHtml(creator));
        mLink = intent.getStringExtra("LINK");
//        mLink = (TextView) findViewById(R.id.item_detail_descr);
//        mLink.setText(Html.fromHtml(link));

        AdView adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_LINK, 0, "Link");
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 更新
            case MENU_ITEM_LINK:
                if (null != mLink && !TextUtils.isEmpty((CharSequence) mLink)) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mLink));
                    startActivity(i);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
