package pl.jarekwasowski.openaikt

import pl.jarekwasowski.openaikt.model.EmbeddingRequest

class EmbeddingFixture {

    companion object {

        const val MODEL = "text-embedding-ada-002"
        const val INPUT = "The food was delicious and the waiter..."

        fun createEmbeddingRequest(
            input: String = INPUT,
            model: String = MODEL,
        ): EmbeddingRequest {
            return EmbeddingRequest(
                input = input,
                model = model,
            )
        }
    }
}
