package top.wxyin.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {
    //颁发者
    private String issuer;
    //密钥
    private String secret;
    //过期时间 分钟
    private int expiration;

    /**
     * @param map ⾃定义参数
     */
    public String generateToken(Map<String, String> map) {
        //设置令牌的过期时间
        Calendar instance = Calendar.getInstance();
        //设置失效时间
        instance.add(Calendar.MINUTE, expiration);
        //创建 JWT builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach(builder::withClaim);
        //创建令牌，并指定令牌过期时间
        return builder.withIssuer(issuer).withIssuedAt(new Date()).withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     *验证令牌是否合法
     *
     * @param token
     */
    public boolean verify(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
    /**
     *解析 token
     *
     * @param token token
     * @return DecodedJWT
     */
    public DecodedJWT jwtDecode(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("token签名错误!");
        } catch (AlgorithmMismatchException e) {
            throw new RuntimeException("token算法不匹配!");
        } catch (TokenExpiredException e) {
            throw new RuntimeException("token过期!");
        } catch (Exception e) {
            throw new RuntimeException("token 解析失败!");
        }
    }
}
