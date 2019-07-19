package jwt;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.StringTokenizer;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class Demo {
	static String  key = Base64.getEncoder().encodeToString("123456".getBytes());
	public static void main(String[] args) {
		
	  
		JwtBuilder builder = Jwts.builder();
		
		String compact = builder.setSubject("job")
			.signWith(SignatureAlgorithm.HS512, key)
			.compressWith(CompressionCodecs.DEFLATE)
			.compact();
	 System.out.println(compact);
/*		eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2IifQ.fiMcSD6ilm9GQxplGPb4sSk-TaIKoARTaL1BJ7UN33rFrQJioJ0Za4Gkn6WDW-d5GsZco8fvTGc9VIXsjajh5g
*/	}
	
	@Test
	public void parse() {
		
		String claimsJwt = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNqqViouTVKyUsrKT1KqBQAAAP__.u_p4OxzkxMvte_Hz6JBpdT8ZLf2aHIooR8sLi3DtmeRSgc0Qo_0aUJCKLICplEi_dPIa7Vk_vMnBAcxGsnYRUw";

		
		Claims body = null;
		try {
			body = Jwts.parser().setSigningKey(key)
							.parseClaimsJws(claimsJwt)
							.getBody();
			
			
			JwsHeader header = Jwts.parser().setSigningKey(key)
			.parseClaimsJws(claimsJwt).getHeader();
			
			String signature = Jwts.parser().setSigningKey(key)
			.parseClaimsJws(claimsJwt).getSignature();
			
			System.out.println(header);
			System.out.println(signature);
			
		} catch (ExpiredJwtException e) {
			
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			
			e.printStackTrace();
		} catch (SignatureException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(body);
		
		String subject = body.getSubject();
		
		System.out.println(subject);
		
		
	}
	
	@Test
	public void base() {
/*		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJ1bmlxdWVfbmFtZSI6InpoYW5nc2FuIiwidXNlcmlkIjoiMDEiLCJpc3MiOiJ0aWFuIiwiYXVkIjoidGlhbiJ9.J8iY5egtC1MXK_fHzdKIgWvEEWPHk9eCxGLBJtxTPOA";
*/		String token = "eda1782204cf41efaca1e051ccc610be62acdcf24c09f011f343583c41cfb93f";
	
		String[] split = token.split("\\.");
		
		Decoder decoder = Base64.getDecoder();
		
		for (String string : split) {
			byte[] decode = decoder.decode(string);
			guessEncoding(decode);
			System.out.println(new String(decode));
		}
	
	}

	public String guessEncoding(byte[] bytes) {  
	   
	    org.mozilla.universalchardet.UniversalDetector detector =  
	        new org.mozilla.universalchardet.UniversalDetector(null);  
	    detector.handleData(bytes, 0, bytes.length);  
	    detector.dataEnd();  
	    String encoding = detector.getDetectedCharset();  
	    detector.reset();  
	   
	    return encoding;  
	}  
}
