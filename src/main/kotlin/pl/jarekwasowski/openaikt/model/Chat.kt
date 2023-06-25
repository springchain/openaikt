package pl.jarekwasowski.openaikt.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.databind.annotation.JsonSerialize

interface FunctionCall
data class FunctionCallStatic(val name: String) : FunctionCall

enum class FunctionCallType(private val value: String) : FunctionCall {
    NONE_TYPE("none"), AUTO_TYPE("auto");

    @JsonValue
    override fun toString(): String {
        return value
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FunctionParameterProperties(
    val type: String,
    val description: String,
    val enum: List<String>? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FunctionParameters(
    val type: String,
    val properties: Map<String, FunctionParameterProperties>,
    val required: List<String>? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Function(
    val name: String,
    val description: String,
    val parameters: FunctionParameters,
)

enum class MessageRole(private val value: String) {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    FUNCTION("function"),
    ;

    @JsonValue
    override fun toString(): String {
        return value
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MessageFunctionCall(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("arguments")
    @JsonDeserialize(using = ArgumentsDeserializer::class)
    @JsonSerialize(using = ArgumentsSerializer::class)
    val arguments: Map<String, String>,
) {
    class ArgumentsDeserializer : JsonDeserializer<Map<String, String>>() {
        override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Map<String, String> {
            val mapper = ObjectMapper()
            val node = jp.codec.readTree<JsonNode>(jp)
            @Suppress("UNCHECKED_CAST")
            return mapper.readValue(node.asText(), Map::class.java) as Map<String, String>
        }
    }
    class ArgumentsSerializer : JsonSerializer<Map<String, String>>() {
        private val mapper = ObjectMapper()

        override fun serialize(value: Map<String, String>, gen: JsonGenerator, serializers: SerializerProvider) {
            val jsonString = mapper.writeValueAsString(value)
            gen.writeString(jsonString)
        }
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ChatMessage(
    @JsonProperty("role")
    val role: MessageRole,
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonProperty("content")
    val content: String? = null,
    @JsonProperty("function_call")
    val functionCall: MessageFunctionCall? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionRequest(
    val model: String,
    var messages: List<ChatMessage>,
    val functions: List<Function>? = null,
    @JsonProperty("function_calls")
    val functionCall: FunctionCall? = null,
    val temperature: Double? = 1.0,
    @JsonProperty("top_p")
    val topP: Double? = 1.0,
    val n: Int? = 1,
    val stream: Boolean? = false,
    val stop: List<String>? = null,
    @JsonProperty("max_tokens")
    val maxTokens: Int? = null,
    @JsonProperty("presence_penalty")
    val presencePenalty: Double? = 0.0,
    @JsonProperty("frequency_penalty")
    val frequencyPenalty: Double? = 0.0,
    @JsonProperty("logit_bias")
    val logitBias: Map<String, Double>? = null,
    @JsonProperty("user")
    val user: String? = null,
)

data class ChatCompletionResponse(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("object")
    val objectType: String,
    @JsonProperty("created")
    val created: Long,
    @JsonProperty("choices")
    val chatChoices: List<ChatChoice>,
    @JsonProperty("usage")
    val usage: Usage,
) : OpenAiResponse

data class ChatChoice(
    @JsonProperty("index")
    val index: Int,
    @JsonProperty("message")
    val chatMessage: ChatMessage,
    @JsonProperty("finish_reason")
    val finishReason: String,
)

interface ChatCompletionAPI {
    suspend fun chatCompletion(request: ChatCompletionRequest): ChatCompletionResponse
}
