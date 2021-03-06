package z.learn.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 命名空间，按照[包].[类名].[方法名]自动生成。
 * <p>
 * Configuration.addMapper
 * --->MapperRegistry(Class(interface),MapperProxyFactory)
 * ------>MapperProxyFactory(interface,(method,mapperMethod))
 * --------->MapperMethod
 * <p>
 * 每个方法对应一个MapperMethod
 */
public interface UserInfoDao {

    @Update({
            "update user_info",
            "set name=#{name},",
            "address=#{address},",
            "phone=#{phone}",
            "where id=#{id}",
    })
    public int updateUser(@Param("id") Integer id,
                          @Param("name") String name,
                          @Param("address") String address,
                          @Param("phone") String phone);


    @Update({
            "<script>",
            "update user_info",
            "<set>",
            "<if test='name != null'> name=#{name}, </if>",
            "<if test='address != null'> address=#{address}, </if>",
            "<if test='phone != null'> phone=#{phone} </if>",
            "</set>",
            "where id=#{id}",
            "</script>"
    })
    public int updateUserScript(@Param("id") Integer id,
                                @Param("name") String name,
                                @Param("address") String address,
                                @Param("phone") String phone);

    /**
     * StatementHandler->BoundSql->DynamicSqlSource->SqlNode
     * MappedStatement->SqlSource
     */
    @Insert({
            "<script>",
            "insert into user_info (id, name, address, phone) values",
            "<foreach item='item' collection='users' separator=','>",
            "(nextval('user_info_seq'), #{item.name}, #{item.address}, #{item.phone})",
            "</foreach>",
            "</script>"
    })
    public int insertBulk(@Param("users") List<UserInfo> users);
}
