package ru.yandex.practicum.service.sensor;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.config.kafka.KafkaConfig;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.config.kafka.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.service.SensorEventHandler;

import java.time.Instant;

@RequiredArgsConstructor
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    protected final KafkaEventProducer producer;
    protected final KafkaConfig kafkaConfig;
    private final String sensors = "sensors";

    protected abstract T mapToAvro(SensorEventProto eventProto);

    @Override
    public void handle(SensorEventProto eventProto) {
        if (!eventProto.getPayloadCase().equals(getMessageType())) {
            throw new IllegalArgumentException(String.format("Неизвестный тип события: %s", eventProto.getPayloadCase()));
        }

        T payload = mapToAvro(eventProto);

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setId(eventProto.getId())
                .setHubId(eventProto.getHubId())
                .setTimestamp(mapToInstant(eventProto.getTimestamp()))
                .setPayload(payload)
                .build();

        producer.send(kafkaConfig.getTopics().get(sensors), eventAvro);
    }

    protected Instant mapToInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(
                timestamp.getSeconds(),
                timestamp.getNanos()
        );
    }
}
