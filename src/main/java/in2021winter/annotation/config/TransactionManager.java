package in2021winter.annotation.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author HuangHai
 * @date 2021/2/9 16:38
 */

public class TransactionManager {
    /**
     * 该类用于创建spring的事务管理器
     *
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager createTransactionManager( @Qualifier("dataSource1") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
