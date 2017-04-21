package com.kaim808.mbtaapis.model;

/**
 * Created by KaiM on 4/12/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeadsignRoot {

    @SerializedName("mode")
    @Expose
    private List<Mode> mode = null;

    public List<Mode> getMode() {
        return mode;
    }

    public void setMode(List<Mode> mode) {
        this.mode = mode;
    }

}


