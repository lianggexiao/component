package com.qing.minisys.controller;

import java.util.List;
import java.util.UUID;

import com.qing.minisys.domain.RequestDTO;
import com.qing.minisys.domain.dto.UserPageDTO;
import com.qing.minisys.domain.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.qing.minisys.domain.BaseResult;
import com.qing.minisys.domain.dto.UserDTO;
import com.qing.minisys.domain.entity.User;
import com.qing.minisys.service.DemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 具体的调用请打开Postman，上面有例子
 * @author 柳青
 * @date 创建时间：2017年4月21日 上午11:27:25
 */
@Api("Demo类")
@RestController
@Validated
@RequestMapping(value = "/public/demo")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    DemoService demoService;

    /**
     * 该方法发生异常时，会调用GlobalExceptionHandler里面的异常处理方法
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "保存用户", notes = "保存用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody RequestDTO<UserDTO> requestDTO) {
        UserDTO userDTO = requestDTO.getReqdata();
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        demoService.saveUser(user);
        return BaseResult.buildSuccessResult();
    }

    /**
     * 可以测试Spring缓存的使用
     */
    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    public BaseResult<?> findByName(String name) {
        User user = demoService.findByName(name);
        return BaseResult.buildRestResult(user);
    }

    /**
     * 测试参数校验
     * 这里为什么使用@RequestBody，json形式传参无法使用？
     * @param userDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public BaseResult<String> getBaseRequest(@Validated UserDTO userDTO){
        System.out.println("执行getBaseRequest方法");
        System.out.println(userDTO.getName());
        return BaseResult.buildSuccessResult();
    }

    @RequestMapping(value = "/getPageUsers", method = RequestMethod.POST)
    public BaseResult<?> getPageUsers(@RequestBody RequestDTO<UserPageDTO> baseDTO) throws Exception {
        logger.info("======开始进入DemoController.getPageUsers方法queryParem={}", baseDTO);
        UserPageDTO userPageDTO = baseDTO.getReqdata();
        Page<User> userPage = demoService.findPageUsers(userPageDTO.getName(), userPageDTO.getPageNo(), userPageDTO.getPageSize());
        return BaseResult.buildRestResult(userPage);
    }

//    public static void main(String[] args){
//        RequestDTO<UserDTO> requestDTO = new RequestDTO<>();
//        requestDTO.setIp("127.0.0.1");
//        requestDTO.setRequestTime("123456");
//        UserDTO userDTO = new UserDTO();
//        userDTO.setName("冯亮");
//        userDTO.setAge(33);
//        requestDTO.setReqdata(userDTO);
//        String json = JSONObject.toJSONString(requestDTO);
//        System.out.println(json);
//    }
}
