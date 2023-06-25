import kotlinx.coroutines.runBlocking
import pl.jarekwasowski.openaikt.OpenAiClient
import pl.jarekwasowski.openaikt.OpenAiWebClient
import pl.jarekwasowski.openaikt.model.EditRequest

fun main() {
    runBlocking {
        val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))

        val chatRequest = EditRequest(
            model = "text-davinci-edit-001",
            input = "We is going to the store.",
            instruction = "Fix the grammar.",
        )

        val response = openAiClient.edit(chatRequest)

        println(response)
    }
}
