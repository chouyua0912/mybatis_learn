package z.learn.mybatis;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Mybatis {

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "test");

        sqlSessionFactory.getConfiguration().addMapper(UserInfoDao.class);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            insertExample(session);

            searchHitLocalCache(session);

            searchHitSessionBasedLocalCache(session);

            mapperUpdateUser(session);

            dynamicMapperUpdateUser(session);
        }
    }

    private static void insertExample(SqlSession session) {
        session.insert("org.mybatis.example.BlogMapper.insertBlog", new Blog("test"));
        session.insert("org.mybatis.example.UserInfo.insertUserInfo", new UserInfo("test", "addre", "111"));
    }

    private static void searchHitLocalCache(SqlSession session) {
        Blog blog = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);
        Blog blog2 = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);
        session.commit();                                   // commit会清空本地Map缓存，是session会话级别的
        Blog blog3 = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);
        session.clearCache();                               // 清空本地缓存
    }

    private static void searchHitSessionBasedLocalCache(SqlSession session) {
        Blog blog = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);
        session.insert("org.mybatis.example.BlogMapper.insertBlog", new Blog("test"));          // 任何更新类操作都会清空本地Map缓存，更新类操作，都被转换成update操作
        Blog blog3 = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);
        session.clearCache();                               // 清空本地缓存
    }

    /**
     * 命名空间，按照[包].[类名].[方法名]自动生成。
     * <p>
     * Configuration.addMapper(Class)
     * --->MapperRegistry(Class(interface),MapperProxyFactory)
     * ------>MapperProxyFactory(Class(interface),(method,mapperMethod))
     * --------->MapperMethod
     * <p>
     * 每个方法对应一个MapperMethod
     */
    private static void mapperUpdateUser(SqlSession session) {
        UserInfoDao userInfoDao = session.getMapper(UserInfoDao.class);         // 通过Session的Mapper工厂动态生成
        userInfoDao.updateUser(2, RandomStringUtils.randomAlphabetic(8), "aabb", "bbcc");
    }

    /**
     * 动态SQL
     */
    private static void dynamicMapperUpdateUser(SqlSession session) {
        UserInfoDao userInfoDao = session.getMapper(UserInfoDao.class);         // 通过Session的Mapper工厂动态生成

        userInfoDao.updateUserScript(4, RandomStringUtils.randomAlphabetic(8), "abc", null);
        userInfoDao.updateUserScript(5, null, null, RandomStringUtils.randomAlphabetic(8));
        userInfoDao.updateUserScript(6, null, RandomStringUtils.randomAlphabetic(8), null);
    }

}
