package de.huepattl.id

import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class App {

    open fun appStartup(@Observes event: StartupEvent) {
        println("ENVironment specific to ID:")
        System.getenv().forEach { (k, v) ->
            if (k.startsWith("ID_")) {
                println("   $k: $v")
            }
        }
    }

}

