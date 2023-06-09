package com.telefield.onebody.controller;

import com.telefield.onebody.dto.GatewayDataDto;
import com.telefield.onebody.dto.GatewayDetailDto;
import com.telefield.onebody.dto.GatewayDto;
import com.telefield.onebody.dto.GatewayRegistrationDto;
import com.telefield.onebody.entity.Event;
import com.telefield.onebody.entity.GatewayCycle;
import com.telefield.onebody.entity.GatewayData;
import com.telefield.onebody.mqtt.MqttHandler;
import com.telefield.onebody.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class DeviceController {
    private final DeviceService deviceService;
    private final MqttHandler.outboundGateway sendResponse;

    @GetMapping("/gateways")
    public List<GatewayDto> getAllGateways(
            @RequestParam String userId
    ) {
        log.info("GET /gateways");
        log.info("userId: {}", userId);
        return deviceService.getAllDevices(userId);
    }

    @GetMapping("/gateway/{macAddress}")
    public GatewayDetailDto getGatewayDetail(
            @PathVariable String macAddress
    ) {
        log.info("GET /gateway/{}", macAddress);
        return deviceService.getGatewayDetail(macAddress);
    }

    @PutMapping("/gateway/registration")
    public GatewayRegistrationDto.Response registrationGateway(
            @RequestBody GatewayRegistrationDto.Request request
    ) {
        log.info("PUT /gateway/registration");
        log.info("requestBody: {}", request);

        return deviceService.registrationGateway(request);
    }

    @GetMapping("/gateway/{macAddress}/data")
    public GatewayDataDto getGatewayData(
            @PathVariable String macAddress
    ) {
        log.info("GET /gateway/{}/data", macAddress);

        return deviceService.getGatewayData(macAddress);
    }

    @GetMapping("/gateway/{macAddress}/data/list")
    public List<GatewayData> getGatewayDataList(
            @PathVariable String macAddress
    ) {
        log.info("GET /gateway/{}/data/list", macAddress);

        return deviceService.getGatewayDataList(macAddress);
    }

    @GetMapping("/gateway/{macAddress}/event/list")
    public List<Event> getGatewayEventList(
            @PathVariable String macAddress
    ) {
        log.info("GET /gateway/{}/event/list", macAddress);

        return deviceService.getGatewayEventList(macAddress);
    }

    @GetMapping("/user/{userId}/event/list")
    public List<Event> getGatewayEventListByUserId(
            @PathVariable String userId
    ) {
        log.info("GET /user/{}/event/list", userId);

        return deviceService.getGatewayEventListByUserId(userId);
    }

    @GetMapping("/gateway/week/event/count")
    public Map<LocalDate, Long> getGatewayEventCount(
    ) {
        log.info("GET /gateway/week/event/count");

        return deviceService.getGatewayEventCount();
    }

    @GetMapping("/gateway/today/event/list")
    public List<Event> getTodayAllGatewayEventList() {
        log.info("GET /gateway/today/event/list");

        return deviceService.getTodayAllGatewayEventList();
    }

    @GetMapping("/gateway/{macAddress}/cycles")
    public List<GatewayCycle> getGatewayCycleList(@PathVariable String macAddress) {
        log.info("GET /gateway/{}/cycles", macAddress);

        return deviceService.getGatewayCycleList(macAddress);
    }

    @GetMapping("/gateway/{userId}/operating")
    public String getOperatingToday(@PathVariable String userId) {
        log.info("GET /gateway/{}/operating", userId);
        return deviceService.getOperating(userId);
    }

    @PostMapping("/remote/{macAddress}")
    public void remoteTest(
            @PathVariable String macAddress, @RequestParam String order
    ) {
        log.info("remote/{}", macAddress);
        String topic = "remote/" + macAddress;
        sendResponse.sendToMqtt(order, topic);
    }
}
