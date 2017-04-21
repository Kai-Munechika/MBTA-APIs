package com.kaim808.mbtaapis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StopsByRouteRoot {

    @SerializedName("direction")
    @Expose
    private List<Direction> direction = null;

    public List<Direction> getDirection() {
        return direction;
    }

    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

}