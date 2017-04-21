package com.qing.simpleframe.repository;/*package com.essence.simpleframe.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.essence.simpleframe.domain.User;

@Mapper  
public interface UserMapper {  
  
    @Select("SELECT * FROM USER WHERE NAME = #{name}")  
    User findByName(@Param("name") String name);  
  
    @Update("UPDATE user SET age=#{age} WHERE name=#{name}")  
    void update(User user);  
  
    @Insert("INSERT INTO USER(NAME,AGE) VALUES(#{name},#{age})")  
    int insert(@Param("name") String name, @Param("age") Integer age);  
  
    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")  
    int insertByMap(Map<String, Object> map);  
  
    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")  
    int insertByUser(User user);  
  
    @Delete("DELETE FROM user WHERE id =#{id}")  
    void delete(Long id);  
  
    @Results({  
        @Result(property="name",column="name"),  
        @Result(property="age",column="age")  
    })  
    @Select("SELECT name, age FROM user")  
    List<User> findAll();  
}  
*/