package com.itheima.dao;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true,column ="id" ,property ="id" ),
            @Result(column = "username",property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "password", property = "password"),
            @Result(column = "phoneNum", property = "phoneNum"),
            @Result(column = "status", property = "status"),
            @Result(column = "id",property = "roles",javaType = java.util.List.class,
                    many = @Many(select ="com.itheima.dao.RoleDao.findByUserId"))
    })
    UserInfo findByUsername(String username);

    @Select("select * from users")
    List<UserInfo> findAll();

    //insert into users values('email','username','password','phonenum','','status')
    @Insert("insert into users (email,username,password,phoneNum,status) values (#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo);

    @Select("select * from users where id = #{userId}")
    @Results({
            @Result(id = true,column ="id" ,property ="id" ),
            @Result(column = "username",property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "password", property = "password"),
            @Result(column = "phoneNum", property = "phoneNum"),
            @Result(column = "status", property = "status"),
            @Result(column = "id",property = "roles",javaType =java.util.List.class,
                    many = @Many(select = "com.itheima.dao.RoleDao.findByUserId"))
    })
    UserInfo findById(String usersId);

    @Select("select * from role where id not in (select roleId from users_role where userId = #{userId})")
    List<Role> findOtherRoles(String usersId);


    @Insert("insert into users_role(roleId,userId) values (#{roleId},#{userId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String id);
}
