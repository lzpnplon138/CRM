package cn.wolfcode.crm.shiro;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.service.IRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;

    @Override
    public String getName() {
        return "MyRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Employee employee = (Employee) principals.getPrimaryPrincipal();
        Long id = employee.getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (employee.getAdmin()) {
            //是超管,所有权限
            info.addStringPermission("*:*");
            //角色
            info.addRole("ADMIN");
        } else {
            //不是超管,查询出对应的权限和角色
            info.addStringPermissions(permissionService.selectResourceByEmployeeId(id));
            info.addRoles(roleService.selectNameByEmployeeId(id));
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从数据库中查询用户
        Employee employee = employeeService.selectByUsername((String) token.getPrincipal());
        if (employee != null) {
            return new SimpleAuthenticationInfo(employee, employee.getPassword(),
                    ByteSource.Util.bytes(employee.getUsername()), getName());
        }
        return null;
    }
}
