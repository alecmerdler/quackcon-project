package com.quackcon.project.engage;

import com.quackcon.project.models.Event;
import com.quackcon.project.models.SensorData;
import com.quackcon.project.services.EventService;
import com.quackcon.project.services.SensorDataService;

import java.util.List;

import rx.Scheduler;

/**
 * Created by alec on 10/15/16.
 */

public class EngagePresenter implements EngageContract.Presenter {

    private final EngageContract.View view;
    private final SensorDataService sensorDataService;
    private final EventService eventService;
    private final Scheduler ioScheduler;
    private final Scheduler mainThreadScheduler;

    public EngagePresenter(EngageContract.View view,
                           SensorDataService sensorDataService,
                           EventService eventService,
                           Scheduler ioScheduler,
                           Scheduler mainThreadScheduler) {
        this.view = view;
        this.sensorDataService = sensorDataService;
        this.eventService = eventService;
        this.ioScheduler = ioScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void loadEvents() {
        List<Event> events = eventService.listEvents();
        view.showEvents(events);
    }

    @Override
    public void initializeDataStreams() {
        sensorDataService.getAllSensorData()
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe((SensorData sensorData) -> {
                    view.vibrate(sensorData.getPattern());
                });
    }
}
