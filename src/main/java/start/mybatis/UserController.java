package start.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	UserMapper userMapper;

	@RequestMapping("/user")
	public User user() {
		User user = userMapper.findUserByName("HHH");

		return user;
	}
}
