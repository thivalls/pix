package br.com.zup.pix.client

data class ItauExternalApiResponse (
    val tipo: String? = null,
    val instituicao: InstituicaoResponse? = null,
    val agencia: String? = null,
    val numero: String? = null,
    val titular: TitularResponse? = null,
)