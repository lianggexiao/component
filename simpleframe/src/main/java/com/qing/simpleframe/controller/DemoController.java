package com.qing.simpleframe.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qing.simpleframe.domain.BaseResult;
import com.qing.simpleframe.domain.dto.UserDTO;
import com.qing.simpleframe.domain.entity.User;
import com.qing.simpleframe.service.DemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/** 
 * @author 柳青
 * @date 创建时间：2017年4月21日 上午11:27:25
 */
@Api("Demo类")
@RestController
@Validated
public class DemoController {

    @Autowired
	DemoService demoService;
	
    @ApiIgnore
	@RequestMapping(value="/save",method = RequestMethod.GET)  
    public BaseResult<String> save(@Validated UserDTO userDTO){ 
	    User user = new User();
	    user.setId(UUID.randomUUID().toString());
	    user.setName(userDTO.getName());
	    user.setAge(userDTO.getAge());
	    demoService.saveUser(user);
        return BaseResult.buildSuccessResult();  
    }  
	
	@ApiOperation(value="查询用户", notes="用户名查询用户")
	@RequestMapping(value="/get",method = RequestMethod.GET)  
    public BaseResult<User> getByName(@Length(min=3, max=20, message="用户名长度只能在3-20之间")
										  @RequestParam(name = "name", required = true)String name){
	    User user = demoService.findByName(name) ;
        return BaseResult.buildRestResult(user);  
    }  
	
}
