package pl.jarekwasowski.openaikt

import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec
import org.springframework.web.reactive.function.client.bodyToMono
import pl.jarekwasowski.openaikt.model.ErrorResponse
import pl.jarekwasowski.openaikt.model.OpenAiResponse
import reactor.core.publisher.Mono

/**
 * OpenAiWebClient class handles the API interactions with OpenAI's services.
 *
 * @param openAiApiKey - The secret key used for OpenAI API authentication.
 */
@Suppress("DEPRECATION")
class OpenAiWebClient(
    val openAiApiKey: String,
) {

    var webClient: WebClient

    private val logger = KotlinLogging.logger { }

    init {
        webClient = WebClient
            .builder()
            .baseUrl("https://api.openai.com")
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", "Bearer $openAiApiKey")
            .build()
    }

    /**
     * Handles the building and execution of the web request to the OpenAI API.
     *
     * @param method The HTTP method of the request.
     * @param path The endpoint path.
     * @param requestData The data to be sent along with the request.
     * @param responseType The type of response expected.
     * @return The response from the OpenAI API.
     */
    suspend fun <T : OpenAiResponse> request(
        method: HttpMethod,
        path: String,
        requestData: Any? = null,
        responseType: Class<T>,
    ): T {
        logger.debug { "Making request method=$method path=$path requestData=$requestData" }

        val spec = when (method) {
            HttpMethod.GET -> webClient.get()
            HttpMethod.POST -> webClient.post()
            HttpMethod.PUT -> webClient.put()
            HttpMethod.DELETE -> webClient.delete()
            HttpMethod.HEAD -> webClient.head()
            HttpMethod.PATCH -> webClient.patch()
            HttpMethod.OPTIONS -> webClient.options()
            else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
        }.uri(path)

        val bodySpec = if (requestData != null && method in listOf(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH)) {
            (spec as RequestBodyUriSpec)
                .body(Mono.just(requestData), requestData::class.java)
        } else {
            spec
        }

        return bodySpec
            .exchange()
            .flatMap { clientResponse ->
                if (clientResponse.statusCode().isError) {
                    clientResponse.bodyToMono<ErrorResponse>()
                        .flatMap { errorResponse ->
                            logger.error { "Received error response: $errorResponse" }
                            Mono.error(errorResponse.toException())
                        }
                } else {
                    clientResponse.bodyToMono(responseType)
                }
            }
            .awaitSingle()
    }
}
