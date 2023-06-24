package pl.jarekwasowski.openaikt.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmbeddingRequest(
    @JsonProperty("input")
    val input: String,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("user")
    val user: String? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmbeddingResponse(
    @JsonProperty("object")
    val objectName: String,
    @JsonProperty("data")
    val data: List<EmbeddingData>,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("usage")
    val usage: Usage,
) : OpenAiResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmbeddingData(
    @JsonProperty("object")
    val objectName: String,
    @JsonProperty("embedding")
    val embedding: List<Float>,
    @JsonProperty("index")
    val index: Int,
)

interface EmbeddingAPI {
    suspend fun getEmbedding(request: EmbeddingRequest): EmbeddingResponse
}
