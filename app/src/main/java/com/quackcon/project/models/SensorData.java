package com.quackcon.project.models;

/**
 * Created by alec on 10/15/16.
 */

public class SensorData {

    private Long id;
    private int intensity;

    public SensorData() {

    }

    public SensorData(Long id, int intensity) {
        this.id = id;
        this.intensity = intensity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
