## Spring 和 JUnit 的整合
- 1 添加依赖
```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>5.0.8.RELEASE</version>
        <scope>test</scope>
    </dependency>
    <!-- 注意还要至少导入junit4.1.12 以上的单元测试包 -->
```
- 2 测试类添加注解,两种方式：全注解和xml
```java
// 第一种 全注解
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
/**
 *  @RunWith(SpringJUnit4ClassRunner.class) 替换原来运行期
 *  @ContextConfiguration(classes = SpringConfig.class) 用于加载applicationContext 对象
 *  location ： xml 方式：用于指定xml配置文件的位置，需要用classpath:指明
 *  classes : 注解方式 ： 用于指定注解的配置类
 */
public class SpringTest {
    @Autowired
    private DataSource dataSource;
    @Test
    public void test2() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(connection);
    }

// 第二种
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class DemoTest {
    @Autowired
    private UserController controller;
    @Autowired
    private ApplicationContext context;
    @Test
    public void test3() {
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void test2() {
        List<User> userList = controller.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }
```