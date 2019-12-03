package de.huepattl.id.jwt

import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Instant
import java.util.*

data class PrivateKeyPkcs8(val raw: String) {

    private val header = "-----BEGIN PRIVATE KEY-----"
    private val footer = "-----END PRIVATE KEY-----"

    init {
        require(raw.startsWith(header) && raw.contains(footer)) { """
                Private key passed is not a valid PKCS#8 format. To create one, issue these commands, for example:
                > openssl genrsa -out ./private.pem 4096
                > openssl pkcs8 -topk8 -nocrypt -in ./private.pem -out ./private.pcks8
            """.trimIndent() }
    }

    fun normalised(): String {
        return raw.trimIndent()
                .replace(header, "")
                .replace(footer, "")
                .replace("\r\n", "")
                .replace("\n", "")
    }
}

/**
 * See https://www.iana.org/assignments/jwt/jwt.xhtml#claims
 */
data class Claims(
        val name: String,
        val issuer: String,
        val issuedAt: Instant,
        val expires: Instant
)

/**
 * JSON Web Token with HS256.
 *
 * @see https://tools.ietf.org/html/rfc7515
 * @see https://jwt.io
 */
data class Jwt(
        val privateKey: PrivateKeyPkcs8,
        val sub: String = "1234567890",
        val name: String = "John Doe",
        val iat: String = "1516239022"
) {

    override fun toString(): String {
        val jws = JsonWebSignature();
        jws.payload = payload()
        jws.key = privateKey()
        jws.setHeader("typ", "JWT")
        jws.keyIdHeaderValue = "huepattl-id"
        jws.algorithmHeaderValue = AlgorithmIdentifiers.RSA_USING_SHA256

        return jws.compactSerialization
    }

    /**
     * The private key is a PKCS #8 generated via:
     *
     * openssl genrsa -out ./private.pem 4096
     * openssl pkcs8 -topk8 -nocrypt -in ./private.pem -out ./private.pcks8
     *
     * @see https://en.wikipedia.org/wiki/PKCS_12
     * @see https://en.wikipedia.org/wiki/PKCS_8
     */
    private fun privateKey(): PrivateKey {
        val spec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.normalised()))
        val kf = KeyFactory.getInstance("RSA")
        val pk = kf.generatePrivate(spec)

        require(pk.algorithm == "RSA")
        require(pk.format == "PKCS#8")

        return pk
    }

    private fun payload(): String {
        return """
            {
                "iss": "huepattl-id",
                "sub": "$sub",
                "name": "$name",
                "exp": 2526076800,
                "groups": [
                    "admin",
                    "user"
                ]
            }
        """.trimIndent()
    }

}