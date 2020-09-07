package com.ibs.backed.dao;

import com.ibs.backed.data.Permission;
import com.ibs.backed.data.Role;
import com.ibs.backed.data.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Rbac数据层
 */
public interface RbacDao {

    User selectUserByid(@Param("id")int id);

    User selectUserByname(@Param("name")String username);

    Role selectRoleByid(@Param("id")int id);

    Permission selectPermissionByid(@Param("id")int id);

    List<Integer> selectUserRole(@Param("id")int id);

    List<Integer> selectRolePermission(@Param("id")int id);

    List<User> selectAllUsers();

    void updateUser(User user);

    void updatePermission(Permission permission);

    void updateRole(Role role);

    void deleteUser(@Param("id")int id);

    void deleteRole(@Param("id")int id);

    void deletePermission(@Param("id")int id);

    void regUser(User user);

    void regRole(Role role);

    void regPermission(Permission permission);

}
