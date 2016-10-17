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

    public long[] getPattern() {
        long[] pattern = null;
        switch (eventType) {
            case 0:
                pattern = new long[] {0, 250};
                break;
            case 1:
                pattern = new long[] {0, 150, 75, 150};
                break;
            case 2:
                pattern = new long[] {0, 150, 75, 150, 75, 150};
                break;
            case 3:
                pattern = new long[] {0, 150, 75, 150, 75, 150, 75, 150};
                break;
            case 4:
                pattern = new long[] {0, 500, 200, 500, 200, 500, 200, 500};
                break;
            default:
                break;
        }

        return pattern;
    }
}
