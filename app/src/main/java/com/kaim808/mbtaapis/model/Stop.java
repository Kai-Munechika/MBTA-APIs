package com.kaim808.mbtaapis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop {

    @SerializedName("stop_order")
    @Expose
    private String stopOrder;
    @SerializedName("stop_id")
    @Expose
    private String stopId;
    @SerializedName("stop_name")
    @Expose
    private String stopName;
    @SerializedName("parent_station")
    @Expose
    private String parentStation;
    @SerializedName("parent_station_name")
    @Expose
    private String parentStationName;
    @SerializedName("stop_lat")
    @Expose
    private String stopLat;
    @SerializedName("stop_lon")
    @Expose
    private String stopLon;
    @SerializedName("stop_sequence")
    @Expose
    private String stopSequence;
    @SerializedName("sch_arr_dt")
    @Expose
    private String schArrDt;
    @SerializedName("sch_dep_dt")
    @Expose
    private String schDepDt;

    public String getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(String stopOrder) {
        this.stopOrder = stopOrder;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getParentStation() {
        return parentStation;
    }

    public void setParentStation(String parentStation) {
        this.parentStation = parentStation;
    }

    public String getParentStationName() {
        return parentStationName;
    }

    public void setParentStationName(String parentStationName) {
        this.parentStationName = parentStationName;
    }

    public String getStopLat() {
        return stopLat;
    }

    public void setStopLat(String stopLat) {
        this.stopLat = stopLat;
    }

    public String getStopLon() {
        return stopLon;
    }

    public void setStopLon(String stopLon) {
        this.stopLon = stopLon;
    }

    public String getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(String stopSequence) {
        this.stopSequence = stopSequence;
    }

    public String getSchArrDt() {
        return schArrDt;
    }

    public void setSchArrDt(String schArrDt) {
        this.schArrDt = schArrDt;
    }

    public String getSchDepDt() {
        return schDepDt;
    }

    public void setSchDepDt(String schDepDt) {
        this.schDepDt = schDepDt;
    }

}