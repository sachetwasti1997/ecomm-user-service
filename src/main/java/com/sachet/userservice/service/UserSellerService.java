package com.sachet.userservice.service;

import com.sachet.userservice.config.EnvironmentConfiguration;
import com.sachet.userservice.model.ProductDto;
import com.sachet.userservice.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSellerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserRepository userRepository;
    private final EnvironmentConfiguration environmentConfiguration;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserSellerService.class);

    public UserSellerService(KafkaTemplate<String, String> kafkaTemplate, UserRepository userRepository, EnvironmentConfiguration environmentConfiguration) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRepository = userRepository;
        this.environmentConfiguration = environmentConfiguration;
    }

    public void sendAddProductEvent(ProductDto productDto) {
        kafkaTemplate.send(environmentConfiguration.getKafkaConfiguration().getTopics().get("user-add-product"), productDto.toString())
                .thenAccept(result -> {
                    LOGGER.info("Successfully sent the event {}", result);
                })
                .join();
    }
}
