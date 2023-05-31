package com.telefield.onebody.mqtt;

import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

public class MqttAdapter {

    private static MqttPahoMessageDrivenChannelAdapter adapter;

    public static synchronized MqttPahoMessageDrivenChannelAdapter getInstance() {
        if (adapter == null) {
            adapter = new MqttPahoMessageDrivenChannelAdapter(MqttConfig.getBrokerUrl(), MqttConfig.defaultMqttPahoClientFactory());
        }
        return adapter;
    }
}
