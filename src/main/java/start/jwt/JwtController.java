package start.jwt;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

	
	@RequestMapping("/token")
	public String getToken(String name,String password) {
		return JwtHelper.createJWT(name, "01", "admin", "tian", "tian", 0L, JwtHelper.key);
	
	}
	@RequestMapping("/tokenUser")
	public String user() {
		return "ok";
	}
}
