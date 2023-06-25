package pl.jarekwasowski.openaikt

import pl.jarekwasowski.openaikt.model.CompletionRequest

class CompletionFixture {

    companion object {

        const val MODEL = "text-davinci-003"
        val PROMPT = listOf("Translate this text to French:", "Hello, how are you?")
        const val MAX_TOKENS = 60
        const val TEMPERATURE = 0.8
        const val N = 1
        val STOP = listOf("###")

        fun createCompletionRequest(
            model: String = MODEL,
            prompt: List<String> = PROMPT,
            maxTokens: Int = MAX_TOKENS,
            temperature: Double = TEMPERATURE,
            n: Int = N,
            stop: List<String> = STOP,
        ): CompletionRequest {
            return CompletionRequest(
                model = model,
                prompt = prompt,
                maxTokens = maxTokens,
                temperature = temperature,
                n = n,
                stop = stop,
            )
        }
    }
}
