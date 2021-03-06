package com.quackcon.project.services;

import com.quackcon.project.models.Event;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alec on 10/15/16.
 */

public class EventServiceMock implements EventService {

    private List<Event> events = new ArrayList<>();

    public EventServiceMock() {
        try {
            events.add(new Event(new Long(1), new URL("https://i.ytimg.com/vi/Dj1_HI1dABE/maxresdefault.jpg"), "Baseball"));
            events.add(new Event(new Long(2), new URL("https://upload.wikimedia.org/wikipedia/commons/1/1b/2013_Alabama_A-Day_spring_football_game.jpg"), "Football"));
            events.add(new Event(new Long(3), new URL("https://upload.wikimedia.org/wikipedia/commons/0/0b/OHL-Hockey-Plymouth-Whalers-vs-Saginaw-Spirit.jpg"), "Hockey"));
            events.add(new Event(new Long(4), new URL("https://metrouk2.files.wordpress.com/2013/02/ay102890475manchester-citys.jpg"), "Soccer"));
            events.add(new Event(new Long(5), new URL("http://www3.pictures.zimbio.com/gi/Authors+vs+Actors+Cricket+Match+Lords+3aTEeiNg_Bnl.jpg"), "Cricket"));
            events.add(new Event(new Long(6), new URL("https://thumbs.dreamstime.com/z/water-polo-match-pro-recco-barceloneta-sori-italy-march-unidentified-players-team-action-valid-39283891.jpg"), "Water Polo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Event> listEvents() {
        return events;
    }
}
