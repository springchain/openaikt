package ai.springchain.model.openai

import ai.springchain.model.openai.model.EditRequest

class EditFixture {

    companion object {

        const val MODEL = "text-davinci-edit-001"
        const val INPUT = "What day of the wek is it?"
        const val INSTRUCTION = "Fix the spelling mistakes"
        const val TEXT = "What day of the week is it?"

        fun createEditRequest(
            model: String = MODEL,
            input: String = INPUT,
            instruction: String = INSTRUCTION,
        ): EditRequest {
            return EditRequest(
                model = model,
                input = input,
                instruction = instruction,
            )
        }
    }
}
