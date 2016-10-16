package com.quackcon.project.engage;

import com.quackcon.project.models.Event;

import java.util.List;

/**
 * Created by alec on 10/15/16.
 */

public interface EngageContract {

    interface View {

        void showEvents(List<Event> events);

        void vibrate(int intensity);
    }

    interface Presenter {

        void loadEvents();

        void initializeDataStreams();
    }
}
