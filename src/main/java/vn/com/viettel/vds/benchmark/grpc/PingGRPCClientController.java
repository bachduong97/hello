package vn.com.viettel.vds.benchmark.grpc;

import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.viettel.vds.arch.controller.restful.BaseController;
import vn.com.viettel.vds.arch.factory.request.GrpcRequestFactory;
import vn.com.viettel.vds.arch.factory.response.GeneralResponse;
import vn.com.viettel.vds.arch.factory.response.ResponseFactory;
import vn.com.viettel.vds.benchmark.proto.PingRequest;
import vn.com.viettel.vds.benchmark.proto.PingResponse;
import vn.com.viettel.vds.benchmark.proto.PingServiceGrpc;

@RestController
@RequestMapping("${app.name-context}/v1/api")
@Slf4j
public class PingGRPCClientController extends BaseController {
    private final ResponseFactory responseFactory;
    private final GrpcRequestFactory grpcRequestFactory;
    private final PingChannelManager pingChannelManager;

    @Value("${partner.spring-b.grpc.url}")
    private String springBGrpcUrl;

    @Value("${partner.spring-b.grpc.port}")
    private int springBGrpcPort;

    public PingGRPCClientController(ResponseFactory responseFactory, GrpcRequestFactory grpcRequestFactory, PingChannelManager pingChannelManager) {
        this.responseFactory = responseFactory;
        this.grpcRequestFactory = grpcRequestFactory;
        this.pingChannelManager = pingChannelManager;
    }

    @GetMapping(value = "/ping-grpc-single-channel")
    public ResponseEntity<GeneralResponse<String>> getRestGrpcSingleChannel(String msg) {
        PingServiceGrpc.PingServiceBlockingStub pingServiceBlockingStub = PingServiceGrpc.newBlockingStub(pingChannelManager.getChannel());
        PingRequest pingRequest = PingRequest.newBuilder().setGreeting("ping-grpc-single-channel").build();
        PingResponse pingResponse = pingServiceBlockingStub.ping(pingRequest);
        return responseFactory.success("ok");
    }


    @GetMapping(value = "/ping-grpc")
    public ResponseEntity<GeneralResponse<String>> getRestGrpc(String msg) {
        final ManagedChannel channel = grpcRequestFactory.createChannel(springBGrpcUrl, springBGrpcPort);
        try {
            PingServiceGrpc.PingServiceBlockingStub pingServiceBlockingStub = PingServiceGrpc.newBlockingStub(channel);
            PingRequest pingRequest = PingRequest.newBuilder().setGreeting("ping-grpc").build();
            PingResponse pingResponse = pingServiceBlockingStub.ping(pingRequest);
        } finally {
            channel.shutdown();
        }
        return responseFactory.success("ok");
    }
}
