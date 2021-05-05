package br.com.zup.pix.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:9091/api/v1/clientes")
interface ItauClient {
    @Get("/{clienteId}")
    fun findByClientId(@PathVariable(value = "clienteId") clientId:String): ItauExternalApiResponse
}