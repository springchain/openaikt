package pl.jarekwasowski.openaikt

import pl.jarekwasowski.openaikt.model.ChatCompletionRequest
import pl.jarekwasowski.openaikt.model.ChatMessage
import pl.jarekwasowski.openaikt.model.Function
import pl.jarekwasowski.openaikt.model.FunctionCall
import pl.jarekwasowski.openaikt.model.FunctionParameterProperties
import pl.jarekwasowski.openaikt.model.FunctionParameters
import pl.jarekwasowski.openaikt.model.MessageFunctionCall
import pl.jarekwasowski.openaikt.model.MessageRole
import pl.jarekwasowski.openaikt.model.MessageRole.ASSISTANT
import pl.jarekwasowski.openaikt.model.MessageRole.SYSTEM
import pl.jarekwasowski.openaikt.model.MessageRole.USER

class ChatFixture {

    companion object {

        const val MODEL = "gpt-3.5-turbo-0613"
        const val CONTENT = "Hello, AI"
        const val DESCRIPTION = "A test function"
        const val TYPE = "String"
        const val FUNCTION_PARAMETER_NAME = "param"

        const val LATITUDE = 52.237049
        const val LONGITUDE = 21.017532
        const val NAME = "Jarek Wasowski"

        const val SYSTEM_MESSAGE = "Help me!"
        const val USER_MESSAGE = "where is user with id 11"
        const val FUNCTION_NAME = "get_user_current_location"
        const val USER_ID_PARAM = "11"
        const val USER_JSON = "{\n  \"name\": \"$NAME\",\n  \"latitude\": $LATITUDE,\n  \"longitude\": $LONGITUDE\n}"
        const val FUNCTION_DESCRIPTION = "Get user current location"
        const val OBJECT_TYPE = "object"
        const val USER_ID_KEY = "user_id"
        const val FUNCTION_PARAM_PROPERTIES_TYPE = "string"
        const val FUNCTION_PARAM_PROPERTIES_DESCRIPTION = "User id"
        const val TEMPERATURE = 1.0
        const val TOP_P = 1.0
        const val N = 1
        const val STREAM = false
        const val STOP_KEYWORD = "STOP"
        const val MAX_TOKENS = 400
        const val PRESENCE_PENALTY = 0.0
        const val FREQUENCY_PENALTY = 0.0
        const val USER_ID = "user_123"

        fun createCreateChatCompletionRequest(
            model: String = MODEL,
            chatMessages: List<ChatMessage> = listOf(
                createMessage(SYSTEM, SYSTEM_MESSAGE),
                createMessage(USER, USER_MESSAGE),
                createMessage(
                    ASSISTANT,
                    null,
                    MessageFunctionCall(FUNCTION_NAME, mapOf(USER_ID_KEY to USER_ID_PARAM)),
                ),
                createMessage(
                    USER,
                    USER_JSON,
                ),
            ),
            functions: List<Function>? = listOf(
                createFunction(
                    name = FUNCTION_NAME,
                    description = FUNCTION_DESCRIPTION,
                    parameters = createFunctionParameters(
                        type = OBJECT_TYPE,
                        properties = mapOf(
                            USER_ID_KEY to createFunctionParameterProperties(
                                type = FUNCTION_PARAM_PROPERTIES_TYPE,
                                description = FUNCTION_PARAM_PROPERTIES_DESCRIPTION,
                            ),
                        ),
                        required = listOf(USER_ID_KEY),
                    ),
                ),
            ),
            functionCall: FunctionCall? = null,
            temperature: Double? = TEMPERATURE,
            topP: Double? = TOP_P,
            n: Int? = N,
            stream: Boolean? = STREAM,
            stop: List<String>? = listOf(STOP_KEYWORD),
            maxTokens: Int? = MAX_TOKENS,
            presencePenalty: Double? = PRESENCE_PENALTY,
            frequencyPenalty: Double? = FREQUENCY_PENALTY,
            logitBias: Map<String, Double>? = null,
            user: String? = USER_ID,
        ): ChatCompletionRequest {
            return ChatCompletionRequest(
                model = model,
                messages = chatMessages,
                functions = functions,
                functionCall = functionCall,
                temperature = temperature,
                topP = topP,
                n = n,
                stream = stream,
                stop = stop,
                maxTokens = maxTokens,
                presencePenalty = presencePenalty,
                frequencyPenalty = frequencyPenalty,
                logitBias = logitBias,
                user = user,
            )
        }

        fun createMessage(
            role: MessageRole = USER,
            content: String? = CONTENT,
            functionCall: MessageFunctionCall? = null,
        ): ChatMessage = ChatMessage(
            role = role,
            content = content,
            functionCall = functionCall,
        )

        fun createFunction(
            name: String = FUNCTION_NAME,
            description: String = DESCRIPTION,
            parameters: FunctionParameters = FunctionParameters(
                type = TYPE,
                properties = mapOf(
                    FUNCTION_PARAMETER_NAME to FunctionParameterProperties(
                        type = TYPE,
                        description = DESCRIPTION,
                    ),
                ),
                required = listOf(FUNCTION_PARAMETER_NAME),
            ),
        ): Function = Function(
            name = name,
            description = description,
            parameters = parameters,
        )

        fun createFunctionParameters(
            type: String,
            properties: Map<String, FunctionParameterProperties>,
            required: List<String>,
        ): FunctionParameters {
            return FunctionParameters(
                type = type,
                properties = properties,
                required = required,
            )
        }

        fun createFunctionParameterProperties(type: String, description: String): FunctionParameterProperties {
            return FunctionParameterProperties(
                type = type,
                description = description,
            )
        }
    }
}
