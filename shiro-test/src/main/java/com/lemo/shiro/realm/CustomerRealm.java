package com.lemo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomerRealm extends AuthorizingRealm {

    Map<String,String> userMap= new HashMap<String,String>();
    {
        userMap.put("zhangsan","123456");
        super.setName("customerRealm");
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username= (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(username);
        Set<String> premission = getPermissionByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo =new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(premission);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionByUsername(String username) {
        Set<String> permissionsset  = new HashSet<String>();
        permissionsset.add("user:delete");
        permissionsset.add("user:update");
        return permissionsset;
    }

    private Set<String> getRolesByUserName(String username) {
        Set<String> rolesset  = new HashSet<String>();
        rolesset.add("admin");
        rolesset.add("user");
        return rolesset;
    }

    //认证 AuthenticationToken 主体传过来的认证信息
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //从主体传过来的认证信息中获得用户名
        String  username= (String) authenticationToken.getPrincipal();

        //通过用户名到数据库或者缓存中获得凭证

        String password =getPasswordByUserName(username);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("zhangsan",password,"customerRealm");

        return authenticationInfo;
    }

    private String getPasswordByUserName(String username) {
        return userMap.get(username);
    }
}
