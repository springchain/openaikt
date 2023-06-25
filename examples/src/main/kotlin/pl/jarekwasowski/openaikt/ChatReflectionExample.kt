package pl.jarekwasowski.openaikt

import kotlinx.coroutines.runBlocking
import pl.jarekwasowski.openaikt.model.ChatCompletionRequest
import pl.jarekwasowski.openaikt.model.ChatMessage
import pl.jarekwasowski.openaikt.model.MessageRole.SYSTEM
import pl.jarekwasowski.openaikt.model.MessageRole.USER

fun main() {
    runBlocking {
        val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))

        val chatRequest = ChatCompletionRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                ChatMessage(SYSTEM, "Suggest 3 strategies on how to sell AI in SMEs. "),
            ),
        )
        var response = openAiClient.chatCompletion(chatRequest)

        println(response)

        chatRequest.messages += response.chatChoices[0].chatMessage
        chatRequest.messages += ChatMessage(USER, "Check your answer is correct. ")

        response = openAiClient.chatCompletion(chatRequest)

        println(response)
    }
}
