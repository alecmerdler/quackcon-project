package com.quackcon.project;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.quackcon.project.engage.EngageContract;
import com.quackcon.project.engage.EngagePresenter;
import com.quackcon.project.engage.EventGridAdapter;
import com.quackcon.project.models.Event;
import com.quackcon.project.services.EventServiceMock;
import com.quackcon.project.services.SensorDataServiceMQTT;

import java.util.List;
import java.util.UUID;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EngageActivity extends AppCompatActivity implements EngageContract.View {

    private Context context;
    private EngageContract.Presenter presenter;
    private GridView eventGridView;
    private UUID pebbleUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pebbleUUID = UUID.fromString("34b52074-c2a6-4f57-ad53-9d805536492c");
        eventGridView = (GridView) findViewById(R.id.events_gridview);
        context = this;
        presenter = new EngagePresenter(this,
                                        new SensorDataServiceMQTT(),
                                        new EventServiceMock(),
                                        Schedulers.io(),
                                        AndroidSchedulers.mainThread());
        presenter.initializeDataStreams();
        presenter.loadEvents();
    }

    @Override
    public void showEvents(List<Event> events) {
        eventGridView.setAdapter(new EventGridAdapter(context, events));
    }

    @Override
    public void vibrate(int eventType) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[] {0, 500}, -1);
        messagePebble(eventType);
    }

    private void messagePebble(int eventType) {
        boolean connected = PebbleKit.isWatchConnected(getApplicationContext());
        PebbleDictionary dictionary = new PebbleDictionary();
        dictionary.addInt32(1, eventType);
        PebbleKit.sendDataToPebble(getApplicationContext(), pebbleUUID, dictionary);
    }
}
