package in2021winter.annotation.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**这是spring用纯注解配置的主配置类
 * @author HuangHai
 * @date 2021/2/7 18:56
 */
//刚刚配置了gitee与GitHub，看看提交时是不是一下提交两个托管平台////答案是只提交到了GitHub上//其实可以任意选择提交的平台，但是要自己选择提交仓库的URL
    @Configuration  // 指定当前类是一个配置类 写这个注解，但是在获取容器时可以传入配置类的字节码文件，所以这里用不用这个注解都无所谓
    @ComponentScan("in2021winter.annotation") //扫描要创建容器的包，相当于xml中的<context:component-scan base-package="in2021winter.huanghai"></context:component-scan>
    @Import({JdbcConfig.class,TransactionManager.class})// 导入子配置类，有该注解的被视为父配置类，被导入的为子配置类
    @PropertySource("classpath:jdbcConfig.properties")//读取jdbc的配置property文件
    @EnableAspectJAutoProxy  //开启spring的注解aop,纯注解方法关键
    //@EnableTransactionManagement  //开启spring注解的事务管理器,纯注解方法关键
public class SpringConfiguation {
}
