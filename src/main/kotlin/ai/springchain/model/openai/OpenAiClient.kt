package ai.springchain.model.openai

import org.springframework.http.HttpMethod
import ai.springchain.model.openai.model.ChatCompletionAPI
import ai.springchain.model.openai.model.ChatCompletionRequest
import ai.springchain.model.openai.model.ChatCompletionResponse
import ai.springchain.model.openai.model.CompletionAPI
import ai.springchain.model.openai.model.CompletionRequest
import ai.springchain.model.openai.model.CompletionResponse
import ai.springchain.model.openai.model.EditAPI
import ai.springchain.model.openai.model.EditRequest
import ai.springchain.model.openai.model.EditResponse
import ai.springchain.model.openai.model.EmbeddingAPI
import ai.springchain.model.openai.model.EmbeddingRequest
import ai.springchain.model.openai.model.EmbeddingResponse
import ai.springchain.model.openai.model.ImageAPI
import ai.springchain.model.openai.model.ImageGenerationRequest
import ai.springchain.model.openai.model.ImageResponse
import ai.springchain.model.openai.model.ListModelsResponse
import ai.springchain.model.openai.model.ModelData
import ai.springchain.model.openai.model.ModelsAPI
import ai.springchain.model.openai.model.ModerationAPI
import ai.springchain.model.openai.model.ModerationRequest
import ai.springchain.model.openai.model.ModerationResponse

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
