package de.huepattl.id.jwt

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

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
        return "-----BEGIN PRIVATE KEY-----\n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\nmWYtdX2QVqf3YI07VPr7RlAn6kEo\n-----END PRIVATE KEY-----\n"
    }

}
