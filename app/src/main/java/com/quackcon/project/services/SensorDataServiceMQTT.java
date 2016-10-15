package com.quackcon.project.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quackcon.project.models.SensorData;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by alec on 10/15/16.
 */

public class SensorDataServiceMQTT implements SensorDataService {

    private final String brokerUrl = "http://localhost:1883";
    private final String sensorDataTopic = "sensorData";
    private String clientId = UUID.randomUUID().toString();
    private IMqttClient client;

    // TODO: Dependency injection
    public SensorDataServiceMQTT(IMqttClient client) {
        this.client = client;
    }

    public SensorDataServiceMQTT() {
        try {
            this.client = new MqttClient(brokerUrl, this.clientId);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    public rx.Observable<SensorData> getAllSensorData() {
//            return Observable.defer(() -> {
//                try {
//                    client.setCallback(new SubscribeCallback());
//                    client.connect();
//                    client.subscribe(sensorDataTopic + "/#");
//                } catch (MqttException me) {
//
//                }
//
//                return Observable.just()
//            });
        return Observable.create((Subscriber<? super SensorData> subscriber) -> {
                try {
                    client.setCallback(new SubscribeCallback(subscriber));
                    client.connect();
                    client.subscribe(sensorDataTopic + "/#");
                } catch (MqttException me) {
                    me.printStackTrace();
                }
            }).publish();
    }

    private class SubscribeCallback implements MqttCallback {

        private Subscriber<? super SensorData> subscriber;
        // FIXME: Use dependency injection
        private ObjectMapper objectMapper = new ObjectMapper();

        SubscribeCallback(Subscriber<? super SensorData> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            SensorData sensorData = objectMapper.readValue(message.getPayload(), SensorData.class);
            subscriber.onNext(sensorData);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }
}
