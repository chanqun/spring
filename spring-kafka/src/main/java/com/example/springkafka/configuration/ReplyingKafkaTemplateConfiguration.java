package com.example.springkafka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class ReplyingKafkaTemplateConfiguration {

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(
            ProducerFactory<String, String> producerFactory,
            ConcurrentMessageListenerContainer<String, String> repliesContainer
    ) {
        return new ReplyingKafkaTemplate<>(producerFactory, repliesContainer);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesContainer(ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory) {
        ConcurrentMessageListenerContainer<String, String> container = concurrentKafkaListenerContainerFactory.createContainer("clip3-replies");
        container.getContainerProperties().setGroupId("clip3-replies-containter-id");

        return container;
    }
}
