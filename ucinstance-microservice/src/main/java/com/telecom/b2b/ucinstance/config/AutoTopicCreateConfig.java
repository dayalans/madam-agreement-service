package com.telecom.b2b.ucinstance.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoTopicCreateConfig {

    @Bean
    public NewTopic OrderEngineEvents(){
        return TopicBuilder.name("orderengine-initiate-events")
                .partitions(3)
                .replicas(3)
                .build();
    }
    
    @Bean
    public NewTopic UCInstanceEvents(){
        return TopicBuilder.name("orderengine-initiate-events")
                .partitions(3)
                .replicas(3)
                .build();
    }

}
