package br.com.zup.pix.repository

import br.com.zup.pix.model.Pix
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface PixRepository : JpaRepository<Pix, Long> {
    fun findByKey(key: String): Optional<Pix>
}