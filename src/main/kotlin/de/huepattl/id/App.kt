package de.huepattl.id

import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
open class App {

    open fun print(@Observes event: StartupEvent) {
        println("""
            Application startup detected: $event
        """.trimIndent())
    }

}

