package br.com.zup.pix

import br.com.zup.pix.GrpcKeyManagerServiceGrpc.GrpcKeyManagerServiceImplBase
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
class KeyManagerEndpoint : GrpcKeyManagerServiceImplBase() {
    override fun store(request: GrpcKeyManagerRequest, responseObserver: StreamObserver<GrpcKeyManagerReply>) {
        println(request.toString())
    }
}