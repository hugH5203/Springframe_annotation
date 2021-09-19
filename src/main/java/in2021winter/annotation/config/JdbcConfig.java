package in2021winter.annotation.config;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**jdbc配置类
 * @author HuangHai
 * @date 2021/2/7 19:06
 */

@Configuration  //声明为一个配置类
public class JdbcConfig {

    @Value("${jdbc.driver}")  //根据SpringConfiguation配置类中的propertySource配置来自动注入相应的值
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean(name = "runner")//注解Bean为将返回值对象放于spring容器中
    @Scope("prototype")//多例对象
    public QueryRunner createQueryRunner(@Qualifier("dataSource1") DataSource dataSource){  //
         /*参数为要注入对象时，spring会自动检测容器中是否有同类型的实例，自动注入。
         如果有多个，就可以结合@Qualifiers（实例名）使用，该注解可以指定容器中的实例来注入，
         为防止容器中有多个同类型的实例，如果有多个就按实例名为准。
         如果实例名都不一样，那就报错*/

        return new QueryRunner(dataSource);

    }

    @Bean(name = "dataSource1")  //读取配置文件
    public DataSource createDataSource1(){
        try {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName(driver);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            return ds;
        } catch (Exception e) {
            System.out.println("创建数据源失败");
            throw new RuntimeException(e);
        }
    }


    @Bean(name = "dataSource2")  //不读取配置文件，写死了
    public DataSource createDataSource2(){
        try {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://localhost:3306/eesy_mybatis");
            ds.setUsername("root");
            ds.setPassword("123");
            return ds;
        } catch (Exception e) {
            System.out.println("创建数据源失败");
            throw new RuntimeException(e);
        }
    }




}
