package com.kaim808.mbtaapis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoutesRoot {

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