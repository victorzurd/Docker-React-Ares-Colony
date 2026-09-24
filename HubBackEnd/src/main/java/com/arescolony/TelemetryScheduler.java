package com.arescolony;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TelemetryScheduler {

    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();

    public TelemetryScheduler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Se ejecuta automáticamente cada 2000 milisegundos (2 segundos)
    @Scheduled(fixedRate = 2000)
    public void sendTelemetry() {
        Map<String, Object> telemetryData = new HashMap<>();
        
        // Simulación de sensores de la colonia de Marte
        telemetryData.put("oxigeno", 90 + random.nextDouble() * 10); // 90% a 100%
        telemetryData.put("presion", 1.0 + (random.nextDouble() * 0.2 - 0.1)); // ~1.0 atm
        telemetryData.put("temperatura", -60 + (random.nextDouble() * 10 - 5)); // ~ -60 °C
        telemetryData.put("radiacion", random.nextInt(50)); // mSv
        telemetryData.put("timestamp", System.currentTimeMillis());

        // Emitir los datos al canal público /topic/telemetry
        messagingTemplate.convertAndSend("/topic/telemetry", telemetryData);
    }
} 
