package com.zaifin.eco.wa.api;

import com.example.whatsappservice.service.WhatsappService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsappController {

    private final WhatsappService whatsappService;

    public WhatsappController(WhatsappService whatsappService) {
        this.whatsappService = whatsappService;
    }

    // webhook endpoint to receive WhatsApp messages
    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(@RequestBody String payload, @RequestHeader("X-Hub-Signature") String signature) {
        boolean valid = whatsappService.verifySignature(payload, signature);
        if (!valid) {
            return ResponseEntity.status(403).body("Invalid signature");
        }
        whatsappService.processInboundMessage(payload);
        return ResponseEntity.ok("Received");
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String to, @RequestParam String message) {
        whatsappService.sendMessage(to, message);
        return ResponseEntity.ok("Message sent");
    }
}
