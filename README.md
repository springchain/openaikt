# Kotlin OpenAI Reactive API Client

This repository contains a Kotlin client library that connects with the OpenAI API. It leverages several other libraries including:
- Sprint WebClient
- FasterXML/jackson
- Logback

The library currently supports the API version as of June 25, 2021, and provides the following features:
- Models
- Chat
- Completion
- Edit
- Images (only create)
- Embeddings
- Moderation

Full API documentation can be found on the [OpenAI platform](https://platform.openai.com/docs/api-reference).

## Prerequisites
To run and develop the code locally, you need to add the OPENAI_API_KEY environment variable containing your OpenAI API key.

## Installation

Currently, only the source code is available. In the future, the library will be distributed via Maven Central.

## Usage

The library provides several features as demonstrated below:

### Chat Completion

```kotlin
val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))
// Find the full implementation in examples/src/main/kotlin/ChatExample.kt

val chatRequest = ChatCompletionRequest(
    model = "gpt-3.5-turbo",
    messages = listOf(
        ChatMessage(SYSTEM, "Suggest 3 strategies on how to sell AI in SMEs. "),
    ),
)
var response = openAiClient.chatCompletion(chatRequest)
```

### Completion

```kotlin
val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))
// Find the full implementation in examples/src/main/kotlin/CompletionExample.kt

val completionRequest = CompletionRequest(
    model = "text-davinci-003",
    prompt = listOf("Hello, how are you?"),
)
val response = openAiClient.completion(completionRequest)
```

### Edit

```kotlin
val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))
// Find the full implementation in examples/src/main/kotlin/EditExample.kt

val editRequest = EditRequest(
    model = "text-davinci-edit-001",
    input = "We is going to the store.",
    instruction = "Fix the grammar.",
)

val response = openAiClient.edit(editRequest)
```

### Get Embeddings

```kotlin
val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))
// Find the full implementation in examples/src/main/kotlin/EmbeddingExample.kt

val embeddingRequest = EmbeddingRequest(
    model = "text-embedding-ada-002",
    input = "We is going to the store.",
)

val response = openAiClient.getEmbedding(embeddingRequest)
```

### List Models

```kotlin
val openAiClient = OpenAiClient(OpenAiWebClient(System.getenv("OPENAI_API_KEY")))
// Find the full implementation in examples/src/main/kotlin/ModelListingExample.kt

val models = openAiClient.listModels()
```

## License

This library is licensed under the Creative Commons NonCommercial (CC BY-NC).
