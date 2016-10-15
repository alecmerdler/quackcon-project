package com.quackcon.project.engage;

/**
 * Created by alec on 10/15/16.
 */

public interface EngageContract {

    interface View {

        void vibrate(int intensity);
    }

    interface Presenter {

        void initializeDataStreams();
    }
}
