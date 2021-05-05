package br.com.zup.pix

import br.com.zup.pix.AccountType.UNKNOWN_ACCOUNT_TYPE
import br.com.zup.pix.GrpcKeyManagerServiceGrpc.GrpcKeyManagerServiceImplBase
import br.com.zup.pix.KeyType.UNKNOWN_KEY_TYPE
import br.com.zup.pix.model.Pix
import br.com.zup.pix.request.PixRequest
import br.com.zup.pix.service.PixService
import com.google.protobuf.Timestamp
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyManagerEndpoint(
    @Inject val pixService: PixService,
) : GrpcKeyManagerServiceImplBase() {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun store(request: GrpcKeyManagerRequest, responseObserver: StreamObserver<GrpcKeyManagerResponse>) {


        try {
            logger.info("Convertendo para novo regitro dto")
            val pixRequest: PixRequest = request.toPixModel();
            println(pixRequest.toString())
            // chama service
            val pix: Pix = pixService.store(pixRequest)



            logger.info("Iniciando processo de gravação de nova chave no sistema interno")
            logger.info("Gravando dados no banco")

            logger.info("Iniciando retorno para blomRPC")
            // println(GrpcKeyManagerResponse.newBuilder())
            responseObserver.onNext(
                GrpcKeyManagerResponse
                    .newBuilder()
                    .setPixId(pix.id.toString())
                    .setPixKey(pix.key)
                    .setCreatedAt(
                        LocalDateTime.now().let {
                            val instant = it.atZone(ZoneId.of("UTC")).toInstant()
                            Timestamp.newBuilder()
                                .setSeconds(instant.epochSecond)
                                .setNanos(instant.nano)
                                .build()
                        }).build()
            )
            responseObserver.onCompleted()
        } catch (e: ConstraintViolationException) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("invalid parameter(s)")
                    .withCause(e)
                    .asRuntimeException()
            )
            return
        }


    }
}

private fun GrpcKeyManagerRequest.toPixModel(): PixRequest {
    return PixRequest(
        clientId = clientId,
        type = when (keyType) {
            UNKNOWN_KEY_TYPE -> null
            else -> KeyType.valueOf(keyType.name)
        },
        key = keyValue,
        typeAccount = when (typeAccount) {
            UNKNOWN_ACCOUNT_TYPE -> null
            else -> AccountType.valueOf(typeAccount.name)
        }
    )
}

//fun GrpcKeyManagerRequest.valida() {
//    println(this)
//}