package br.com.zup.pix.model

import br.com.zup.pix.AccountType
import br.com.zup.pix.KeyType
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Pix(
    // @ValidUUID
    @field:NotNull
    @Column(nullable = false)
    val clientId: UUID,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: KeyType?,

    @field:NotBlank
    @Column(unique = true, nullable = false)
    val key: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val typeAccount: AccountType?,
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    override fun toString(): String {
        return "Pix(clientId='$clientId', type=$type, key='$key', typeAccount=$typeAccount, id=$id)"
    }
}