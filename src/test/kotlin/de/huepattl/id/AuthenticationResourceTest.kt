package de.huepattl.id

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.StringStartsWith
import org.junit.jupiter.api.Test

@QuarkusTest
open class AuthenticationResourceTest {

    @Test
    fun authenticate() {
        given()
                .contentType(ContentType.JSON)
                .body(Login("admin", "foo"))
                .`when`().post("/authentication")
                .then().statusCode(200)
                .body(`is`(StringStartsWith(true, "ey")))
    }

}