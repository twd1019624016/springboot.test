package start.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
	@Select("select * from user where name = #{name}")
	User findUserByName(@Param("name") String name);
}
