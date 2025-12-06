package ru.yandex.practicum.kafka.telemetry.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.service.hub.HubEventService;
import ru.yandex.practicum.kafka.telemetry.service.sensor.SensorEventService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class CollectorController extends CollectorControllerGrpc.CollectorControllerImplBase {

    private final HubEventService hubEventService;
    private final SensorEventService sensorEventService;


    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            sensorEventService.processEvent(request);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL.
                            withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }

    }

    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<HubEventProto> responseObserver) {
        try {
            hubEventService.processEvent(request);
            responseObserver.onNext(HubEventProto.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }
}