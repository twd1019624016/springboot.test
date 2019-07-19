package start.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private SysUserRepository sysUserRepository;

	public SysUser findByName(String userName) {
		return sysUserRepository.findByName(userName);
	}
	 
}
