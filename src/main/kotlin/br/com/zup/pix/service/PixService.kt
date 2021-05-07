package br.com.zup.pix.service

import br.com.zup.pix.GrpcKeyManagerResponse
import br.com.zup.pix.repository.PixRepository
import br.com.zup.pix.client.ItauClient
import br.com.zup.pix.client.ItauExternalApiResponse
import br.com.zup.pix.model.Pix
import br.com.zup.pix.request.PixRequest
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@Validated
class PixService(
    @Inject val itauClient: ItauClient,
    @Inject val pixRepository: PixRepository
) {
    val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun store(@Valid pixRequest: PixRequest, response: StreamObserver<GrpcKeyManagerResponse>): Pix? {
        logger.info("START SERVICE")
        logger.info("Buscando usuário por id na api do ITAU $pixRequest.clientId")
        try {
            val existClient: ItauExternalApiResponse = itauClient.findByClientId(pixRequest.clientId);
            if(existClient == null) {
                logger.info("Usuário INEXISTENTE")
                throw IllegalStateException("Client not found in Itau service")
            }
            logger.info("Usuário encontrado")

            logger.info("Verificando se chave já existe")
            val existentKey: Optional<Pix> = pixRepository.findByKey(pixRequest.key.toString())
            if(existentKey.isPresent) {
                logger.info("Chave existente")
                throw IllegalStateException("The key has been already created")
            }


            logger.info("Inicianco criacao de entidade")
            val pixToBeSaved: Pix = pixRequest.toPix()
            println("********************* " + pixToBeSaved.toString())
            logger.info("Entidade pronta para salvar")

            return pixRepository.save(pixToBeSaved)
        } catch (e: Exception) {
            response.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("Cliente inexistente ou chave já cadastrada")
                    .withCause(e)
                    .asRuntimeException()
            )
            // throw java.lang.IllegalStateException("No pix founded -> ?????? " + e.message)
        }

        return null
    }
}