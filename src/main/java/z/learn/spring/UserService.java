package z.learn.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Resource
    ApplicationContext context;
}
