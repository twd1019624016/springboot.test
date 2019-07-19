package start.security;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SysResourceRepostory extends JpaRepository<SysResource, Integer> {

	List<SysResource> findByResourceString(String auth);

	

}
