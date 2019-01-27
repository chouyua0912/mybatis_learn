package z.learn.spring;

import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("SELECT * FROM user_info WHERE id = #{userId}")
    User getUser(int id);
}
