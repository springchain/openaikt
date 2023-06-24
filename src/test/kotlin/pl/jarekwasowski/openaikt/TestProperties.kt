package pl.jarekwasowski.openaikt

import io.kotest.core.annotation.EnabledCondition
import io.kotest.core.spec.Spec
import pl.jarekwasowski.openaikt.TestProperties.Companion.OPENAI_API_KEY_ENV_VAR_NAME
import kotlin.reflect.KClass

class TestProperties {
    companion object {

        const val OPENAI_API_KEY_ENV_VAR_NAME = "OPENAI_API_KEY"

        val OPENAI_API_KEY = lazy {
            System.getenv(OPENAI_API_KEY_ENV_VAR_NAME)
                ?: throw RuntimeException("$OPENAI_API_KEY_ENV_VAR_NAME is not set")
        }
    }
}

class EnabledIfEnvironmentVariable : EnabledCondition {
    override fun enabled(kclass: KClass<out Spec>): Boolean {
        return System.getenv(OPENAI_API_KEY_ENV_VAR_NAME) != null
    }
}
