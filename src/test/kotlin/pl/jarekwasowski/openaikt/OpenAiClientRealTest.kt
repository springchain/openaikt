package pl.jarekwasowski.openaikt

import io.kotest.common.runBlocking
import io.kotest.core.annotation.EnabledIf
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldInclude

@EnabledIf(EnabledIfEnvironmentVariable::class)
class OpenAiClientRealTest : StringSpec() {

    private val openApiWebClient = OpenAiWebClient(TestProperties.OPENAI_API_KEY.value)
    private val openAiClient = OpenAiClient(openApiWebClient)

    init {
        "chat API test" {
            runBlocking {

                val request = ChatFixture.createCreateChatCompletionRequest()

                val response = openAiClient.createChatCompletion(request)

                response.choices.size shouldBe ChatFixture.N
                response.choices[0].message.content shouldInclude ChatFixture.NAME
                response.choices[0].message.content shouldInclude "${ChatFixture.LONGITUDE}"
                response.choices[0].message.content shouldInclude "${ChatFixture.LATITUDE}"
                response.choices[0].message.functionCall shouldBe null
            }
        }

        "list models test" {
            runBlocking {

                val response = openAiClient.listModels()

                response.objectType shouldBe "list"
                response.data.size shouldBeGreaterThan 1
            }
        }

        "test retrieve Model information" {
            runBlocking {
                val response = openAiClient.retrieveModel("davinci")

                response.id shouldBe "davinci"
            }
        }

        "test edit" {
            runBlocking {
                val response = openAiClient.edit(EditFixture.createEditRequest())

                response.choices[0].text shouldInclude EditFixture.TEXT
            }
        }

        "test generateImage" {
            runBlocking {
                val response = openAiClient.generateImage(ImageFixture.createImageGenerationRequest())

                println(response)

                response.data[0].url shouldInclude "https://"
            }
        }

        "test getEmbedding" {
            runBlocking {
                val response = openAiClient.getEmbedding(EmbeddingFixture.createEmbeddingRequest())
                response.data[0].embedding.size shouldBeGreaterThan 1
            }
        }

        "test moderate" {
            runBlocking {
                val response = openAiClient.moderate(ModerationFixture.createModerationRequest())

                response.results[0].flagged shouldBe true
            }
        }
    }
}
