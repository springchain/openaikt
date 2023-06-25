package pl.jarekwasowski.openaikt.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CompletionRequest(
    @JsonProperty("model")
    val model: String,
    @JsonProperty("prompt")
    val prompt: List<String>,
    @JsonProperty("max_tokens")
    val maxTokens: Int? = null,
    @JsonProperty("temperature")
    val temperature: Double? = null,
    @JsonProperty("top_p")
    val topP: Double? = null,
    @JsonProperty("n")
    val n: Int? = null,
    @JsonProperty("stream")
    val stream: Boolean? = null,
    @JsonProperty("logprobs")
    val logprobs: Int? = null,
    @JsonProperty("stop")
    val stop: List<String>? = null,
    @JsonProperty("presence_penalty")
    val presencePenalty: Double? = null,
    @JsonProperty("frequency_penalty")
    val frequencyPenalty: Double? = null,
    @JsonProperty("best_of")
    val bestOf: Int? = null,
    @JsonProperty("logit_bias")
    val logitBias: Map<String, Double>? = null,
    @JsonProperty("user")
    val user: String? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CompletionResponse(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("object")
    val objectName: String,
    @JsonProperty("created")
    val created: Long,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("choices")
    val choices: List<CompletionChoice>,
    @JsonProperty("usage")
    val usage: CompletionUsage,
) : OpenAiResponse

data class CompletionChoice(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("index")
    val index: Int,
    @JsonProperty("logprobs")
    val logprobs: Any?, // This can be various types, so we declare it as Any.
    @JsonProperty("finish_reason")
    val finishReason: String,
)

data class CompletionUsage(
    @JsonProperty("prompt_tokens")
    val promptTokens: Int,
    @JsonProperty("completion_tokens")
    val completionTokens: Int,
    @JsonProperty("total_tokens")
    val totalTokens: Int,
)

interface CompletionAPI {
    suspend fun completion(request: CompletionRequest): CompletionResponse
}
