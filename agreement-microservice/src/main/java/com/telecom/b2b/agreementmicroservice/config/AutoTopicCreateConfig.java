package com.telecom.b2b.agreementmicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoTopicCreateConfig {

    @Bean
    public NewTopic AgreementEvents(){
        return TopicBuilder.name("agreement-events")
                .partitions(3)
                .replicas(3)
                .build();
    }

}
