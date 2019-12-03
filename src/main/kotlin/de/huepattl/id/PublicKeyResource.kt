package de.huepattl.id

import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/keys/public")
class PublicKeyResource {

    @Inject
    @ConfigProperty(name = "ID_PUBLIC_KEY")
    var publicKeyLocation: String? = null

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun publicKey(): String {
        return File(publicKeyLocation).readText()
    }

}