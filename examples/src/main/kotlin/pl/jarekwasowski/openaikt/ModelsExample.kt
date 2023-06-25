package pl.jarekwasowski.openaikt

import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))

        val models = openAiClient.listModels()

        println(models)
    }
}
