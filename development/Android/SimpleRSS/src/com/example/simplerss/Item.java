package com.example.simplerss;

public class Item {
    private CharSequence mTitle;    // 記事のタイトル
    private CharSequence mDescription;    // 記事の本文
    private CharSequence mDate;         // 更新日
    private CharSequence mCreator;         // 管理人
    private CharSequence mLink;         // リンク
    private boolean mHasImg;

    public Item() {
        mTitle = "";
        mDescription = "";
        mDate = "";
        mCreator = "";
        mLink = "";
        mHasImg = false;
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public void setDescription(CharSequence description) {
        mDescription = description;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public CharSequence getDate() {
        return mDate;
    }
    
    public void setDate(CharSequence date) {
        mDate = date;
    }

    public CharSequence getCreator() {
        return mCreator;
    }

    public void setCreator(CharSequence creator) {
        mCreator = creator;
    }

    public CharSequence getLink() {
        return mLink;
    }

    public void setLink(CharSequence link) {
        mLink = link;
    }

    public boolean hasImg() {
        return mHasImg;
    }
}