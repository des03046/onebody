package com.telefield.onebody.service;

import com.telefield.onebody.dto.GatewayDataDto;
import com.telefield.onebody.dto.GatewayDetailDto;
import com.telefield.onebody.dto.GatewayDto;
import com.telefield.onebody.dto.GatewayRegistrationDto;
import com.telefield.onebody.entity.*;
import com.telefield.onebody.exception.DeviceDataException;
import com.telefield.onebody.exception.DeviceException;
import com.telefield.onebody.exception.EventException;
import com.telefield.onebody.exception.UserException;
import com.telefield.onebody.mqtt.MqttHandler;
import com.telefield.onebody.mqtt.MqttTopics;
import com.telefield.onebody.repository.*;
import com.telefield.onebody.type.DeviceType;
import com.telefield.onebody.type.EventType;
import com.telefield.onebody.type.GatewayStateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.telefield.onebody.exception.DeviceDataErrorCode.NO_DATA;
import static com.telefield.onebody.exception.DeviceErrorCode.DEVICE_ALREADY_ENROLLED;
import static com.telefield.onebody.exception.DeviceErrorCode.NO_DEVICE;
import static com.telefield.onebody.exception.EventErrorCode.NO_EVENT;
import static com.telefield.onebody.exception.UserErrorCode.NO_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {
    private final GatewayRepository gatewayRepository;
    private final GatewayDataRepository gatewayDataRepository;
    private final GatewayCycleRepository gatewayCycleRepository;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final EventRepository eventRepository;
    private final MqttHandler.outboundGateway sendResponse;


    public List<GatewayDto> getAllDevices(String userId) {
        return gatewayRepository.findByManagerIdOrChildId(userId, userId)
                .stream().map(GatewayDto::fromEntity)
                .collect(Collectors.toList());
    }

    public GatewayDetailDto getGatewayDetail(String macAddress) {
        return gatewayRepository.findByGwMacAddress(macAddress)
                .map(GatewayDetailDto::fromEntity)
                .orElseThrow(() -> new DeviceException(NO_DEVICE));
    }

    @Transactional
    public void openGateway(String macAddress, JSONObject payload) {
        String regDate = payload.getString("regDate");
        LocalDateTime localDateTime = LocalDateTime.parse(regDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (!gatewayRepository.findByGwMacAddress(macAddress).isPresent()) {
            Gateway gateway = Gateway.builder()
                    .gwRssi(payload.getString("gwRssi"))
                    .gwPhone(payload.getString("gwPhone"))
                    .gwMacAddress(payload.getString("gwmacAddr"))
                    .gwDevType(DeviceType.ACTIVITY_SENSOR)
                    .powerState(GatewayStateType.NORMAL)
                    .regDate(localDateTime)
                    .gwVer(payload.getString("gwVer"))
                    .build();

            gatewayRepository.save(gateway);
        } else {
            sendResponse.sendToMqtt(DEVICE_ALREADY_ENROLLED.getMessage(), MqttTopics.response(macAddress));
        }
    }

    @Transactional
    public GatewayRegistrationDto.Response registrationGateway(GatewayRegistrationDto.Request request) {
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new UserException(NO_USER)
        );

        Gateway gateway = gatewayRepository.findByGwMacAddress(request.getMacAddress())
                .orElseThrow(
                        () -> new DeviceException(NO_DEVICE)
                );

        if (user.isManagement())
            gateway.setManagerId(request.getUserId());
        else
            gateway.setChildId(request.getUserId());

        if (!userInfoRepository.findByGwMacAddress(request.getMacAddress()).isPresent()) {
            UserInfo userInfo = UserInfo.builder()
                    .gwMacAddress(gateway.getGwMacAddress())
                    .gwPhone(gateway.getGwPhone())
                    .build();

            userInfoRepository.save(userInfo);
        }
        return GatewayRegistrationDto.Response.fromEntity(gateway);
    }

    public GatewayDataDto getGatewayData(String macAddress) {
        //기기 존재 여부 확인
        gatewayRepository.findByGwMacAddress(macAddress)
                .orElseThrow(
                        () -> new DeviceException(NO_DEVICE)
                );

        //데이터 가져오기
        GatewayData gatewayData = gatewayDataRepository.findTopByGwMacAddrOrderByRegDateDesc(macAddress)
                .orElseThrow(
                        () -> new DeviceDataException(NO_DATA)
                );

        return GatewayDataDto.fromEntity(gatewayData);
    }

    public List<GatewayData> getGatewayDataList(String macAddress) {
        //기기 존재 여부 확인
        gatewayRepository.findByGwMacAddress(macAddress)
                .orElseThrow(
                        () -> new DeviceException(NO_DEVICE)
                );

        //데이터 가져오기
        return gatewayDataRepository.findByGwMacAddrOrderByRegDate(macAddress);
    }

    @Transactional
    public void saveDeviceStatus(String macAddress, JSONObject payload) {
        try {
            Gateway gateway = gatewayRepository.findByGwMacAddress(macAddress)
                    .orElseThrow(() -> new DeviceException(NO_DEVICE));

            String regDate = payload.getString("regDate");
            LocalDateTime localDateTime = LocalDateTime.parse(regDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            gateway.setGwRssi(payload.getString("gwRssi"));
            gateway.setRegDate(localDateTime);
            gateway.setGwVer(payload.getString("gwVer"));

            log.info("gateway: " + gateway);

            GatewayCycle gatewayCycle = GatewayCycle.builder()
                    .gwVer(payload.getString("gwVer"))
                    .gwMacAddress(macAddress)
                    .gwPhone(payload.getString("gwPhone"))
                    .gwRssi(payload.getString("gwRssi"))
                    .regDate(localDateTime)
                    .gwDevType(DeviceType.ACTIVITY_SENSOR)
                    .build();

            log.info("gatewayCycle: " + gatewayCycle);
            gatewayCycleRepository.save(gatewayCycle);
        } catch (DeviceException e) {
            sendResponse.sendToMqtt(e.getMessage(), MqttTopics.response(macAddress));
        }

    }

    @Transactional
    public void saveDeviceEnvironmentData(String macAddress, JSONObject payload) {
        try {
            gatewayRepository.findByGwMacAddress(macAddress)
                    .orElseThrow(() -> new DeviceException(NO_DEVICE));

            String regDate = payload.getString("regDate");
            LocalDateTime localDateTime = LocalDateTime.parse(regDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String co2 = payload.getString("co2");

            GatewayData gatewayData = GatewayData.builder()
                    .tVoc(payload.getString("tvoc"))
                    .co2(co2)
                    .lux(payload.getString("lux"))
                    .humidity(payload.getString("humi"))
                    .temperature(payload.getString("temp"))
                    .gwRssi(payload.getString("gwRssi"))
                    .gwMacAddr(payload.getString("gwMacAddr"))
                    .gwPhone(payload.getString("gwphone"))
                    .regDate(localDateTime)
                    .build();

            gatewayDataRepository.save(gatewayData);

            if (Integer.parseInt(co2) > 1000) {
                createEventCo2(gatewayData);
            }
        } catch (DeviceException e) {
            sendResponse.sendToMqtt(e.getMessage(), MqttTopics.response(macAddress));
        }
    }

    private void createEventCo2(GatewayData gatewayData) {
        Event event = Event.builder()
                .regDate(LocalDateTime.now())
                .gwMacAddress(gatewayData.getGwMacAddr())
                .gwPhone(gatewayData.getGwPhone())
                .eventType(EventType.TOO_MUCH_CO2)
                .build();

        eventRepository.save(event);
    }

    public List<GatewayCycle> getGatewayCycleList(String macAddress) {
        gatewayRepository.findByGwMacAddress(macAddress)
                .orElseThrow(() -> new DeviceException(NO_DEVICE));
        return gatewayCycleRepository.findByGwMacAddressOrderByRegDate(macAddress);
    }

    public void saveEvent(String macAddr, JSONObject payload) {
        try {
            gatewayRepository.findByGwMacAddress(macAddr)
                    .orElseThrow(() -> new DeviceException(NO_DEVICE));

            String regDate = payload.getString("regDate");
            LocalDateTime localDateTime = LocalDateTime.parse(regDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            EventType eventType = checkEventType(payload.getString("eventType"));

            Event event = Event.builder()
                    .eventType(eventType)
                    .regDate(localDateTime)
                    .gwPhone(payload.getString("gwPhone"))
                    .gwMacAddress(payload.getString("gwMacAddress"))
                    .build();

            eventRepository.save(event);

        } catch (DeviceException e) {
            sendResponse.sendToMqtt(e.getMessage(), MqttTopics.response(macAddr));
        }
    }

    private EventType checkEventType(String eventType) {
        switch (eventType) {
            case "0":
                return EventType.POWER_OFF;
            case "1":
                return EventType.NON_RECEIVE;
            default:
                throw new EventException(NO_EVENT);
        }
    }

    public List<Event> getGatewayEventList(String macAddress) {
        gatewayRepository.findByGwMacAddress(macAddress)
                .orElseThrow(
                        () -> new DeviceException(NO_DEVICE)
                );

        return eventRepository.findByGwMacAddressOrderByRegDate(macAddress);
    }

    public List<Event> getTodayAllGatewayEventList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return eventRepository.findByRegDateAfterOrderByRegDateDesc(
                LocalDateTime.parse(LocalDateTime
                        .now().with(LocalTime.MIN).format(formatter)));
    }

    public List<Event> getGatewayEventListByUserId(String userId) {
        List<Gateway> gatewayList = gatewayRepository.findByManagerIdOrChildId(userId, userId);
        List<Event> all = eventRepository.findAll();

        List<String> gatewayIds = gatewayList.stream()
                .map(Gateway::getGwMacAddress)
                .collect(Collectors.toList());

        return all.stream()
                .filter(event -> gatewayIds.contains(event.getGwMacAddress()))
                .collect(Collectors.toList());
    }

    public Map<GatewayStateType, Integer> getDeviceStateCount(String userId) {
        List<Gateway> gatewayList = gatewayRepository.findByManagerIdOrChildId(userId, userId);
        Map<GatewayStateType, Integer> stateCountMap = new HashMap<>();
        for (Gateway gateway : gatewayList) {
            GatewayStateType state = gateway.getPowerState();
            stateCountMap.put(state, stateCountMap.getOrDefault(state, 0) + 1);
        }
        return stateCountMap;
    }

    public Map<LocalDate, Long> getGatewayEventCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);

        List<Event> eventCounts = eventRepository.findByRegDateBetween(oneWeekAgo, now);
        return eventCounts.stream()
                .collect(Collectors.groupingBy(event -> event.getRegDate().toLocalDate(), Collectors.counting()));
    }

    public String getOperating(String userId) {
        List<Gateway> gatewayList = gatewayRepository.findByManagerIdOrChildId(userId, userId);

        int totalGateways = gatewayList.size();
        int operatingGateways = 0;

        for (Gateway gateway : gatewayList) {
            if (gateway.getPowerState().equals(GatewayStateType.NORMAL)) {
                operatingGateways++;
            }
        }

        double operatingRate = (double) operatingGateways / totalGateways * 100.0;
        String formattedOperatingRate = String.format("%.1f", operatingRate);
        return formattedOperatingRate;

    }
}
