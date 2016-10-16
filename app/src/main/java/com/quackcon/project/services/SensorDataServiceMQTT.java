package com.quackcon.project.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quackcon.project.models.SensorData;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by alec on 10/15/16.
 */

public class SensorDataServiceMQTT implements SensorDataService {

    private final String brokerUrl = "tcp://52.25.184.170:1883";
    private final String sensorDataTopic = "sensorData";
    private String clientId = UUID.randomUUID().toString();
    private IMqttClient client;
    private MemoryPersistence persistence;

    // TODO: Dependency injection
    public SensorDataServiceMQTT(IMqttClient client, MemoryPersistence persistence) {
        this.client = client;
        this.persistence = persistence;
    }

    public SensorDataServiceMQTT() {
        this.persistence = new MemoryPersistence();
        try {
            this.client = new MqttClient(brokerUrl, clientId, persistence);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    public rx.Observable<SensorData> getAllSensorData() {
        return Observable.create((Subscriber<? super SensorData> subscriber) -> {
                try {
                    client.setCallback(new SubscribeCallback(subscriber));
                    client.connect();
                    client.subscribe(sensorDataTopic + "/#");
                } catch (MqttException me) {
                    me.printStackTrace();
                }
            });
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
        public void messageArrived(String topic, MqttMessage message) {
            try {
                byte[] bytes = message.getPayload();
                SensorData sensorData = objectMapper.readValue(message.getPayload(), SensorData.class);
                if(sensorData.getIntensity() == 1) {
                    subscriber.onNext(sensorData);
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }
}
