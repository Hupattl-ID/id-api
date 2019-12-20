package de.huepattl.id

import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Exposes a public RSA key that other applications can use to validate a JWT signature against. For example,
 * the URL o this resource can be set for 'mp.jwt.verify.publickey.location' in Quarkus.io applications.
 */
@Path("/keys/public")
class PublicKeyResource {

    /**
     * Location of the public RSA key file.
     *
     * @see /support/dev/generate-certificates.sh
     */
    @Inject
    @ConfigProperty(name = "ID_PUBLIC_KEY")
    var publicKeyLocation: String? = null

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun publicKey(): String {
        return File(publicKeyLocation).readText()
    }

}
