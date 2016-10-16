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

    private final String brokerUrl1 = "tcp://52.25.184.170:1884";
    private final String brokerUrl2 = "tcp://52.25.184.170:1883";
    private final String sensorDataTopic = "sensorData";
    private String clientId = UUID.randomUUID().toString();
    private String clientId2 = UUID.randomUUID().toString();
    private IMqttClient client1;
    private IMqttClient client2;
    private MemoryPersistence persistence;

    // TODO: Dependency injection
    public SensorDataServiceMQTT(IMqttClient client, MemoryPersistence persistence) {
        this.client1 = client;
        this.persistence = persistence;
    }

    public SensorDataServiceMQTT() {
        this.persistence = new MemoryPersistence();
        try {
            this.client1 = new MqttClient(brokerUrl1, clientId, persistence);
            this.client2 = new MqttClient(brokerUrl2, clientId2, persistence);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    public Observable<SensorData> getAllSensorData() {
        return Observable.create((Subscriber<? super SensorData> subscriber) -> {
                try {
                    client1.setCallback(new SubscribeCallback(subscriber));
                    client1.connect();
                    client1.subscribe(sensorDataTopic + "/#");
                } catch (MqttException me) {
                    me.printStackTrace();
                }
            });
    }

    public Observable<SensorData> getAllSensorData2() {
        return Observable.create((Subscriber<? super SensorData> subscriber) -> {
            try {
                client2.setCallback(new SubscribeCallback(subscriber));
                client2.connect();
                client2.subscribe(sensorDataTopic + "/#");
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
                if(sensorData.getEventType() >= 0 && sensorData.getEventType() < 5) {
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
