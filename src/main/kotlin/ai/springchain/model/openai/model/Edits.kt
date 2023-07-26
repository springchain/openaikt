package ai.springchain.model.openai.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class EditRequest(
    @JsonProperty("model")
    val model: String,
    @JsonProperty("input")
    val input: String,
    @JsonProperty("instruction")
    val instruction: String,
    @JsonProperty("temperature")
    val temperature: Double? = 1.0,
    @JsonProperty("top_p")
    val topP: Double? = 1.0,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EditResponse(
    @JsonProperty("object")
    val objectName: String,
    @JsonProperty("created")
    val created: Long,
    @JsonProperty("choices")
    val choices: List<EditChoice>,
    @JsonProperty("usage")
    val usage: Usage,
) : OpenAiResponse

data class EditChoice(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("index")
    val index: Int,
)

interface EditAPI {
    suspend fun edit(request: EditRequest): EditResponse
}
