package com.ibs.backed.verify;

import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.RbacDao;
import com.ibs.backed.data.JWTToken;
import com.ibs.backed.data.Permission;
import com.ibs.backed.data.Role;
import com.ibs.backed.data.User;
import com.ibs.backed.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 消息授权过滤器
 * @author xml
 */
@Component
public class AuthenrizationFilter extends AuthorizingRealm {

    private final RbacDao rbacDao = MyBatis.getMyBatis().getRbacDao();

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        String token = (String) authenticationToken.getCredentials();
        //解密获得username
        String username = JwtUtil.getUsername(token);
        User user = rbacDao.selectUserByname(username);

        if(username == null || user==null || !JwtUtil.verity(token,user.getPassword())){
            throw new AuthenticationException("token认证失败");
        }
        String userpass = user.getPassword();


        return new SimpleAuthenticationInfo(token,token,"TokenRealm");
    }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————"+principalCollection.toString());
        String username = JwtUtil.getUsername(principalCollection.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = rbacDao.selectUserByname(username);
        List<Integer> roles = rbacDao.selectUserRole(user.getId());


        Set<String> roleSet = new HashSet<>();
        Set<Role> userroles = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();

        System.out.println(roles.toString());

        for(Integer role:roles){
            System.out.println(role);
            Role role1 = rbacDao.selectRoleByid(role);
            roleSet.add(role1.getName());
            userroles.add(role1);
        }

        for(Role role:userroles){
            List<Integer> list = rbacDao.selectRolePermission(role.getId());
            for(int id : list){
                Permission permission = rbacDao.selectPermissionByid(id);
                permissionSet.add(permission.getName());
            }
        }

        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }
}
