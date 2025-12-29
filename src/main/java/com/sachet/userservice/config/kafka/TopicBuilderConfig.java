package com.sachet.userservice.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicBuilderConfig {

    @Bean
    public NewTopic createProductAddTopic() {
        return TopicBuilder
                .name("user-product-add")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic createProductBuyTopic() {
        return TopicBuilder
                .name("user-product-buy")
                .partitions(3)
                .replicas(3)
                .build();
    }

}
