package pl.jarekwasowski.openaikt

import org.springframework.http.HttpMethod
import pl.jarekwasowski.openaikt.model.ChatCompletionAPI
import pl.jarekwasowski.openaikt.model.ChatCompletionRequest
import pl.jarekwasowski.openaikt.model.ChatCompletionResponse
import pl.jarekwasowski.openaikt.model.CompletionAPI
import pl.jarekwasowski.openaikt.model.CompletionRequest
import pl.jarekwasowski.openaikt.model.CompletionResponse
import pl.jarekwasowski.openaikt.model.EditAPI
import pl.jarekwasowski.openaikt.model.EditRequest
import pl.jarekwasowski.openaikt.model.EditResponse
import pl.jarekwasowski.openaikt.model.EmbeddingAPI
import pl.jarekwasowski.openaikt.model.EmbeddingRequest
import pl.jarekwasowski.openaikt.model.EmbeddingResponse
import pl.jarekwasowski.openaikt.model.ImageAPI
import pl.jarekwasowski.openaikt.model.ImageGenerationRequest
import pl.jarekwasowski.openaikt.model.ImageResponse
import pl.jarekwasowski.openaikt.model.ListModelsResponse
import pl.jarekwasowski.openaikt.model.ModelData
import pl.jarekwasowski.openaikt.model.ModelsAPI
import pl.jarekwasowski.openaikt.model.ModerationAPI
import pl.jarekwasowski.openaikt.model.ModerationRequest
import pl.jarekwasowski.openaikt.model.ModerationResponse

class OpenAiClient(
    private val openApiWebClient: OpenAiWebClient,
) : ChatCompletionAPI, ModelsAPI, EditAPI, ImageAPI, EmbeddingAPI, ModerationAPI, CompletionAPI {

    override suspend fun chatCompletion(request: ChatCompletionRequest): ChatCompletionResponse =
        openApiWebClient.request(
            HttpMethod.POST,
            "/v1/chat/completions",
            request,
            ChatCompletionResponse::class.java,
        )

    override suspend fun listModels(): ListModelsResponse =
        openApiWebClient.request(
            HttpMethod.GET,
            "/v1/models",
            null,
            ListModelsResponse::class.java,
        )

    override suspend fun retrieveModel(id: String): ModelData =
        openApiWebClient.request(
            HttpMethod.GET,
            "/v1/models/$id",
            null,
            ModelData::class.java,
        )

    override suspend fun edit(request: EditRequest): EditResponse =
        openApiWebClient.request(
            HttpMethod.POST,
            "/v1/edits",
            request,
            EditResponse::class.java,
        )

    override suspend fun generateImage(request: ImageGenerationRequest): ImageResponse =
        openApiWebClient.request(
            HttpMethod.POST,
            "/v1/images/generations",
            request,
            ImageResponse::class.java,
        )

    override suspend fun getEmbedding(request: EmbeddingRequest): EmbeddingResponse =
        openApiWebClient.request(
            HttpMethod.POST,
            "/v1/embeddings",
            request,
            EmbeddingResponse::class.java,
        )

    override suspend fun moderate(request: ModerationRequest): ModerationResponse =
        openApiWebClient.request(
            HttpMethod.POST,
            "/v1/moderations",
            request,
            ModerationResponse::class.java,
        )

    override suspend fun completion(request: CompletionRequest): CompletionResponse =
        openApiWebClient.request(
            HttpMethod.POST,
            "/v1/completions",
            request,
            CompletionResponse::class.java,
        )
}
