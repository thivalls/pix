package br.com.zup.pix.request

import br.com.zup.pix.AccountType
import br.com.zup.pix.KeyType
import br.com.zup.pix.model.Pix
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
class PixRequest(
        //@ValidUUID
    @field:NotBlank
    val clientId: String,

    @field:NotNull
    val type: KeyType?,

    @field:NotBlank
    @field:Size(max = 77)
    val key: String,

    @field:NotNull
    val typeAccount: AccountType?
    ) {

    fun toPix() : Pix {
        return Pix(
            clientId = clientId,
            type = type,
            key = key,
            typeAccount = typeAccount,
            createdAt = LocalDateTime.now(),
        )
    }

    override fun toString(): String {
        return "PixRequest(clientId='$clientId', type=$type, key='$key', typeAccount=$typeAccount)"
    }


}