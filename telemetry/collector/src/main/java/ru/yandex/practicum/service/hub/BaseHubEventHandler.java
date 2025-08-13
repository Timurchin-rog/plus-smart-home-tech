package ru.yandex.practicum.service.hub;

import com.google.protobuf.Timestamp;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.service.HubEventHandler;

import java.time.Instant;

public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    protected final KafkaEventProducer producer;

    @Value("${collector.kafka.topics.hubs-events}")
    private String hubsTopic;

    public BaseHubEventHandler(KafkaEventProducer producer) {
        this.producer = producer;
    }

    protected abstract T mapToAvro(HubEventProto eventProto);

    @Override
    public void handle(HubEventProto eventProto) {
        if (!eventProto.getPayloadCase().equals(getMessageType())) {
            throw new IllegalArgumentException(String.format("Неизвестный тип события: %s", eventProto.getPayloadCase()));
        }

        T payload = mapToAvro(eventProto);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(eventProto.getHubId())
                .setTimestamp(mapToInstant(eventProto.getTimestamp()))
                .setPayload(payload)
                .build();

        producer.send(hubsTopic, eventAvro);
    }

    protected Instant mapToInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(
                timestamp.getSeconds(),
                timestamp.getNanos()
        );
    }

}
