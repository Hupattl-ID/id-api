package de.huepattl.id

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.StringStartsWith
import org.junit.jupiter.api.Test

@QuarkusTest
open class InformationResourceTest {

    /**
     * @return JWT authorisation token
     */
    private fun authenticate(): String {
        return given()
                .contentType(ContentType.JSON)
                .body(Login("admin", "foo"))
                .`when`().post("/authentication").body().print()
    }

    @Test
    fun ping() {
        given()
                .filter(RequestLoggingFilter()).filter(ResponseLoggingFilter())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer ${authenticate()}")
                .`when`().get("/ping")
                .then()
                .statusCode(200)
                .body(`is`(StringStartsWith(true, "Pong")))
    }

    @Test
    fun info() {
        given()
                .filter(RequestLoggingFilter()).filter(ResponseLoggingFilter())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer ${authenticate()}")
                .`when`().get("/ping/info")
                .then()
                .statusCode(200)
                .body(`is`(StringStartsWith(true, "secured")))
    }

}