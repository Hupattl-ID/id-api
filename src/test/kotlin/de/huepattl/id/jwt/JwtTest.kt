package de.huepattl.id.jwt

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException
import java.security.Signature

internal class JwtTest {

    @Test
    fun privateKeyInvalid() {
        assertThrows(IllegalArgumentException::class.java
        ) {
            PrivateKeyPkcs8("foo bar")
        }
    }

    @Test
    fun privateKey() {
        // given
        val pk = PrivateKeyPkcs8(loadPrivateKey())

        // when
        val pkNormalised = pk.normalised()

        // then
        assertTrue(pkNormalised.startsWith("MIIJQQ"))
    }

    @Test
    fun token() {
        // given
        val jwt = Jwt(PrivateKeyPkcs8(loadPrivateKey()), name = "Test")

        // when
        val token = jwt.toString()

        // then
        assertTrue(token.startsWith("ey"))
    }

    private fun loadPrivateKey(): String {
        return this::class.java.getResource("/private.key").readText()
    }

}