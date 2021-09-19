package in2021winter.annotation.AnnotationDao;

import in2021winter.annotation.annotationDomain.User;
import in2021winter.annotation.annotationUtils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author HuangHai
 * @date 2021/2/6 11:11
 */
@Repository("userDao")
public class UserDao implements IUserDao {
    /*这里用jdbcTemplate 不行，因为它的方法里没有需要connection的，而我们必须使用treadLocal里提供的connection才能确保是同一个connection
    才能保证事务安全性。*/
    //private JdbcTemplate template;

   /* public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }*/
@Autowired
   private QueryRunner runner;

@Autowired
   private ConnectionUtils con;

    @Override
    public List<User> findAll() {
        //return template.query("select * from user",new BeanPropertyRowMapper<User1>(User1.class));
        try {
            return runner.query(con.getThreadConnection(),"select * from user",new BeanListHandler<User>(User.class));
        } catch (SQLException e) {
            System.out.println("执行失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(User user){
        //template.update("insert into user(username,address,sex,birthday) values(?,?,?,?)",user.getUsername(),user.getAddress(),user.getSex(),user.getBirthday());
        try {
            runner.update(con.getThreadConnection(),"insert into user(username,address,sex,birthday) values(?,?,?,?)",user.getUsername(),user.getAddress(),user.getSex(),user.getBirthday());
        } catch (SQLException e) {
            System.out.println("执行失败");
            e.printStackTrace();
        }

    }

    @Override
    public void updateUser(User user){
        //template.update("update user set username=?,address=?,sex=?,birthday=? where id=?",user.getUsername(),user.getAddress(),user.getSex(),user.getBirthday(),user.getId());
        try {
            runner.update(con.getThreadConnection(),"update user set username=?,address=?,sex=?,birthday=? where id=?"
                    ,user.getUsername(),user.getAddress(),user.getSex(),user.getBirthday(),user.getId());
        } catch (SQLException e) {
            System.out.println("执行失败");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Integer id){
        //template.update("delete from user where id=?",id);
        try {
            runner.update(con.getThreadConnection(),"delete from user where id=?",id);
        } catch (SQLException e) {
            System.out.println("执行失败");
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Integer id) {
        //return template.queryForObject("select * from user where id=?",new BeanPropertyRowMapper<User1>(User1.class),id);
        try {
            return runner.query(con.getThreadConnection(),"select *from user where id=?",new BeanHandler<User>(User.class),id);
        } catch (SQLException e) {
            System.out.println("执行失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findByName(String username){
       // return template.query("select * from user where username like ?",new BeanPropertyRowMapper<User1>(User1.class),username);
        try {
            return runner.query(con.getThreadConnection(),"select * from user where username like ?",new BeanListHandler<User>(User.class),username);
        } catch (SQLException e) {
            System.out.println("执行失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findTotal(){
        //return template.queryForObject("select count(*) from user",Integer.class);
        try {
            return  runner.query(con.getThreadConnection(),"select count(*) from user",new BeanHandler<Integer>(Integer.class));
        } catch (SQLException e) {
            System.out.println("执行失败");
            throw new RuntimeException(e);
        }
    }

}
