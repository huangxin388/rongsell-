package com.bupt.rongsell.vo;

/**
 * @Author huang xin
 * @Date 2020/4/1 16:55
 * @Version 1.0
 */
public class CategoryVo {
    private Integer id;
    private String name;

    public CategoryVo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryVo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
