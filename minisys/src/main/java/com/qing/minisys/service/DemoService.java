package com.qing.minisys.service;

import com.qing.minisys.domain.page.Page;
import com.qing.minisys.domain.page.PageContext;
import com.qing.minisys.domain.page.annotation.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qing.minisys.domain.entity.User;
import com.qing.minisys.repository.DemoRepository;

import java.util.List;

@Service
@Transactional
public class DemoService {

	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    @Autowired
	DemoRepository demoDao;

	public User findByName(String name) {
		return demoDao.findByName(name);
	}

	public void saveUser(User user){
		demoDao.insertOne(user);
	}

	/**
	 * 根据姓名关键字分页查询用户
	 * @param name
	 * @param pageNo     当前页码
	 * @param pageSize   每页大小
	 * @return
	 */
	@Pageable(sqlId = "com.qing.minisys.repository.DemoRepository.findPageUsers", pageNo = "#p1", pageSize = "#p2", isQueryTotal = true)
	public Page<User> findPageUsers(String name, int pageNo, int pageSize) {
		Page<User> page = new Page<>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		PageContext pageContext = PageContext.getPageContext();
		pageContext.setPage(page);
		List<User> userList = demoDao.findPageUsers(name);
		logger.info("DemoService.findPageUsers查询用户列表userList={}", userList);
		page.setData(userList);
		return page;
	}
}
