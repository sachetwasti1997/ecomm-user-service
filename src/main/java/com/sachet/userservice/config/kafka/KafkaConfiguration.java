package com.sachet.userservice.config.kafka;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class KafkaConfiguration {
    private String bootstrapServers;
    private String consumerGroup;
    private Map<String, String> topics;
}
