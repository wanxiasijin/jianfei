package com.android.commonlib.okhttp.bean;

public class TrackData {
    private String lng;
    private String lat;
    private String gps_time;
    private String velocity;
    private String edr_odometer;
    private String acc_on_off;
    private String angle;
    private String vehicle_no;
    private String whether_locate;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getGps_time() {
        return gps_time;
    }

    public void setGps_time(String gps_time) {
        this.gps_time = gps_time;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getEdr_odometer() {
        return edr_odometer;
    }

    public void setEdr_odometer(String edr_odometer) {
        this.edr_odometer = edr_odometer;
    }

    public String getAcc_on_off() {
        return acc_on_off;
    }

    public void setAcc_on_off(String acc_on_off) {
        this.acc_on_off = acc_on_off;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getWhether_locate() {
        return whether_locate;
    }

    public void setWhether_locate(String whether_locate) {
        this.whether_locate = whether_locate;
    }
}
