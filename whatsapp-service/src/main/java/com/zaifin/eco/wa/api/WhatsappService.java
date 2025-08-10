package com.zaifin.eco.wa.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WhatsappService {

    private final WebClient webClient;
    private final String verifyToken;

    public WhatsappService(@Value("${meta.webhook-verify-token}") String verifyToken) {
        this.verifyToken = verifyToken;
        this.webClient = WebClient.builder().baseUrl("https://graph.facebook.com/v15.0") // Example WhatsApp API base URL
                .build();
    }

    // Dummy signature verification logic
    public boolean verifySignature(String payload, String signature) {
        // TODO: Implement real verification using verifyToken or app secret
        return true;
    }

    public void processInboundMessage(String payload) {
        // TODO: parse and handle incoming WhatsApp messages
        System.out.println("Received payload: " + payload);
    }

    public void sendMessage(String to, String message) {
        // TODO: call WhatsApp Business API to send message using WebClient
        System.out.printf("Sending message to %s: %s%n", to, message);
    }
}
