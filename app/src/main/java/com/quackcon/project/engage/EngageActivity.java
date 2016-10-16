package com.quackcon.project.engage;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.quackcon.project.R;
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
    private Switch togglePebble;
    private Switch togglePhone;
    private boolean isPhoneEnabled;
    private boolean isPebbleEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pebbleUUID = UUID.fromString("4e4019bf-b50e-4ff4-9e05-441edb18bc70");
        eventGridView = (GridView) findViewById(R.id.events_gridview);
        togglePebble = (Switch) findViewById(R.id.toggle_vibrate_pebble);
        togglePhone = (Switch) findViewById(R.id.toggle_vibrate_phone);
        context = this;
        setEventListeners();
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
        if (isPhoneEnabled) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[] {0, 500}, -1);
        }
        if (isPebbleEnabled) {
            messagePebble(eventType);
        }
    }

    private void messagePebble(int eventType) {
        boolean connected = PebbleKit.isWatchConnected(getApplicationContext());
        PebbleDictionary dictionary = new PebbleDictionary();
        dictionary.addInt32(0, eventType);
        PebbleKit.sendDataToPebble(getApplicationContext(), pebbleUUID, dictionary);
    }

    private void setEventListeners() {
        isPhoneEnabled = true;
        isPebbleEnabled = true;
        togglePebble.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            isPebbleEnabled = isChecked;
        });
        togglePhone.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            isPhoneEnabled = isChecked;
        });
    }

    private long[] getPattern(int eventType) {
        return null;
    }
}
