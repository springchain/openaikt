package pl.jarekwasowski.openaikt

import kotlinx.coroutines.runBlocking
import pl.jarekwasowski.openaikt.model.EmbeddingRequest

fun main() {
    runBlocking {
        val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))

        val embeddingRequest = EmbeddingRequest(
            model = "text-embedding-ada-002",
            input = "We is going to the store.",
        )

        val response = openAiClient.getEmbedding(embeddingRequest)
        println(response)
    }
}
