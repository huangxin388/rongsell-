package com.bupt.rongsell.dao;

import com.bupt.rongsell.entity.PayInfo;
import com.bupt.rongsell.entity.PayInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface PayInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    long countByExample(PayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int deleteByExample(PayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int insert(PayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int insertSelective(PayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    List<PayInfo> selectByExample(PayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    PayInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int updateByExampleSelective(@Param("record") PayInfo record, @Param("example") PayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int updateByExample(@Param("record") PayInfo record, @Param("example") PayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int updateByPrimaryKeySelective(PayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbg.generated Thu Mar 26 17:37:11 CST 2020
     */
    int updateByPrimaryKey(PayInfo record);
}
