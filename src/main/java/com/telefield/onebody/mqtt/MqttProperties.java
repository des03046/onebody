package com.telefield.onebody.mqtt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class MqttProperties {
    @Value("${MQTT_USERNAME}")
    private String MQTT_USERNAME;

    @Value("${MQTT_PASSWORD}")
    private String MQTT_PASSWORD;

    @Value("${BROKER_URL}")
    private String BROKER_URL;

}
