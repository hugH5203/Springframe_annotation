package in2021winter.annotation.annotationService;

import in2021winter.annotation.AnnotationDao.IUserDao;
import in2021winter.annotation.AnnotationDao.IUserDao;
import in2021winter.annotation.annotationDomain.User;
import in2021winter.annotation.annotationDomain.User;
import in2021winter.annotation.annotationService.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;




/**
 * @author HuangHai
 * @date 2021/2/6 12:47
 */

@Service("userService")
/*将该类的实例（单例还是多例看@Scope配置），和@component的作用一样，只不过component是万能的，而@Service,@Controller,@Repository等三个注解为衍生的,没别的特殊作用。
就是spring为了更好的区分三层架构而做的优化。相当于xml中的Bean标签*/
//@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)//spring事务管理器，只读型。如果是增删改方法，需要在该方法上单独配置为REQUITED与true
public class UserService implements IService {

    @Autowired  //自动注入，如果容器中有且只有一个时该类型的实例的话，用注解注入时，set方法就不是必须的了
    //  @Qualifier("userDao1") 类型注入，指定一个实例注入，是对@Autowired的补充，对变量时不能单独使用，得配合@Autowired一起使用，但是对方法参数就可以单独使用
    // @Resource(name="userDao2") 名称注入，万能的注入，类似于@Qualifier,但是没了它的缺点，可以单独使用
    private IUserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public int findTotal() {
        return userDao.findTotal();
    }

    @Override
    public void transfer(String sourceName, String targetName, String sex) {
        //1，根据名字查询转出账户
        User source = userDao.findByName(sourceName).get(0);
        //2.根据名字查询转入账户
        User target = userDao.findByName(targetName).get(0);
        //3.转入账户减钱(修改性别)
        source.setSex("ta"+sex);

        //异常测试
        //int i=5/0;

        //4.转出账户加钱（修改性别）
        target.setSex("ta2"+sex);
        //5.转入账户更新数据
        userDao.updateUser(source);
        //6.转出账户更新数据
        userDao.updateUser(target);
    }
}
