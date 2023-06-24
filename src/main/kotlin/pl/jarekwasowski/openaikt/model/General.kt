package pl.jarekwasowski.openaikt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Usage(
    @JsonProperty("prompt_tokens")
    val promptTokens: Int,
    @JsonProperty("total_tokens")
    val totalTokens: Int,
)

interface OpenAiResponse

class ErrorResponse(
    @JsonProperty("error")
    val error: OpenAiError,
) : OpenAiResponse {

    fun toException(): OpenAiClientException {
        return OpenAiClientException(
            message = error.message,
            type = error.type,
            param = error.param,
            code = error.code,
        )
    }

    override fun toString(): String {
        return "ErrorResponse(error=$error)"
    }
}

class OpenAiError(
    @JsonProperty("message")
    val message: String,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("param")
    val param: String?,
    @JsonProperty("code")
    val code: String?,
) {
    override fun toString(): String {
        return "OpenAiError(message=$message, type=$type, param=$param, code=$code)"
    }
}

data class OpenAiClientException(
    override val message: String,
    val type: String,
    val param: String?,
    val code: String?,
) : Exception(message)
