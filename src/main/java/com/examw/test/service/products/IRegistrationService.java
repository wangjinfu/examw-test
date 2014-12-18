package com.examw.test.service.products;

import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 注册码服务接口实现类
 * @author fengwei.
 * @since 2014年8月14日 下午3:06:38.
 */
public interface IRegistrationService extends IBaseDataService<RegistrationInfo>{
	/**
	 * 加载状态名称
	 * @param status 状态值
	 * @return
	 */
	String loadStatusName(Integer status);
//	/**
//	 * 查询注册码的软件类型限制集合。
//	 * @param registrationId
//	 * 注册码ID。
//	 * @return
//	 * 数据集合。
//	 */
//	List<SoftwareTypeLimitInfo> findSoftwareTypeLimits(String registrationId);
}