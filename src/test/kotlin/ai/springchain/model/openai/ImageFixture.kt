package ai.springchain.model.openai

import ai.springchain.model.openai.model.ImageGenerationRequest
import ai.springchain.model.openai.model.ImageSize

class ImageFixture {

    companion object {

        const val PROMPT = "Dog rides his bike over the rainbow"
        const val N = 1
        val SIZE = ImageSize.S256

        fun createImageGenerationRequest(
            prompt: String = PROMPT,
            n: Int = N,
            size: ImageSize = SIZE,
        ): ImageGenerationRequest {
            return ImageGenerationRequest(
                prompt = prompt,
                n = n,
                size = size,
            )
        }
    }
}
