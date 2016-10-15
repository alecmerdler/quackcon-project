package com.quackcon.project.engage;

import com.quackcon.project.services.SensorDataService;

import rx.Scheduler;

/**
 * Created by alec on 10/15/16.
 */

public class EngagePresenter implements EngageContract.Presenter {

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
}
