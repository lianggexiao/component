package com.example.demo.web;

import com.example.demo.domain.BaseResult;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by liuq on 2017/5/15.
 */
@Api("用户管理")
@RestController
@RequestMapping(value="/hello")
public class HelloController {

    @Autowired
    public UserService userService;

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value={"/list"}, method=RequestMethod.GET)
    public List<User> getUserList() {
        List<User> r = new ArrayList<User>();
        return r;
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/save",method= RequestMethod.POST)
    public BaseResult<String> save(@ModelAttribute User user) {
        User u = new User();
        u.setId(UUID.randomUUID().toString());
        u.setUsername(user.getUsername());
        u.setAge(user.getAge());
        return BaseResult.buildSuccessResult();
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public BaseResult<User> getUser(@PathVariable String id) {
        // url中的id可通过@PathVariable绑定到函数的参数中
        User user = userService.getUser(id);
        BaseResult<User> result = BaseResult.buildRestResult(user);
        return result;
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public BaseResult<String> putUser(@PathVariable String id, @ModelAttribute User user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        User u = userService.getUser(id);
        u.setId(id);
        u.setUsername(user.getUsername());
        u.setAge(user.getAge());
        userService.update(u);
        return BaseResult.buildSuccessResult();
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public BaseResult<String> deleteUser(@PathVariable String id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        userService.remove(id);
        return BaseResult.buildSuccessResult();
    }

}
