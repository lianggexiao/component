//package com.qing.minisys;
//
//import com.essence.core.util.JSONUtils;
//import com.qing.minisys.domain.entity.User;
//import com.qing.minisys.repository.DemoRepository;
//import com.qing.minisys.service.DemoService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import java.util.List;
//
///**
// * @description 单元测试DEMO，采用Junit+Mockito框架，Mockito进行模拟。</br>
// * 具体API请参考<url>http://static.javadoc.io/org.mockito/mockito-core/2.8.9/org/mockito/Mockito.html</url>
// * @package com.essence.simpleframe
// * @author wur
// * @date 2017-6-6
// */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({JSONUtils.class})
//public class DemoServiceTest extends BaseTest {
//
//    @Mock
//    private DemoRepository demoDao;
//
//    @InjectMocks
//    private DemoService demoService;
//
//    @Test
//    public void test_find() throws Exception {
//
//        //Mock JSONUtils.obj2json方法，该方法带返回参数，并有异常抛出
//        PowerMockito.mockStatic(JSONUtils.class);
//        PowerMockito.when(JSONUtils.class, "obj2json", Mockito.anyList()).thenReturn(Mockito.anyString()).thenThrow(new RuntimeException());
//
//        String name = "test";
//        //Mock 数据库操作
//        Mockito.when(demoDao.findPageUsers(name)).thenReturn(Mockito.anyListOf(User.class));
//        List<User> userList = demoService.findPageUsers(name, 1, 20);
//        Assert.assertNotNull(userList);
//    }
//
//    @Test(expected = Exception.class)
//    public void test_find_whenNameIsEmpty() throws Exception {
//        String name = "";
//        Mockito.when(demoDao.findPageUsers(name)).thenReturn(Mockito.anyListOf(User.class));
//        List<User> userList = demoService.findPageUsers(name, 1, 20);
//        Assert.assertNotNull(userList);
//    }
//}
