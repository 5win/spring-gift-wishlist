package gift.jwt;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        // 파싱 후, 시크릿키를 통해 이 서버에서 생성되었는지 검증.
        // 이후 토큰에서 클레임을 파싱한 뒤, 페이로드를 가져오고 username을 가져온다.
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
            .get("username", String.class);
    }

//    public String getRole(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
//            .get("role", String.class);
//    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
            .getExpiration().before(new Date());
    }

    public String createJwt(String username, long expiredMs) {
        return Jwts.builder()
            .claim("username", username)
//            .claim("role", role)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date((System.currentTimeMillis() + expiredMs)))
            .signWith(secretKey)
            .compact();
    }
}
