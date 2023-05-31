package com.telefield.onebody.controller;

import com.telefield.onebody.entity.*;
import com.telefield.onebody.repository.*;
import com.telefield.onebody.service.DeviceService;
import com.telefield.onebody.type.DeviceType;
import com.telefield.onebody.type.EventType;
import com.telefield.onebody.type.GatewayStateType;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class DeviceControllerTest {
    @Autowired
    DeviceService deviceService;

    @Autowired
    GatewayRepository gatewayRepository;

    @Autowired
    GatewayDataRepository gatewayDataRepository;

    @Autowired
    GatewayCycleRepository gatewayCycleRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    public void exampleUserCreate() {
        User user = User.builder()
                .userId("admin")
                .userName("관리자")
                .phoneNumber("01000000000")
                .password("admin@1")
                .location("경기도 분당시 삼평동")
                .allowed(true)
                .management(true)
                .build();

        userRepository.save(user);
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        exampleDataCreate();
        exampleDeviceCreate();
    }

    public void exampleDeviceCreate() {
        Gateway gateway = Gateway.builder()
                .gwMacAddress("00158D000868B487")
                .gwPhone("01233438248")
                .gwDevType(DeviceType.ACTIVITY_SENSOR)
                .gwVer("1.2.2")
                .gwRssi("76")
                .powerState(GatewayStateType.NORMAL)
                .regDate(LocalDateTime.now())
                .build();

        gatewayRepository.save(gateway);
    }

    public void exampleDataCreate() {
        GatewayData gatewayData = GatewayData.builder()
                .gwMacAddr("00158D000868B487")
                .gwPhone("01233438248")
                .gwRssi("76")
                .regDate(LocalDateTime.now())
                .temperature("24")
                .humidity("23")
                .lux("433")
                .co2("805")
                .tVoc("502")
                .build();

        GatewayData gatewayData2 = GatewayData.builder()
                .gwMacAddr("00158D000868B487")
                .gwPhone("01233438248")
                .gwRssi("74")
                .regDate(LocalDateTime.now())
                .temperature("26")
                .humidity("24")
                .lux("432")
                .co2("801")
                .tVoc("503")
                .build();

        GatewayCycle gatewayCycle = GatewayCycle.builder()
                .gwVer("1.2.2")
                .gwMacAddress("00158D000868B487")
                .gwPhone("01233438248")
                .gwRssi("70")
                .regDate(LocalDateTime.now())
                .gwDevType(DeviceType.ACTIVITY_SENSOR)
                .build();

        Event event = Event.builder()
                .regDate(LocalDateTime.now())
                .gwMacAddress("00158D000868B487")
                .gwPhone("01233438248")
                .eventType(EventType.POWER_OFF)
                .build();

        Event event2 = Event.builder()
                .regDate(LocalDateTime.now())
                .gwMacAddress("00158D000868B487")
                .gwPhone("01233438248")
                .eventType(EventType.NON_RECEIVE)
                .build();

        gatewayDataRepository.save(gatewayData);
        gatewayDataRepository.save(gatewayData2);
        gatewayCycleRepository.save(gatewayCycle);
        eventRepository.save(event);
        eventRepository.save(event2);
    }

    /**
     *
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Before("findAllGateway")
    void RegistrationGateway() throws Exception {
        exampleUserCreate();
        mockMvc.perform(
                        put("/gateway/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n"
                                        + "\"macAddress\":\"00158D000868B487\",\n"
                                        + "\"userId\":\"admin\"\n"
                                        + " }")
                )
                .andDo(print())
                .andDo(document("gateway/registration"))
                .andExpect(status().isOk());
    }

    @Test
    void findAllGateway() throws Exception {
        mockMvc.perform(
                        get("/gateways")
                                .param("userId", "admin")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("gateways"))
                .andExpect(status().isOk());
    }


    @Test
    void findGateway() throws Exception {
        mockMvc.perform(
                        get("/gateway/{macAddress}", "00158D000868B487")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("gateway/{macAddress}"))
                .andExpect(status().isOk());
    }

    @Test
    void getGatewayData() throws Exception {
        mockMvc.perform(
                        get("/gateway/{macAddress}/data", "00158D000868B487")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("gateway/{macAddress}/data"))
                .andExpect(status().isOk());
    }

    @Test
    void getGatewayDataList() throws Exception {
        mockMvc.perform(
                        get("/gateway/{macAddress}/data/list", "00158D000868B487")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("gateway/{macAddress}/data/list"))
                .andExpect(status().isOk());
    }

    @Test
    void getGatewayCycleList() throws Exception {
        mockMvc.perform(
                        get("/gateway/{macAddress}/cycles", "00158D000868B487")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andDo(document("gateway/{macAddress}/cycles"))
                .andExpect(status().isOk());
    }

    @Test
    void getDeviceEventList() throws Exception {
        mockMvc.perform(
                        get("/gateway/{macAddress}/event/list", "00158D000868B487")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andDo(document("gateway/{macAddress}/event/list"))
                .andExpect(status().isOk());
    }

    @Test
    void getGatewayEventListByUserId() throws Exception {
        mockMvc.perform(
                        get("/user/{userId}/event/list", "admin")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andDo(document("user/{userId}/event/list"))
                .andExpect(status().isOk());
    }

    @Test
    void getTodayAllEventList() throws Exception {
        mockMvc.perform(
                        get("/gateway/today/event/list")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andDo(document("gateway/today/event/list"))
                .andExpect(status().isOk());
    }
}