package com.quackcon.project.models;

/**
 * Created by alec on 10/15/16.
 */

public class SensorData {

    private Long id;
    private int eventType;

    public SensorData() {

    }

    public SensorData(Long id, int intensity) {
        this.id = id;
        this.eventType = intensity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public
}
