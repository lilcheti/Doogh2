package com.milcrypto.doogh;

import android.app.Application;

public class kirtuin extends Application {
    private String subtitle;
    private String myList;

    public String getMyList() {
        return myList;
    }

    public void setMyList(String myList) {
        this.myList = myList;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
