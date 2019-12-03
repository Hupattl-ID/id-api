package de.huepattl.id

import java.util.*
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.RequestScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

data class User(val id: String = UUID.randomUUID().toString(), val name: String = "[?]")

@Path("/user")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
open class UserResource {

    @GET
    @RolesAllowed("admin")
    fun find(): List<User> {
        return arrayListOf(
                User(name = "John Doe"),
                User(name = "Tony Stark")
        )
    }
}
