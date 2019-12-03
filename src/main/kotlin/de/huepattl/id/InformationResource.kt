package de.huepattl.id

import org.eclipse.microprofile.jwt.JsonWebToken
import java.time.Instant
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext


@Path("/ping")
@RequestScoped
@Produces(MediaType.TEXT_PLAIN)
open class InformationResource {

    @Inject
    var jwt: JsonWebToken? = null

    @GET
    @PermitAll
    fun ping(): Response {
        return Response
                .ok("Pong at ${Instant.now()} ${jwt.toString()}")
                .build()
    }

    @GET
    @Path("/info")
    @RolesAllowed("admin")
    fun info(@Context sctx: SecurityContext): String {

        println("CTX: " + sctx.userPrincipal.name)
        jwt?.claimNames?.forEach { println(it) }
        println("${jwt?.claimNames}")
        return "secured at ${Instant.now()}"
    }
}