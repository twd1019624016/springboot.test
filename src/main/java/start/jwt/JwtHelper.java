package start.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/* 
 * 构造及解析jwt的工具类 
 */  
public class JwtHelper {  
	
	public static String  key = Base64.getEncoder().encodeToString("123456".getBytes());
    public static Claims parseJWT(String jsonWebToken, String base64Security){  
        try  
        {  
            Claims claims = Jwts.parser()  
                       .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))  
                       .parseClaimsJws(jsonWebToken).getBody();  
            return claims;  
        }  
        catch(Exception ex)  
        {  
            return null;  
        }  
    }  
      
    /** 
     * 生成token 
     *  
     * @author hetiewei 
     * @date 2016年10月18日 下午2:51:38 
     * @param name     keyId 
     * @param userId    
     * @param role 
     * @param audience   接收者 
     * @param issuer     发行者 
     * @param TTLMillis  过期时间(毫秒) 
     * @param base64Security 
     * @return 
     */  
    public static String createJWT(String name, String userId, String role,   
            String audience, String issuer, long TTLMillis, String base64Security)   
    {  
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;  
           
        long nowMillis = System.currentTimeMillis();  
        Date now = new Date(nowMillis);  
           
        //生成签名密钥  
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);  
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());  
           
          //添加构成JWT的参数  
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")  
                                        .claim("role", role)  
                                        .claim("unique_name", name)  
                                        .claim("userid", userId)  
                                        .setIssuer(issuer)  
                                        .setAudience(audience)  
                                        .signWith(signatureAlgorithm, signingKey);  
         //添加Token过期时间  
        if (TTLMillis > 0) {  
            long expMillis = nowMillis + TTLMillis;  
            Date exp = new Date(expMillis);  
            builder.setExpiration(exp).setNotBefore(now);  
        }  
           
         //生成JWT  
        return builder.compact();  
    }   
}  