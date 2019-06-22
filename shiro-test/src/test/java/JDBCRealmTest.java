import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JDBCRealmTest {

    //创建数据源
    DruidDataSource dataSource= new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("");
    }

    @Test
    public void TestJDBCRealm(){



        //创建jdbcRealm
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);  //这是权限开关 默认是false 要查询权限必须打开权限开关

        /*
        /  protected static final String DEFAULT_AUTHENTICATION_QUERY = "select password from users where username = ?";
           protected static final String DEFAULT_SALTED_AUTHENTICATION_QUERY = "select password, password_salt from users where username = ?";
           protected static final String DEFAULT_USER_ROLES_QUERY = "select role_name from user_roles where username = ?";
            protected static final String DEFAULT_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";
            以上是JdbcRealm 自带的sql 语句用来认证，授权与查询权限，但是一般我们用自定义的查询语句来查询
         */
        String sql=" SELECT PASSWORD FROM USER WHERE USERNAME=?";
        String sql2=" SELECT ROLE FROM USER_ROLE WHERE USERNAME=?";
        String sql3=" SELECT PERMISSION FROm PERMISSION WHERE username=?";


        jdbcRealm.setAuthenticationQuery(sql);
        jdbcRealm.setUserRolesQuery(sql2);
        jdbcRealm.setPermissionsQuery(sql3);

        //创建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager= new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //获得主体
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan","123456");

        subject.login(usernamePasswordToken);

        System.out.println(subject.isAuthenticated());

       subject.checkRole("admin");

       subject.checkPermission("user:delete");

    }
}
