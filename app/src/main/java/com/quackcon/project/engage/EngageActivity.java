package com.quackcon.project.engage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

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
        // FIXME: Acquire Pebble ID programmatically
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
    public void vibrate(long[] pattern) {
        if (isPhoneEnabled) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern, -1);
        }
        if (isPebbleEnabled) {
            messagePebble((pattern.length / 2) - 1);
        }
    }

    private void messagePebble(int eventType) {
        PebbleDictionary dictionary = new PebbleDictionary();
        dictionary.addInt32(0, eventType);
        PebbleKit.sendDataToPebble(getApplicationContext(), pebbleUUID, dictionary);
    }

    private void enableDialog(CharSequence[] options) {
        new AlertDialog.Builder(this)
                .setMultiChoiceItems(options, new boolean[options.length], (DialogInterface dialog, int which, boolean isChecked) -> {})
                .setNegativeButton(R.string.common_cancel, null)
                .setPositiveButton(R.string.common_okay, null)
                .setTitle(R.string.event_specific_select_prompt)
                .show();
    }

    private void setEventListeners() {
        isPhoneEnabled = true;
        isPebbleEnabled = true;
        if (PebbleKit.isWatchConnected(getApplicationContext())) {
            isPebbleEnabled = false;
            togglePebble.setClickable(false);
            Toast.makeText(getApplicationContext(), "Pebble connection available", Toast.LENGTH_LONG).show();
            togglePebble.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                isPebbleEnabled = isChecked;
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Pebble connect not available", Toast.LENGTH_LONG).show();
        }
        togglePhone.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            isPhoneEnabled = isChecked;
        });
        eventGridView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
                // FIXME: Associate options with Event model for direct access
                switch (position) {
                    case 0:
                        enableDialog(new CharSequence[] {"Batting", "Catching", "Home Runs"});
                        break;
                    case 1:
                        enableDialog(new CharSequence[] {"Hitting", "Touchdown"});
                        break;
                    case 2:
                        enableDialog(new CharSequence[] {"Hitting", "Scoring", "Saves"});
                        break;
                    case 3:
                        enableDialog(new CharSequence[] {"Scoring", "Goal Post"});
                        break;
                    case 4:
                        enableDialog(new CharSequence[] {"Hit"});
                        break;
                    case 5:
                        enableDialog(new CharSequence[] {"Goal", "Block", "Penalty"});
                        break;
                }
        });
    }
}
