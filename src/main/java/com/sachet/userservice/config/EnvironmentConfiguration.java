package com.sachet.userservice.config;

import com.sachet.userservice.config.kafka.KafkaConfiguration;
import com.sachet.userservice.config.model.DatabaseConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "user.config")
public class EnvironmentConfiguration {
    private DatabaseConfiguration databaseConfiguration;
    private KafkaConfiguration kafkaConfiguration;
}
