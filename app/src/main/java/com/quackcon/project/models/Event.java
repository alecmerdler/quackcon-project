package com.quackcon.project.models;

import java.net.URL;

/**
 * Created by alec on 10/15/16.
 */

public class Event {

    private Long id;
    private URL previewImageUrl;
    private String name;


    public Event(Long id, URL previewImageUrl, String name) {
        this.id = id;
        this.previewImageUrl = previewImageUrl;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URL getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(URL previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
