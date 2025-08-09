package ru.yandex.practicum.service.sensor;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.service.SensorEventHandler;

import java.time.Instant;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    protected final KafkaEventProducer producer;

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

        log.info("Сообщение для отправки: {}", eventAvro);
        producer.send("telemetry.sensors.v1", eventAvro);
    }

    protected Instant mapToInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(
                timestamp.getSeconds(),
                timestamp.getNanos()
        );
    }
}
