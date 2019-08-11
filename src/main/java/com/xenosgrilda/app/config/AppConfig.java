package com.xenosgrilda.app.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc // Replaces "<mvc:annotation-driven/>"
@EnableTransactionManagement // Replaces "<tx:annotation-driven/>"
@ComponentScan("com.xenosgrilda")
@PropertySource( {"classpath:persistence-mysql.properties"} ) // Exposes the properties values to this class
public class AppConfig implements WebMvcConfigurer {

    // Setting up Environment to get "properties" files access
    @Autowired
    private Environment env;

    // Setting Logger (Optional)
    private Logger logger = Logger.getLogger(getClass().getName());

    // ======================================================================
    // =                            Bean Configs                            =
    // ======================================================================
    @Bean
    public DataSource dataSource() {

        // Creating connection pool ( Basically connections that are kept open to use )
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();

        // Setting Driver
        try {

            pooledDataSource.setDriverClass(env.getProperty("jdbc.driver"));

        } catch (PropertyVetoException rexc) {

            throw new RuntimeException(rexc);
        }

        // Logging connection props, for testing reasons
        this.logger.info(">>> jdbc.url=" + env.getProperty(env.getProperty("jdbc.url")));
        this.logger.info(">>> jdbc.user=" + env.getProperty(env.getProperty("jdbc.user")));


        // Setting DB connection props
        pooledDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        pooledDataSource.setUser(env.getProperty("jdbc.user"));
        pooledDataSource.setPassword(env.getProperty("jdbc.password"));

        // Setting connection pool props ( Since we're using MySQL pool connection we need to se this)
        pooledDataSource.setInitialPoolSize(this.parsePropertyInt("connection.pool.initialPoolSize"));
        pooledDataSource.setMinPoolSize(this.parsePropertyInt("connection.pool.minPoolSize"));
        pooledDataSource.setMaxPoolSize(this.parsePropertyInt("connection.pool.maxPoolSize"));
        pooledDataSource.setMaxIdleTime(this.parsePropertyInt("connection.pool.maxIdleTime"));

        return pooledDataSource;
    }

    // Setting Hibernate Session Factory and HibernateTransactionManager
    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        // create session factorys
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan")); // @Entity Classes
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }


    // ======================================================================
    // =                         Resource Configs                           =
    // ======================================================================

    // I'm using this class to map our static resources files to be served by our app, this is the same as
    // <mvc:resources mapping="/resources/**" location="/resources/" />
    // But with some more features, check: https://www.baeldung.com/spring-mvc-static-resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This will map a external URI to the "webapp/resources" folder
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/static");
    }




    // ======================================================================
    // =                         Helper Functions                           =
    // ======================================================================

    // Custom method to convert returned properties values to int
    private int parsePropertyInt(String propName) {

        return Integer.parseInt(env.getProperty(propName));
    }

    // Custom method to get Hibernate properties
    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        return props;
    }
}





/**
 * @PropertySource Is how we tell Spring to read properties files and assign it to the "classpath"
 * By default, Spring will take .properties from "main/resources" and place them in "classpath"
 * And we can get access to the properties registered by using the "Environment" object
 *
 * DataSource: This is how we can connect to a DB in Java, this class offers us a implementation to configure the
 * connection programmatically.
 *
 * ComboPooledDataSource:
 */

/**
 * For creating the connection, we used to do it in the "dispatcher-servlet.xml", and we used to create a @Bean there:
 * <!-- Step 1: Define Database DataSource / connection pool -->
 * <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
 *       destroy-method="close">
 *     <property name="driverClass" value="com.mysql.cj.jdbc.Driver" />
 *     <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&amp;serverTimezone=UTC" />
 *     <property name="user" value="web_db_user" />
 *     <property name="password" value="web_db_user" />
 *
 *     <!-- these are connection pool properties for C3P0 -->
 *     <property name="minPoolSize" value="5" />
 *     <property name="maxPoolSize" value="20" />
 *     <property name="maxIdleTime" value="30000" />
 *     <property name="initialPoolSize" value="5" />
 *     <!-- A connection pool is used to avoid opening new connections such as sessionFactory -->
 *     <!-- https://pt.wikipedia.org/wiki/Pool_de_conex%C3%B5es -->
 * </bean>
 */

/**
 * For configuring the Hibernate, we used to do it in the "dispatcher-servlet.xml", and we used to create a @Bean there:
 * <!-- Step 2: Setup Hibernate session factory -->
 *  <bean id="sessionFactory"
 *        class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
 *      <property name="dataSource" ref="dataSource" />
 *      <!-- This is the same as componentsScan but for @Entity annotated classes -->
 *      <property name="packagesToScan" value="com.xenosgrilda.app.entities" />
 *      <property name="hibernateProperties">
 *          <props>
 *              <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
 *              <prop key="hibernate.show_sql">true</prop>
 *          </props>
 *      </property>
 *  </bean>
 *
 *  <!-- Step 3: Setup Hibernate transaction manager -->
 *  <bean id="myTransactionManager"
 *        class="org.springframework.orm.hibernate5.HibernateTransactionManager">
 *      <property name="sessionFactory" ref="sessionFactory"/>
 *  </bean>
 *
 *  <!-- Step 4: Enable configuration of transactional behavior based on annotations -->
 *  <tx:annotation-driven transaction-manager="myTransactionManager" />
 */