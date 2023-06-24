package pl.jarekwasowski.openaikt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ListModelsResponse(
    @JsonProperty("object")
    val objectType: String,
    @JsonProperty("data")
    val data: List<ModelData>,
) : OpenAiResponse

data class ModelData(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("object")
    val objectName: String,
    @JsonProperty("owned_by")
    val ownedBy: String,
    @JsonProperty("permission")
    val permission: List<ModelPermission>,
) : OpenAiResponse

data class ModelPermission(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("object")
    val objectName: String,
    @JsonProperty("allow_create_engine")
    val allowCreateEngine: Boolean,
    @JsonProperty("allow_sampling")
    val allowSampling: Boolean,
    @JsonProperty("allow_logprobs")
    val allowLogprobs: Boolean,
    @JsonProperty("allow_search_indices")
    val allowSearchIndices: Boolean,
    @JsonProperty("allow_view")
    val allowView: Boolean,
    @JsonProperty("allow_fine_tuning")
    val allowFineTuning: Boolean,
    @JsonProperty("organization")
    val organization: String,
    @JsonProperty("group")
    val group: String?,
    @JsonProperty("is_blocking")
    val isBlocking: Boolean,
)

interface ModelsAPI {
    suspend fun listModels(): ListModelsResponse
    suspend fun retrieveModel(id: String): ModelData
}
