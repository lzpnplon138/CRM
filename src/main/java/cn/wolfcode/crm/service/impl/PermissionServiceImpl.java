package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.PageResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    //RequestMappingHandlerMapping管理所有贴有RequestMapping注解的方法
    @Autowired
    private RequestMappingHandlerMapping rmhm;


    @Override
    public List<Permission> list() {
        return permissionMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = permissionMapper.queryCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        return new PageResult(count, permissionMapper.queryList(qo));
    }

    @Override
    public void reload() {
        //查询所有权限表达式
        List<String> resources = permissionMapper.selectAllResource();
        //所有贴有RequestMapping注解的方法
        Collection<HandlerMethod> methods = rmhm.getHandlerMethods().values();
        for (HandlerMethod method : methods) {
            RequiresPermissions annotation = method.getMethodAnnotation(RequiresPermissions.class);
            if (annotation != null) {
                String[] value = annotation.value();
                //判断数据库中有无该权限表达式
                if (!resources.contains(value[0])) {
                    permissionMapper.insert(new Permission(null, value[0], value[1]));
                }
            }
        }
    }

    @Override
    public List<Permission> selectByRoleId(Long roleId) {
        return permissionMapper.selectByRoleId(roleId);
    }

    @Override
    public List<String> selectResourceByEmployeeId(Long employeeId) {
        return permissionMapper.selectResourceByEmployeeId(employeeId);
    }

}
