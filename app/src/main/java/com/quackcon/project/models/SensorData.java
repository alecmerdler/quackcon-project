package com.quackcon.project.models;

/**
 * Created by alec on 10/15/16.
 */

public class SensorData {

    private Long id;
    private int intensity;
    private int duration;

    public SensorData() {

    }

    public SensorData(Long id, int intensity, int duration) {
        this.id = id;
        this.intensity = intensity;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
