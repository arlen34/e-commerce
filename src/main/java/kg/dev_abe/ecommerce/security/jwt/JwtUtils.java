package kg.dev_abe.ecommerce.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.token")
public class JwtUtils {
    private String secret;
    private String issuer;
    private Long expires;

    public String generateToken(String email){
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(3).toInstant());
        return JWT.create()
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateJWTToken(String jwt) {
        DecodedJWT verifier = getDecodedJWT(jwt);
        return verifier.getClaim("email").asString();
    }

    private DecodedJWT getDecodedJWT(String jwt) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer(issuer).build();
        return jwtVerifier.verify(jwt);
    }
}
