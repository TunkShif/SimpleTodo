package one.tunkshif.simpletodo.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@ComponentScan
@EnableTransactionManagement
class JpaConfig {
    @Bean("entityManagerFactory")
    fun createEntityManagerFactory(@Autowired dataSource: DataSource): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setDataSource(dataSource)
            setPackagesToScan("one.tunkshif.simpletodo.model")
            setJpaProperties(Properties().apply {
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect")
                setProperty("hibernate.hbm2ddl.auto", "update")
                setProperty("hibernate.show_sql", "true")
            })
        }

    fun createDataSource(): DataSource {
        val config = HikariConfig().apply {
            driverClassName = "com.mysql.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://localhost:3306/test"
            username = "tunkshif"
            password = "mysqlTK.SF777"
        }
        return HikariDataSource(config)
    }

    @Bean("transactionManager")
    fun createTxManager(@Autowired entityManagerFactory: EntityManagerFactory): PlatformTransactionManager =
        JpaTransactionManager(entityManagerFactory)

}