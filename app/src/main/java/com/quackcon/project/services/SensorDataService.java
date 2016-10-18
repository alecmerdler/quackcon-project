package com.quackcon.project.services;

import com.quackcon.project.models.SensorData;

import rx.Observable;

/**
 * Created by alec on 10/15/16.
 */

public interface SensorDataService {

    Observable<SensorData> getAllSensorData();


}
