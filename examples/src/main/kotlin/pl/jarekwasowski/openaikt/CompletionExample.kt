import kotlinx.coroutines.runBlocking
import pl.jarekwasowski.openaikt.OpenAiClient
import pl.jarekwasowski.openaikt.OpenAiWebClient
import pl.jarekwasowski.openaikt.model.CompletionRequest

fun main() {
    runBlocking {
        val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))

        val completionRequest = CompletionRequest(
            model = "text-davinci-003",
            prompt = listOf("Hello, how are you?"),
        )
        val response = openAiClient.completion(completionRequest)

        println(response)
    }
}
