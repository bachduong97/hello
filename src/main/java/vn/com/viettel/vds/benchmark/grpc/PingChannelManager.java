package vn.com.viettel.vds.benchmark.grpc;

import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.com.viettel.vds.arch.factory.request.GrpcRequestFactory;

@Component
public class PingChannelManager {
    private GrpcRequestFactory grpcRequestFactory;

    @Value("${partner.spring-b.grpc.url}")
    private String springBGrpcUrl;

    @Value("${partner.spring-b.grpc.port}")
    private int springBGrpcPort;
    private static ManagedChannel channel;

    private PingChannelManager(GrpcRequestFactory grpcRequestFactory) {
        this.grpcRequestFactory = grpcRequestFactory;
    }

    public ManagedChannel getChannel() {
        if (channel == null) {
            synchronized (PingChannelManager.class) {
                if (channel == null) {
                    channel = grpcRequestFactory.createChannel(springBGrpcUrl, springBGrpcPort);
                }
            }
        }
        return channel;
    }
}
