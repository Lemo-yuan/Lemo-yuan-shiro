import com.lemo.shiro.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomerRealmTest {
    @Test
    public void testAuthentication(){

        CustomerRealm customerRealm = new CustomerRealm();

        DefaultSecurityManager defaultSecurityManager= new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customerRealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =new UsernamePasswordToken("zhangsan","123456");
        subject.login(token);

        System.out.println("认证结果："+subject.isAuthenticated());





        subject.checkRole("admin");


        subject.checkPermission("user:update");
    }
}
