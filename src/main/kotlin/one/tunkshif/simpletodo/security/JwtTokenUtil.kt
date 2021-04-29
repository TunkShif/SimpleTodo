package one.tunkshif.simpletodo.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import one.tunkshif.simpletodo.extension.Logging
import one.tunkshif.simpletodo.extension.logger
import one.tunkshif.simpletodo.model.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil : Logging {

    private val jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val jwtIssuer = "one.tunkshif"

    fun generateAccessToken(user: User): String =
        Jwts.builder()
            .setSubject("${user.id},${user.username}")
            .setIssuer(jwtIssuer)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
            .signWith(jwtKey)
            .compact()

    // TODO
    fun getUsername(token: String): String =
        Jwts.parserBuilder().setSigningKey(jwtKey).build()
            .parseClaimsJws(token).body.subject
//            .split(",")[1]

    fun getExpirationDate(token: String): Date =
        Jwts.parserBuilder().setSigningKey(jwtKey).build()
            .parseClaimsJws(token).body.expiration

    fun validate(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(jwtKey).build()
                .parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            logger().error("Invalid JWT signature - ${e.message}")
        } catch (e: MalformedJwtException) {
            logger().error("Invalid JWT token - ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger().error("Expired JWT token - ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger().error("Unsupported JWT token - ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger().error("JWT claims string is empty - ${e.message}")
        }
        return false
    }
}