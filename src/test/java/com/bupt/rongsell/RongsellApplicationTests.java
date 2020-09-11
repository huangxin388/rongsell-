package com.bupt.rongsell;

import com.alibaba.fastjson.JSONObject;
import com.bupt.rongsell.dao.HobbyMapper;
import com.bupt.rongsell.dao.SkuMapper;
import com.bupt.rongsell.dao.SpuMapper;
import com.bupt.rongsell.entity.Hobby;
import com.bupt.rongsell.entity.Sku;
import com.bupt.rongsell.entity.Spu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class RongsellApplicationTests {

    @Autowired
    private HobbyMapper hobbyMapper;

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuMapper spuMapper;

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

    @Test
    public void testMap() {
        Map map = new HashMap();
        map.put("爱好", "篮球");
        map.put("爱好", "足球");
        System.out.println(map.get("爱好"));
    }

    @Test
    void testGetId() {
        Spu spu = new Spu();
        spu.setTitle("测试主键");
        spu.setSpgId(36);
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());
        spu.setIsDelete(false);
        int result = spuMapper.insert(spu);
        System.out.println("result:" + result);
        System.out.println("id:" + spu.getId());


    }

}
