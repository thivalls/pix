package br.com.zup.pix.model

import br.com.zup.pix.AccountType
import br.com.zup.pix.KeyType
import br.com.zup.pix.ValidUUID
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Pix(
    // @ValidUUID
    @field:NotBlank
    val clientId: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val type: KeyType?,

    @field:NotBlank
    @field:Size(max = 77)
    val key: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val typeAccount: AccountType?,

    @field:NotNull
    val createdAt: LocalDateTime
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    override fun toString(): String {
        return "Pix(clientId='$clientId', type=$type, key='$key', typeAccount=$typeAccount, createdAt=$createdAt, id=$id)"
    }
}