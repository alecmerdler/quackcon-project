package com.quackcon.project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quackcon.project.engage.EngageContract;
import com.quackcon.project.engage.EngagePresenter;
import com.quackcon.project.services.SensorDataServiceMQTT;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements EngageContract.View {

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
}
