package com.quackcon.project.services;

import com.quackcon.project.models.SensorData;

/**
 * Created by alec on 10/15/16.
 */

public interface SensorDataService {

    rx.Observable<SensorData> getAllSensorData();
    rx.Observable<SensorData> getAllSensorData2();
}
