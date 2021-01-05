package com.milcrypto.doogh;

import java.util.ArrayList;

public class Season {
    ArrayList<ArrayList<String>> url;

    public Season(ArrayList<ArrayList<String>> url) {
        this.url = url;
    }

    public ArrayList<ArrayList<String>> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<ArrayList<String>> url) {
        this.url = url;
    }
}
