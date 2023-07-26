package ai.springchain.model.openai.model

import ai.springchain.model.openai.model.ImageResponseFormat.URL
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

enum class ImageSize(val value: String) {
    S256("256x256"),
    S512("512x512"),
    S1024("1024x1024"),
    ;

    @JsonValue
    override fun toString(): String {
        return value
    }
}

enum class ImageResponseFormat(val value: String) {
    URL("url"),
    B64JSON("b64_json"),
    ;

    @JsonValue
    override fun toString(): String {
        return value
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ImageGenerationRequest(
    @JsonProperty("prompt")
    val prompt: String,
    @JsonProperty("n")
    val n: Int,
    @JsonProperty("size")
    val size: ImageSize,
    @JsonProperty("response_format")
    val responseFormat: ImageResponseFormat? = URL,
    @JsonProperty("user")
    val user: String? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ImageResponse(
    @JsonProperty("created")
    val created: Long,
    @JsonProperty("data")
    val data: List<ImageData>,
) : OpenAiResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ImageData(
    @JsonProperty("url")
    val url: String,
)

interface ImageAPI {
    suspend fun generateImage(request: ImageGenerationRequest): ImageResponse
}
