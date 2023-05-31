package com.telefield.onebody.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.stereotype.Component;

@Component
public class MqttConfig {
    private static String MQTT_USERNAME;
    private static String MQTT_PASSWORD;
    private static String BROKER_URL;

    private static MqttConnectOptions connectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setServerURIs(new String[]{BROKER_URL});
        options.setUserName(MQTT_USERNAME);
        options.setPassword(MQTT_PASSWORD.toCharArray());
        return options;
    }

    public static DefaultMqttPahoClientFactory defaultMqttPahoClientFactory() {
        DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
        clientFactory.setConnectionOptions(connectOptions());

        return clientFactory;
    }

    public static String getBrokerUrl() {
        return BROKER_URL;
    }

    @Value("${mqtt.BROKER_URL}")
    public void setBrokerUrl(String brokerUrl) {
        BROKER_URL = brokerUrl;
    }

    @Value("${mqtt.MQTT_PASSWORD}")
    public void setMqttPassword(String mqttPassword) {
        MQTT_PASSWORD = mqttPassword;
    }

    @Value("${mqtt.MQTT_USERNAME}")
    public void setMqttUsername(String mqttUsername) {
        MQTT_USERNAME = mqttUsername;
    }
}
