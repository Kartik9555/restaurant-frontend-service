package com.jet.restaurant.frontend.api;

import com.jet.restaurant.frontend.api.configuration.KafkaConfig;
import com.jet.restaurant.frontend.api.domain.Status;
import com.jet.restaurant.frontend.api.listener.RestaurantStatusInboundEvent;
import com.jet.restaurant.frontend.api.service.RestaurantService;
import com.jet.restaurant.frontend.api.topic.Restaurant;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@SpringBootTest(classes = {TestKafkaConsumer.Config.class, RestaurantStatusInboundEvent.class})
@EmbeddedKafka(
        partitions = 1,
        controlledShutdown = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestKafkaConsumer {

    @MockBean
    private RestaurantService service;

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @BeforeAll
    public void setUp() throws Exception {
        for (final MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer,
                    kafkaEmbedded.getPartitionsPerTopic());
        }
    }

    @Value("${kafka.topic.status-update}")
    private String topicName;

    @Autowired
    private KafkaTemplate requestKafkaTemplate;

    @Test
    void consume_success() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setStatus(Status.OPEN);
        requestKafkaTemplate.send(topicName, restaurant);
    }


    @Configuration
    @Import({
            KafkaConfig.class
    })
    public static class Config {

        @Value(value = "${spring.kafka.bootstrapServer}")
        private String bootstrapAddress;

        @Bean
        public ProducerFactory<String, String> requestProducerFactory() {
            final Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public KafkaTemplate<String, String> requestKafkaTemplate() {
            return new KafkaTemplate<>(requestProducerFactory());
        }
    }
}
