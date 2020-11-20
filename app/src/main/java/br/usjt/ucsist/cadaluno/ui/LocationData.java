package br.usjt.ucsist.cadaluno.ui;

public class LocationData {

    double longitude;
    double latitude;
    int data;

    public LocationData(double longitude, double latitude, int data) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.data = data;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}

