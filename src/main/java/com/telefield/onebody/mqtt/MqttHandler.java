package com.telefield.onebody.mqtt;

import com.telefield.onebody.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MqttHandler {
    private MqttPahoMessageDrivenChannelAdapter adapter;
    private final DeviceService deviceService;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inboundChannel() {
        adapter = MqttAdapter.getInstance();
        adapter.setCompletionTimeout(5000);
        adapter.addTopic(MqttTopics.DEVICE_STATUS);
        adapter.addTopic(MqttTopics.DEVICE_ENV_DATA);
        adapter.addTopic(MqttTopics.DEVICE_ACTIVE_DATA);
        adapter.addTopic(MqttTopics.DEVICE_EVENT);
        adapter.addTopic(MqttTopics.DEVICE_OPEN);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler inboundMessageHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            log.info("topic: " + topic);
            log.info("payload: " + message.getPayload());

            String[] topics = Objects.requireNonNull(topic).split("/");
            String macAddr = topics[3];
            log.info("macAddr: " + macAddr);

            JSONObject payload = new JSONObject(message.getPayload().toString());

            switch (topics[2]) {
                case "status":
                    //주기보고
                    log.info("주기 보고");
                    deviceService.saveDeviceStatus(macAddr, payload);
                    break;
                case "env":
                    //환경 센서
                    log.info("환경 센서");
                    deviceService.saveDeviceEnvironmentData(macAddr, payload);
                    break;
                case "active":
                    //활동 센서
                    log.info("활동 센서");
                    break;
                case "open":
                    //기기 등록
                    log.info("기기 등록");
                    deviceService.openGateway(macAddr, payload);
                    break;
                case "event":
                    log.info("이벤트 등록");
                    deviceService.saveEvent(macAddr, payload);
                    break;
            }
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), MqttConfig.defaultMqttPahoClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(2);
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface outboundGateway {
        void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);
    }
}
