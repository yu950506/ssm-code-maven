# 使用全注解的方式配置Spring,完全去除bean.xml
## 1. 注解回顾

**@Component**  
    相当于xml配置中的<bean></bean>标签   
    value属性指定bean的id，如果不指定，默认是当前类名的，并且首字母是小写
    ```xml
   
     @Component(value = "aaa") // value 默认是 user
      public class User {
     使用@Component组件管理的类，必须在配置文件中加上组件扫描，才能被Spring容器管理
     <!--基于注解的方式进行扫描配置了注解的类-->
        <context:component-scan base-package="cn.yhs.learn"></context:component-scan>
    
    ```
   
下面这两个和声明周期相关    
**@PostConstruct**

    相当与 <bean init-method=""></bean>
     @PostConstruct
      public void init(){
      
**@PreDestroy**  

    相当于 <bean destroy=""></bean>
       @PreDestroy
        public void destroy(){

-- 下面这个是和作用范围相关的  
**@Scope** 

    相当于<bean scope=" "></bean>
    value 默认是singleton,单例 ， protorype resuest session globalsession 
    @Component(value = "aaa")
    @Scope(value = "singleton")
    public class User {
  
-- 三层架构的注解 : **@Controller @Service  @Repository**
 他们三个注解和@Component的作用和属性是一样的，只不是提供了更加语义化       
 **@Controller** 一般用于表现层的注解     
 **@Service** 一般用于业务层的注解    
 **@Repository**  一般用于持久层的注解    
 
 
-- 用于依赖注入数据的
1. **@AutoWired** 单独使用
```
    @Autowired
    private UserDao userDao;
    类似于<property  name  =   "   "     ref  =  " "   />
    自动按照类型注入，当使用注解注入属性时，set方法是可以省略的，它只能注入其他bean类型，
    当有多个类型匹配的时候，使用要注入的对象的变量名称的作为bean的id，在spring中查找
  ``` 
2. **@Autowired**     **@Qualifier** 组合使用
```java
     @Autowired
     @Qualifier(value = "userDaoImpl2") // 当有多个类型时，用于指定id,必须和@AutoWire 一起使用，但是给方法参数注入时，可以单独使用
     private UserDao userDao;
```
    
3.  **@Resource**
```java
@Resource(name = "userDaoImpl2") // 直接按照bean的id注入，作用类似于上面两个标签@Autowired @Qualifier(value = "userDaoImpl2")组合的作用
private UserDao userDao;
```

4. **@Value**
```java
    @Component
    public class User {
        @Value("喻汉生") // 只能注入基本数据类型和String类型
        private String username;
        @Value("18")
        private int age;
```
## 2. 新注解
**@Configuration** 相当于bean.xml配置文件用于指定当前类是一个Spring的配置类， 当创建容器的时候，会从该类加上注解，获取容器时需要使用AnnotationApplicationContext value 属性，用于指定配置类的字节码            
**@ComponentScan** 相当于 <context:component-scan base-package="cn.yhs.learn"></context:component-scan> 标签，用于指定要扫描的包
@ComponentScan(basePackages = "cn.yhs.learn")
```java
@Configuration
@ComponentScan(basePackages = "cn.yhs.learn")
//@Import({DruidJdbcConfig.class,C3p0JdbcConfig.class})
@Import(C3p0JdbcConfig.class)
public class SpringConfig {
```
**@Bean** 该注解只能写在方法上，表名使用此方法创建一个对象，并且放入Spring容器
```java
    @Bean(name = "druidDataSource")
    public DataSource getDruidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        return druidDataSource;
    }
```
**@PropertySource** 用于加载properties文件中的配置， value属性用于指定文件所在位置，需要在前面加classpath
```java
@PropertySource(value = "classpath:druid.properties")
public class DruidJdbcConfig {
    @Value("${druid.jdbc.driverClassName}")
    private String driverClassName;
    @Value("${druid.jdbc.url}")
    private String url;
    @Value("${druid.jdbc.username}")
    private String username;
    @Value("${druid.jdbc.password}")
    private String password;
    @Value("${druid.jdbc.maxWait}")
    private Integer maxWait;
    @Value("${druid.jdbc.initialSize}")
    private Integer initialSize;
    @Value("${druid.jdbc.maxActive}")
    private Integer maxActive;
```
**@Import** 用于导入其他配置类，可以不用加@Configuration注解，当然，写上也没有问题,value属性用于指定其他配置类的字节码。
```java
@Configuration
@ComponentScan(basePackages = "cn.yhs.learn")
//@Import({DruidJdbcConfig.class,C3p0JdbcConfig.class})
@Import(C3p0JdbcConfig.class)
public class SpringConfig {
```