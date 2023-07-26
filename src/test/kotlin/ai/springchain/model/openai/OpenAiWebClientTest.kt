package ai.springchain.model.openai

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import ai.springchain.model.openai.model.ErrorResponse
import ai.springchain.model.openai.model.OpenAiClientException
import ai.springchain.model.openai.model.OpenAiResponse
import reactor.core.publisher.Mono

@Suppress("DEPRECATION")
class OpenAiWebClientTest : StringSpec() {

    private val mockResponse: OpenAiResponse = mockk()
    private val mockErrorResponse: ErrorResponse = mockk()

    private val mockWebClient: WebClient = mockk()
    private val requestHeadersUriSpec: WebClient.RequestHeadersUriSpec<*> = mockk()
    private val requestBodyUriSpec: WebClient.RequestBodyUriSpec = mockk()
    private val mockClientResponse: ClientResponse = mockk()
    private val openAiClientException = mockk<OpenAiClientException>()
    private val openAiWebClient = OpenAiWebClient("testApiKey").apply {
        webClient = mockWebClient
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        clearAllMocks()
    }

    override suspend fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        setupMocks()
    }

    private fun setupMocks() {
        every { mockErrorResponse.toException() } returns openAiClientException
//        every { openAiClientException.getCause() } returns null
        every { mockWebClient.get() } returns requestHeadersUriSpec
        every { mockWebClient.post() } returns requestBodyUriSpec
        every { requestBodyUriSpec.uri(any<String>()) } returns requestBodyUriSpec
        every { requestHeadersUriSpec.uri(any<String>()) } returns requestHeadersUriSpec
        every { requestBodyUriSpec.body(any()) } returns requestBodyUriSpec
        every { requestBodyUriSpec.body(any<Mono<*>>(), any<Class<*>>()) } returns requestBodyUriSpec
        every { requestBodyUriSpec.exchange() } returns Mono.just(mockClientResponse)
        every { requestHeadersUriSpec.exchange() } returns Mono.just(mockClientResponse)
        every { mockClientResponse.bodyToMono(OpenAiResponse::class.java) } returns Mono.just(mockResponse)
//        every { mockClientResponse.bodyToMono(ErrorResponse::class.java) } returns Mono.just(mockErrorResponse)
        every { mockClientResponse.statusCode() } returns HttpStatus.OK
        every { mockClientResponse.bodyToMono(any<ParameterizedTypeReference<*>>()) } answers {
            when (firstArg<ParameterizedTypeReference<*>>().type) {
                ErrorResponse::class.java -> Mono.just(mockErrorResponse)
                OpenAiResponse::class.java -> Mono.just(mockResponse)
                else -> Mono.empty()
            }
        }
    }

    init {
        "request should make GET request and return successful response" {
            runBlocking {
                val response = openAiWebClient.request(HttpMethod.GET, "/test", null, OpenAiResponse::class.java)
                response shouldBe mockResponse
            }
            coVerify(exactly = 1) { mockWebClient.get() }
            coVerify(exactly = 1) { requestHeadersUriSpec.uri("/test") }
        }

        "request should make POST request and return successful response" {
            runBlocking {
                val response =
                    openAiWebClient.request(HttpMethod.POST, "/test", mockResponse, OpenAiResponse::class.java)
                response shouldBe mockResponse
            }
            coVerify(exactly = 1) { mockWebClient.post() }
            coVerify(exactly = 1) { requestBodyUriSpec.uri("/test") }
        }

//        "request should handle error status" {
//            every { mockClientResponse.statusCode() } returns HttpStatus.BAD_REQUEST
//
//            shouldThrowExactly<OpenAiClientException> {
//                runBlocking {
//                    openAiWebClient.request(HttpMethod.GET, "/error", null, OpenAiResponse::class.java)
//                }
//            }
//
//            coVerify(exactly = 1) { mockWebClient.get() }
//            coVerify(exactly = 1) { requestHeadersUriSpec.uri("/error") }
//        }
    }
}
