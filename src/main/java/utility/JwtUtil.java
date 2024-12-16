package utility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
   public JwtUtil(){

    }

    private static String SECRET_KEY = "my_super_secret_key_which_is_at_least_32_characters_long";


    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email); // Thêm email vào claims
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
            return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("email", String.class); // Đảm bảo rằng trường "email" tồn tại
        } catch (Exception e) {
            throw new RuntimeException("Không thể lấy email từ token", e);
        }

    }



}
