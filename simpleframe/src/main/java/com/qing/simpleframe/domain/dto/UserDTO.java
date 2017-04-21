package com.qing.simpleframe.domain.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class UserDTO {

    @NotBlank(message="用户名不能为空")
    @Length(min=3, max=20, message="用户名长度只能在3-20之间")
    private String name;
    
    @NotNull(message="年龄不能为空")
    @Range(min = 18, max = 60, message = "年龄只能从18-60") 
    private Integer age;
    
//    注解详细列表 http://blog.csdn.net/xgblog/article/details/52548659
//    Validator不仅能够校验单个实例对象，还可以校验完整的对象图。对于实例中的对象成员属性，注解上@Valid，就可以被关联校验。
//    @Valid
//    T t;
    
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

}
