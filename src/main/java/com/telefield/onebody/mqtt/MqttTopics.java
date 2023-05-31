package com.telefield.onebody.mqtt;

public class MqttTopics {

    public static final String DEVICE_OPEN = "device/data/open/+";

    public static final String DEVICE_STATUS = "device/data/status/+";

    public static final String DEVICE_ENV_DATA = "device/data/env/+";

    public static final String DEVICE_ACTIVE_DATA = "device/data/active/+";

    public static final String DEVICE_EVENT = "device/data/event/+";

    public static String response(String mac) {
        return "device/response/" + mac;
    }
}
