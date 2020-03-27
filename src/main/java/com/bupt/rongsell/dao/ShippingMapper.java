package com.bupt.rongsell.dao;

import com.bupt.rongsell.entity.Shipping;
import com.bupt.rongsell.entity.ShippingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ShippingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    long countByExample(ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int deleteByExample(ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int insert(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int insertSelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    List<Shipping> selectByExample(ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    Shipping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int updateByExampleSelective(@Param("record") Shipping record, @Param("example") ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int updateByExample(@Param("record") Shipping record, @Param("example") ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int updateByPrimaryKeySelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbg.generated Mon Mar 23 20:33:55 CST 2020
     */
    int updateByPrimaryKey(Shipping record);

    /**
     * 根据用户id和地址id删除地址，避免横向越权
     * @param userId
     * @param shippingId
     * @return
     */
    int deleteByUserIdShippingId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 更新地址信息
     * @param shipping
     * @return
     */
    int safeUpdateByPrimaryKey(Shipping shipping);

    /**
     * 通过用户id和地址id查询地址
     * @param userId
     * @param shippingId
     * @return
     */
    Shipping selectShippingById(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    List<Shipping> selectShippingByUserId(Integer userId);
}
