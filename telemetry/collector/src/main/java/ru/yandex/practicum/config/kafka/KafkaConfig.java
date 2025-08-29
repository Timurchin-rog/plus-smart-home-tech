package ru.yandex.practicum.config.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Properties;

@ConfigurationProperties("kafka")
@Configuration
@Getter
@RequiredArgsConstructor
public class KafkaConfig {
    private final Map<String, String> topics;
    private final Properties producerProperties;
}
