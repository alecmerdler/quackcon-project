package com.quackcon.project;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;

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
        Vibrator mVib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        final int lclIntnsty = intensity *= 10;
        mVib.vibrate(new long[] {(100 - lclIntnsty), lclIntnsty}, -1);
    }
}
