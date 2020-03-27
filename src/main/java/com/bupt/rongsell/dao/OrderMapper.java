package com.bupt.rongsell.dao;

import com.bupt.rongsell.entity.Order;
import com.bupt.rongsell.entity.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    long countByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int deleteByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    List<Order> selectByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_order
     *
     * @mbg.generated Thu Mar 26 12:04:33 CST 2020
     */
    int updateByPrimaryKey(Order record);

    /**
     * 根据用户id和订单号查询订单
     * @param userId
     * @param orderNo
     * @return
     */
    Order selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    Order selectByOrderNo(Long orderNo);

    List<Order> selectByUserId(Integer userId);

    List<Order> selectAll();
}
