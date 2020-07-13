package com.bupt.rongsell;

import com.alibaba.fastjson.JSONObject;
import com.bupt.rongsell.dao.HobbyMapper;
import com.bupt.rongsell.dao.SkuMapper;
import com.bupt.rongsell.entity.Hobby;
import com.bupt.rongsell.entity.Sku;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RongsellApplicationTests {

    @Autowired
    private HobbyMapper hobbyMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetInfo() {
        List<Hobby> hobbyList = hobbyMapper.selectHobbys();
        System.out.println(hobbyList.size() + "查询成功");
        for(Hobby hobby : hobbyList) {
            System.out.println(hobby);
        }

    }

    @Test
    public void testInsert() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ball", "堡垒求");
        Hobby hobby = new Hobby();
        hobby.setHuahua(jsonObject);
        hobby.setId(35);
        hobbyMapper.insertHobby(hobby);
    }

    @Test
    public void testCustomer() {
        List<Sku> skuList = skuMapper.customerSelectSku("美的");
        System.out.println(skuList.size());
        for(Sku sku : skuList) {
            System.out.println(sku.getTitle());
        }
    }

}
