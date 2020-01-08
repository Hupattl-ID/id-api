package de.huepattl.id

import de.huepattl.id.jwt.Jwt
import de.huepattl.id.jwt.PrivateKeyPkcs8
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.util.logging.Logger
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.*
import javax.ws.rs.core.NewCookie
import javax.ws.rs.core.Response

data class Login(val user: String = "", val password: String = "")

@Path("/authentication")
@RequestScoped
class AuthenticationResource {

    val log: Logger = Logger.getLogger(this::class.qualifiedName)

    @Inject
    @ConfigProperty(name = "id.private-key.file")
    lateinit var privateKeyLocation: String

    /**
     * Returns authentication token if credentials were valid.
     *
     * @param Credentials (username and password)
     * @return JSON Web Token (JWT) when authentication was successful or HTTP 500 otherwise
     */
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    fun login(login: Login): Response {
        log.info("Login: ${login.user}")

        return if (login.user == "admin") {
            val jwt = buildToken()
            log.info("Login successful for ${login.user}")
            Response.ok(jwt.toString()).build()
        } else {
            log.severe("Login failed for user ${login.user}")
            Response.status(500, "Invalid credentials provided").build()
        }
    }

    /**
     * Renders a HTML login form that can be used for authentication.
     */
    //@GET
    //@Produces(TEXT_HTML)
    fun showLoginForm(): String {
        log.info("Serving login page")
        return """
            <html>
                <form method="post" action="/authentication">
                    <input type="text" name="user" />
                    <input type="password" name="password" />
                    <input type="submit" value="Login" />
                </form>
            </html>
        """.trimIndent()
    }

    /**
     * Form-based version of [#login].
     */
    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    //@Produces("application/jwt")
    @Produces(TEXT_PLAIN)
    fun processFormLogin(
            @FormParam("user") user: String,
            @FormParam("password") password: String): Response {
        log.info("Form login: ${user}")

        return if (user == "admin") {
            val jwt = buildToken()
            log.info("Login successful for ${user}")

            val jwtCookie = NewCookie("access_token", jwt)
            Response.ok(jwt).cookie(jwtCookie).build()
        } else {
            log.severe("Login failed for user ${user}")
            Response.status(500, "Invalid credentials provided").build()
        }
    }

    private fun buildToken(): String {
        log.info("Using private key in $privateKeyLocation")

        return Jwt(
                PrivateKeyPkcs8(File(privateKeyLocation).readText()),
                sub = "foo",
                name = "admin",
                iat = "1422779638").toString()
    }

}
