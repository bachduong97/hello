package vn.com.viettel.vds.benchmark.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import vn.com.viettel.vds.benchmark.proto.PingRequest;
import vn.com.viettel.vds.benchmark.proto.PingResponse;
import vn.com.viettel.vds.benchmark.proto.PingServiceGrpc;

@GRpcService
@Slf4j
public class PingGRPCServerController extends PingServiceGrpc.PingServiceImplBase {
    @Override
    public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
        PingResponse response = PingResponse.newBuilder().setReply("Hihi em chào anh ạ: " + request.getGreeting()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.info("grpc server");
    }
}
