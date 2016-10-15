package com.quackcon.project.engage;

import com.quackcon.project.models.SensorData;
import com.quackcon.project.services.SensorDataService;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;

/**
 * Created by alec on 10/15/16.
 */

public class EngagePresenter implements EngageContract.Presenter {

    // FIXME: Testing
    private List<SensorData> dataList = new ArrayList<>();

    private final EngageContract.View view;
    private final SensorDataService sensorDataService;
    private final Scheduler ioScheduler;
    private final Scheduler mainThreadScheduler;

    public EngagePresenter(EngageContract.View view,
                           SensorDataService sensorDataService,
                           Scheduler ioScheduler,
                           Scheduler mainThreadScheduler) {
        this.view = view;
        this.sensorDataService = sensorDataService;
        this.ioScheduler = ioScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    public void initializeDataStreams() {
        sensorDataService.getAllSensorData()
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe((SensorData sensorData) -> {
                    dataList.add(sensorData);
                });
    }
}
