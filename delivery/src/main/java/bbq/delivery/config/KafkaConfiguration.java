package bbq.delivery.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.kafka.config.TopicBuilder;


@Component
@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder.name("delivery_updates").build();
    }
    
}
