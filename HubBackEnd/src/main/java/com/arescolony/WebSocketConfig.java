package com.arescolony;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Canal por donde el servidor (Java) enviará los datos al frontend
        config.enableSimpleBroker("/topic");
        
        // Prefijo para los mensajes que envíe el cliente al servidor
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Punto de conexión para el Frontend (permitiendo CORS)
        registry.addEndpoint("/ws-ares")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Fallback si el navegador no soporta WebSockets nativos
    }
}