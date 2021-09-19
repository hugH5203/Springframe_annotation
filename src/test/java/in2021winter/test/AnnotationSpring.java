package in2021winter.test;

import in2021winter.annotation.annotationDomain.User;
import in2021winter.annotation.annotationService.IService;
import in2021winter.annotation.annotationService.UserService;
import in2021winter.annotation.config.JdbcConfig;
import in2021winter.annotation.config.SpringConfiguation;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**基于注解的spring测试，（IOC解耦，Aop面向切面，一系列通知）还有事务控制，
 * @author HuangHai
 * @date 2021/2/7 21:11
 */
public class AnnotationSpring {

    /**
     * 测试注解下的基本方法，但是我已经加入了aop动态代理，所以该方法会报错，除非用代理类。使用代理类的方法：将获取对象的参数改为IService的接口类型就可以
     */
    @Test
    public void testAnnotationSpring() {
        //注解必须要用这个类来获取容器，还要将父配置文件作为参数加进去
 AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(SpringConfiguation.class);

 //从容器中获取实例对象
        UserService service=context.getBean("userService", UserService.class);
        List<User> users=service.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("-----------------------------------------------------");
        List<User> userList=service.findByName("%王%");
        for (User user : userList) {
            System.out.println(user);
        }
        System.out.println("-------------------------------------------------------");
        service.transfer("%老%","%人%","灯");
    }


    /**
     * 测试注解的aop（环绕通知实现）,必须得用接口来接收与使用，不然报错提示：该实例对象为一个代理类
     */
    @Test
    public void testAnnotationAop(){
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(SpringConfiguation.class);
        IService service=context.getBean("userService", IService.class);
         service.transfer("%老%","%人%","神");
    }
}
