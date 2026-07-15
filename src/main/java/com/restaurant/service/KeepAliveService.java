package com.restaurant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KeepAliveService {

    private static final Logger logger = LoggerFactory.getLogger(KeepAliveService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    // Ping the backend every 10 minutes (600000 ms) to keep the Render free tier instance awake
    @Scheduled(fixedRate = 600000)
    public void pingSelf() {
        try {
            // Ping the public URL so Render sees it as incoming external traffic
            String url = "https://zyra-backend-snqj.onrender.com/api/restaurants";
            logger.info("Pinging self to keep backend awake at {}", url);
            restTemplate.getForObject(url, String.class);
            logger.info("Successfully pinged backend");
        } catch (Exception e) {
            logger.error("Failed to ping self: {}", e.getMessage());
        }
    }
}
