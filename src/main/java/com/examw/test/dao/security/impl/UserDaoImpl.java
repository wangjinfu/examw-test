package com.examw.test.dao.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.security.IUserDao;
import com.examw.test.domain.security.User;
import com.examw.test.model.security.UserInfo;

/**
 * 用户数据接口实现类。
 * @author yangyong.
 * @since 2014-05-08.
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.IUserDao#findUsers(com.examw.netplatform.model.admin.UserInfo)
	 */
	@Override
	public List<User> findUsers(UserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "select u from User u where 1=1 "; 
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by u." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.IUserDao#total(com.examw.netplatform.model.admin.UserInfo)
	 */
	@Override
	public Long total(UserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from User u where 1=1";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(UserInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getAccount())){
			hql += " and (u.account = :account) ";
			parameters.put("account", info.getAccount());
		}
		if(info.getRoleId() != null && info.getRoleId().length > 0){
			hql += " and (u.roles.id in (:roleId)) ";
			parameters.put("roleId", info.getRoleId());
		}
		if(info.getStatus() != null){
			hql += " and (u.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getName())){
			hql +=" and (u.name like :name  or u.account like :name  or u.nickName like :name)";
			parameters.put("name", "%" + info.getName() +  "%");
		}
		return hql;
	}
	/*
	 * 根据账号查找用户。
	 * @see com.examw.netplatform.dao.admin.IUserDao#findByAccount(java.lang.String)
	 */
	@Override
	public User findByAccount(String account) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据账号查询用户：%s", account));
		if(StringUtils.isEmpty(account)) return null;
		final String hql = "from User u where u.account = :account order by u.createTime desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("account", account);
		List<User> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}