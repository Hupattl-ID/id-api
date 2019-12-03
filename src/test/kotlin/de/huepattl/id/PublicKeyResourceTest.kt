package de.huepattl.id

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.StringStartsWith
import org.junit.jupiter.api.Test

@QuarkusTest
open class PublicKeyResourceTest {

    @Test
    fun checkPublicKey() {
        given()
                .filter(RequestLoggingFilter()).filter(ResponseLoggingFilter())
                .`when`().get("/keys/public")
                .then()
                .statusCode(200)
                .body(`is`(StringStartsWith(true,"-----BEGIN PUBLIC KEY-----")))
    }

}