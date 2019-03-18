package com.itheima.dao;

import com.itheima.domain.Member;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrdersDao {
    //查找所有
    @Select("select * from orders")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.itheima.dao.ProductDao.findById")),
    })
    List<Orders> findAllByPage();

    @Select("select * from orders where id = #{ordersId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            //产品信息
            @Result(property = "product", column = "productId", javaType = Product.class,
                    one = @One(select = "com.itheima.dao.ProductDao.findById")),
            //会员信息
            @Result(property = "member", column = "memberId", javaType = Member.class,
                    one = @One(select = "com.itheima.dao.MemberDao.findById")),
            //旅客表信息
            @Result(property = "travellers",column = "id",javaType = java.util.List.class,
                    many = @Many(select = "com.itheima.dao.TravellerDao.findByOrderId"))
    })
    Orders findById(String ordersId);
}
