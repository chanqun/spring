package com.example.springkafka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public KafkaAdmin.NewTopics clip2s() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("clip3").build()
        );
    }
}
