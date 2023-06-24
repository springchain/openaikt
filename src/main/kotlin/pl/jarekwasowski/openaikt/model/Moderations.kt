package pl.jarekwasowski.openaikt.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ModerationRequest(
    @JsonProperty("input")
    val input: String,
    @JsonProperty("model")
    val model: String? = "text-moderation-latest"
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ModerationResponse(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("results")
    val results: List<Result>
) : OpenAiResponse

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Result(
    @JsonProperty("categories")
    val categories: Categories,
    @JsonProperty("category_scores")
    val categoryScores: CategoryScores,
    @JsonProperty("flagged")
    val flagged: Boolean
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Categories(
    @JsonProperty("hate")
    val hate: Boolean,
    @JsonProperty("hate/threatening")
    val hateThreatening: Boolean,
    @JsonProperty("self-harm")
    val selfHarm: Boolean,
    @JsonProperty("sexual")
    val sexual: Boolean,
    @JsonProperty("sexual/minors")
    val sexualMinors: Boolean,
    @JsonProperty("violence")
    val violence: Boolean,
    @JsonProperty("violence/graphic")
    val violenceGraphic: Boolean
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CategoryScores(
    @JsonProperty("hate")
    val hate: Double,
    @JsonProperty("hate/threatening")
    val hateThreatening: Double,
    @JsonProperty("self-harm")
    val selfHarm: Double,
    @JsonProperty("sexual")
    val sexual: Double,
    @JsonProperty("sexual/minors")
    val sexualMinors: Double,
    @JsonProperty("violence")
    val violence: Double,
    @JsonProperty("violence/graphic")
    val violenceGraphic: Double
)

interface ModerationAPI {
    suspend fun moderate(request: ModerationRequest): ModerationResponse
}