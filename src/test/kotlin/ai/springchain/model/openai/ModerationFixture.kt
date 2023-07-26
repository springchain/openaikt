package ai.springchain.model.openai

import ai.springchain.model.openai.model.ModerationRequest

class ModerationFixture {

    companion object {

        const val INPUT = "I want to kill them."
        const val MODEL = "text-moderation-latest"

        fun createModerationRequest(
            input: String = INPUT,
            model: String? = MODEL,
        ): ModerationRequest {
            return ModerationRequest(
                input = input,
                model = model,
            )
        }
    }
}
