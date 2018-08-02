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

        SqlSession session = sqlSessionFactory.openSession();
        try {

            int ret = session.insert("org.mybatis.example.BlogMapper.insertBlog", new Blog("test"));

            session.insert("org.mybatis.example.UserInfo.insertUserInfo", new UserInfo("test", "addre", "111"));

            Blog blog = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);

            UserInfoDao userInfoDao = session.getMapper(UserInfoDao.class);

            userInfoDao.updateUser(2, RandomStringUtils.randomAlphabetic(8), "aabb", "bbcc");

            userInfoDao.updateUser(4, RandomStringUtils.randomAlphabetic(8), null, "bbcc");

            session.commit();
            System.out.println(ret);
        } finally {
            session.close();
        }
    }

}
