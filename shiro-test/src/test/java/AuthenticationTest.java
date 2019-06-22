import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    /*SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("Mark","123456");
    }*/
    /*
    shiro 认证
     */

    @Test
    public void testAuthentication(){

        IniRealm iniRealm=new IniRealm("classpath:shiro.ini");


     /*
     /第一种方法
      */
       /* Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini"); //IniSecurityManagerFactory是创建securityManager的工厂
        //创幻SecurityManager环境
        org.apache.shiro.mgt.SecurityManager securityManager =factory.getInstance();

*/
       DefaultSecurityManager defaultSecurityManager= new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
         //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =new UsernamePasswordToken("zhang","123");
        subject.login(token);

        System.out.println("认证结果："+subject.isAuthenticated());



        /*
        授权的过程
       1.构建SecurityManager环境 -》创建主体进行授权-》securityManager进行授权-》Authorizer授权器进行授权-》Realmo从数据库或者缓存中获取角色权限数据
       */

       subject.checkRole("admin");

       //验证权限
        subject.checkPermission("user:update");
    }
}
