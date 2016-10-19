package com.quackcon.project.engage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.quackcon.project.models.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alec on 10/15/16.
 */

public class EventGridAdapter extends ArrayAdapter<Event> {

    public EventGridAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 4, 8, 4);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setContentDescription(getItem(position).getName());
        
        return retrievePreviewImage(imageView, getItem(position));
    }

    private ImageView retrievePreviewImage(ImageView imageView, Event event) {
        Picasso.with(getContext())
                .load(event.getPreviewImageUrl().toString())
                .into(imageView);

        return imageView;
    }
}
