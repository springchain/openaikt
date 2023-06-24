package pl.jarekwasowski.openaikt

import pl.jarekwasowski.openaikt.model.ImageGenerationRequest
import pl.jarekwasowski.openaikt.model.ImageSize

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
