# 基于注解的方式配置Spring
> 基于注解的方式和xml配置文件的方式实现的功能都是一样的，都是为了降低程序见的耦合，只是配置的方式不一样。
*  1.环境搭建
```xml
   <!-- 导入Spring要用的依赖 -->
   <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.8.RELEASE</version>
     </dependency>
```
*  2.使用注解配置管理的资源
## 下面就来详细介绍相关注解
```
@Component 加在类上
    相当于xml配置中的<bean></bean>标签
    value属性指定bean的id，如果不指定，默认是当前类名的，并且首字母是小写
     @Component(value = "aaa") // value 默认是 user
      public class User {
   tips: 使用@Component组件管理的类，必须在配置文件中加上组件扫描，才能被Spring容器管理
     <!--基于注解的方式进行扫描配置了注解的类-->
        <context:component-scan base-package="cn.yhs.learn"></context:component-scan>
   
-- 下面这两个和声明周期相关
@PostConstruct 加在方法上
    相当与 <bean init-method=""></bean>
     @PostConstruct
      public void init(){
      
@PreDestroy  加在方法上
    相当于 <bean destroy=""></bean>
       @PreDestroy
        public void destroy(){

-- 下面这个是和作用范围相关的  
@Scope 加在类上 
    相当于<bean scope=" "></bean>
    value 默认是singleton,单例 ， protorype resuest session globalsession 
    @Component(value = "aaa")
    @Scope(value = "singleton")
    public class User {
  
-- 三层架构的注解 @Controller @Service  @Repository
 他们三个注解和@Component 的作用和属性是一样的，只不是提供了更加语义化
 @Controller 一般用于表现层的注解 
 @Service 一般用于业务层的注解
 @Repository  一般用于持久层的注解
 
 
-- 用于依赖注入数据的
1. @AutoWired 单独使用
    @Autowired
    private UserDao userDao;
    类似于<property       name       =       "   "           ref       =       "   "       />
    自动按照类型注入，当使用注解注入属性时，set方法是可以省略的，它只能注入其他bean类型，
    当有多个类型匹配的时候，使用要注入的对象的变量名称的作为bean的id，在spring中查找
    
2. @Autowired     @Qualifier 组合使用
     @Autowired
     @Qualifier(value = "userDaoImpl2") // 当有多个类型时，用于指定id,必须和@AutoWire 一起使用，但是给方法参数注入时，可以单独使用
     private UserDao userDao;
    
3. @Resource(name = "userDaoImpl2") // 直接按照bean的id注入，作用类似于上面两个标签@Autowired @Qualifier(value = "userDaoImpl2")组合的作用
private UserDao userDao;

4. @Value
    @Component
    public class User {
        @Value("喻汉生") // 只能注入基本数据类型和String类型
        private String username;
        @Value("18")
        private int age;
```


