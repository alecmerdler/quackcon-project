package com.quackcon.project;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quackcon.project.engage.EngageContract;
import com.quackcon.project.engage.EngagePresenter;
import com.quackcon.project.services.SensorDataServiceMQTT;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EngageActivity extends AppCompatActivity implements EngageContract.View {

    private Context context;
    private EngageContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        presenter = new EngagePresenter(this,
                                        new SensorDataServiceMQTT(),
                                        Schedulers.io(),
                                        AndroidSchedulers.mainThread());
        presenter.initializeDataStreams();
    }

    @Override 
    public void vibrate(int intensity) {
        Vibrator mVib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        // intensity is in range of 1-10
        // convert the range to 10-100%
        int lclIntnsty = intensity; // limit the intensity range to 1-10
        lclIntnsty *= 10;   // convert the range to 10-100
        mVib.vibrate(new long[] {(100 - lclIntnsty), lclIntnsty}, -1);  // turn the vibrator ON for duration of the intnesity
    }
}
