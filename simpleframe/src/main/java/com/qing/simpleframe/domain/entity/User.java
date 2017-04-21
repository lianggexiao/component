package com.qing.simpleframe.domain.entity;

import java.io.Serializable;


/**
 * @author ax
 * @date 创建时间：2017年3月7日 下午5:14:43
 */
public class User implements Serializable{

    private static final long serialVersionUID = 5011308087935720264L;
    private String id;
    private String name;
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

}
